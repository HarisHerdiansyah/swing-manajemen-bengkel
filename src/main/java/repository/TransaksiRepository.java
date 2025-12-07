/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

package repository;

import dto.request.TransaksiRequestDTO;
import dto.request.TransaksiDetailRequestDTO;
import dto.response.TransaksiResponseDTO;
import util.DBConnection;

import java.util.ArrayList;
import java.sql.*;
import java.util.Date;
import java.util.List;

public class TransaksiRepository {

    public String createTransaksi(TransaksiRequestDTO request) throws SQLException {
        String noFaktur = generateNoFaktur();
        Connection conn = null;
        PreparedStatement stmtTransaksi = null;
        PreparedStatement stmtDetail = null;
        PreparedStatement stmtUpdateStok = null;

        try {
            conn = DBConnection.get();
            conn.setAutoCommit(false); // Start transaction

            // Validasi stok untuk semua barang sebelum memproses transaksi
            String validationError = validateStok(request.getDetailRequestDTOList(), conn);
            if (validationError != null) {
                throw new SQLException(validationError);
            }

            // Insert into transaksi
            String sqlTransaksi = "INSERT INTO transaksi (no_faktur, nopol, nama_pelanggan, nama_mekanik, keluhan, total_belanja) VALUES (?, ?, ?, ?, ?, ?)";
            stmtTransaksi = conn.prepareStatement(sqlTransaksi);
            stmtTransaksi.setString(1, noFaktur);
            stmtTransaksi.setString(2, request.getNopol());
            stmtTransaksi.setString(3, request.getNamaPelangan());
            stmtTransaksi.setString(4, request.getNamaMekanik());
            stmtTransaksi.setString(5, request.getKeluhan());
            stmtTransaksi.setDouble(6, request.getTotalBelanja());
            stmtTransaksi.executeUpdate();

            // Insert into transaksi_detail
            String sqlDetail = "INSERT INTO transaksi_detail (no_faktur, nama_item, jenis, qty, harga_modal, harga_deal, catatan) VALUES (?, ?, ?, ?, " +
                "CASE WHEN ? = 'BARANG' THEN (SELECT harga_beli FROM barang WHERE nama_barang = ?) * ? ELSE 0 END, ?, ?)";
            stmtDetail = conn.prepareStatement(sqlDetail);

            // Prepare statement untuk update stok
            String sqlUpdateStok = "UPDATE barang SET stok = stok - ? WHERE nama_barang = ?";
            stmtUpdateStok = conn.prepareStatement(sqlUpdateStok);

            List<TransaksiDetailRequestDTO> details = request.getDetailRequestDTOList();
            for (TransaksiDetailRequestDTO detail : details) {
                // Insert transaksi detail
                stmtDetail.setString(1, noFaktur);
                stmtDetail.setString(2, detail.getJenis().equals("BARANG") ? detail.getNamaBarang() : "Jasa");
                stmtDetail.setString(3, detail.getJenis());
                stmtDetail.setInt(4, detail.getJumlah());
                stmtDetail.setString(5, detail.getJenis());
                stmtDetail.setString(6, detail.getNamaBarang());
                stmtDetail.setInt(7, detail.getJumlah());
                stmtDetail.setDouble(8, detail.getSubtotal());
                stmtDetail.setString(9, detail.getCatatan());
                stmtDetail.executeUpdate();

                // Kurangi stok jika jenis adalah BARANG
                if ("BARANG".equals(detail.getJenis())) {
                    stmtUpdateStok.setInt(1, detail.getJumlah());
                    stmtUpdateStok.setString(2, detail.getNamaBarang());
                    int updatedRows = stmtUpdateStok.executeUpdate();

                    if (updatedRows == 0) {
                        throw new SQLException("Gagal mengurangi stok untuk barang: " + detail.getNamaBarang());
                    }
                }
            }

            conn.commit();
            return noFaktur;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw e; // Re-throw exception agar bisa ditangani di service layer
        } finally {
            try {
                if (stmtTransaksi != null) stmtTransaksi.close();
                if (stmtDetail != null) stmtDetail.close();
                if (stmtUpdateStok != null) stmtUpdateStok.close();
                DBConnection.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String validateStok(List<TransaksiDetailRequestDTO> details, Connection conn) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT stok FROM barang WHERE nama_barang = ?";
            stmt = conn.prepareStatement(sql);

            for (TransaksiDetailRequestDTO detail : details) {
                if ("BARANG".equals(detail.getJenis())) {
                    stmt.setString(1, detail.getNamaBarang());
                    rs = stmt.executeQuery();

                    if (rs.next()) {
                        int stokTersedia = rs.getInt("stok");
                        if (stokTersedia < detail.getJumlah()) {
                            return "Stok tidak cukup untuk barang '" + detail.getNamaBarang() +
                                   "'. Stok tersedia: " + stokTersedia + ", diminta: " + detail.getJumlah();
                        }
                    } else {
                        return "Barang '" + detail.getNamaBarang() + "' tidak ditemukan dalam database";
                    }

                    rs.close();
                }
            }

            return null; // Semua validasi berhasil
        } catch (SQLException e) {
            e.printStackTrace();
            return "Terjadi kesalahan saat validasi stok: " + e.getMessage();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateNoFaktur() {
        String prefix = "TRX-";
        String sql = "SELECT MAX(no_faktur) FROM transaksi WHERE no_faktur LIKE ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, prefix + "%");
            rs = stmt.executeQuery();

            if (rs.next()) {
                String maxNo = rs.getString(1);
                if (maxNo != null && maxNo.startsWith(prefix)) {
                    String numStr = maxNo.substring(prefix.length());
                    try {
                        long num = Long.parseLong(numStr);
                        num++;
                        return prefix + String.format("%012d", num);
                    } catch (NumberFormatException e) {
                        // Jika gagal parse, mulai dari 1
                    }
                }
            }
            // Jika tidak ada atau gagal, mulai dari 1
            return prefix + String.format("%012d", 1L);
        } catch (SQLException e) {
            e.printStackTrace();
            // Fallback ke timestamp jika error
            return prefix + System.currentTimeMillis();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DBConnection.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<TransaksiResponseDTO> getAllTransaksi() {
        String sql = "SELECT t.no_faktur, t.tanggal, p.nopol, p.nama_pemilik, t.nama_mekanik, t.total_belanja FROM transaksi t JOIN pelanggan p ON t.nopol = p.nopol ORDER BY t.tanggal DESC";
        List<TransaksiResponseDTO> list = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String noFaktur = rs.getString("no_faktur");
                Date tanggal = rs.getTimestamp("tanggal");
                String nopol = rs.getString("nopol");
                String namaPelanggan = rs.getString("nama_pemilik");
                String namaMekanik = rs.getString("nama_mekanik");
                double totalBelanja = rs.getDouble("total_belanja");
                list.add(new TransaksiResponseDTO(noFaktur, tanggal, nopol, namaPelanggan, namaMekanik, totalBelanja));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DBConnection.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<TransaksiResponseDTO> searchTransaksi(String noFaktur, String tanggal, String namaMekanik, String namaPelanggan) {
        StringBuilder sql = new StringBuilder(
            "SELECT t.no_faktur, t.tanggal, p.nopol, p.nama_pemilik, t.nama_mekanik, t.total_belanja " +
            "FROM transaksi t " +
            "JOIN pelanggan p ON t.nopol = p.nopol " +
            "WHERE 1=1"
        );

        List<String> params = new ArrayList<>();

        if (noFaktur != null && !noFaktur.trim().isEmpty()) {
            sql.append(" AND t.no_faktur LIKE ?");
            params.add("%" + noFaktur.trim() + "%");
        }

        if (tanggal != null && !tanggal.trim().isEmpty()) {
            sql.append(" AND DATE_FORMAT(t.tanggal, '%Y-%m-%d') LIKE ?");
            params.add("%" + tanggal.trim() + "%");
        }

        if (namaMekanik != null && !namaMekanik.trim().isEmpty()) {
            sql.append(" AND t.nama_mekanik LIKE ?");
            params.add("%" + namaMekanik.trim() + "%");
        }

        if (namaPelanggan != null && !namaPelanggan.trim().isEmpty()) {
            sql.append(" AND p.nama_pemilik LIKE ?");
            params.add("%" + namaPelanggan.trim() + "%");
        }

        sql.append(" ORDER BY t.tanggal DESC");

        List<TransaksiResponseDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                stmt.setString(i + 1, params.get(i));
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                String resultNoFaktur = rs.getString("no_faktur");
                Date resultTanggal = rs.getTimestamp("tanggal");
                String nopol = rs.getString("nopol");
                String resultNamaPelanggan = rs.getString("nama_pemilik");
                String resultNamaMekanik = rs.getString("nama_mekanik");
                double totalBelanja = rs.getDouble("total_belanja");
                list.add(new TransaksiResponseDTO(resultNoFaktur, resultTanggal, nopol, resultNamaPelanggan, resultNamaMekanik, totalBelanja));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DBConnection.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
