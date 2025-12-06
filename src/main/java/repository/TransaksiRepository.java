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

    public String createTransaksi(TransaksiRequestDTO request) {
        String noFaktur = generateNoFaktur();
        Connection conn = null;
        PreparedStatement stmtTransaksi = null;
        PreparedStatement stmtDetail = null;

        try {
            conn = DBConnection.get();
            conn.setAutoCommit(false); // Start transaction

            // Insert into transaksi
            String sqlTransaksi = "INSERT INTO transaksi (no_faktur, nopol, nama_mekanik, keluhan, total_belanja) VALUES (?, ?, ?, ?, ?)";
            stmtTransaksi = conn.prepareStatement(sqlTransaksi);
            stmtTransaksi.setString(1, noFaktur);
            stmtTransaksi.setString(2, request.getNopol());
            stmtTransaksi.setString(3, request.getNamaMekanik());
            stmtTransaksi.setString(4, request.getKeluhan());
            stmtTransaksi.setDouble(5, request.getTotalBelanja());
            stmtTransaksi.executeUpdate();

            // Insert into transaksi_detail
            String sqlDetail = "INSERT INTO transaksi_detail (no_faktur, nama_item, jenis, qty, harga_modal, harga_deal, catatan) VALUES (?, ?, ?, ?, " +
                "CASE WHEN ? = 'BARANG' THEN (SELECT harga_beli FROM barang WHERE nama_barang = ?) * qty ELSE 0 END, ?, ?)";
            stmtDetail = conn.prepareStatement(sqlDetail);

            List<TransaksiDetailRequestDTO> details = request.getDetailRequestDTOList();
            for (TransaksiDetailRequestDTO detail : details) {
                stmtDetail.setString(1, noFaktur);
                stmtDetail.setString(2, detail.getJenis().equals("BARANG") ? detail.getNamaBarang() : "Jasa"); // Assuming namaBarang is namaItem
                stmtDetail.setString(3, detail.getJenis());
                stmtDetail.setInt(4, detail.getJumlah());
                stmtDetail.setString(5, detail.getJenis());
                stmtDetail.setString(6, detail.getNamaBarang());
                stmtDetail.setDouble(7, detail.getSubtotal());
                stmtDetail.setString(8, detail.getCatatan());
                stmtDetail.executeUpdate();
            }

            conn.commit();
            return noFaktur;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (stmtTransaksi != null) stmtTransaksi.close();
                if (stmtDetail != null) stmtDetail.close();
                DBConnection.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
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
}
