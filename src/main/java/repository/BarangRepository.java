package repository;

import dto.request.BarangRequestDTO;
import dto.response.BarangResponseDTO;
import model.Barang;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangRepository {

    public BarangResponseDTO create(BarangRequestDTO request) {
        String sql = "INSERT INTO barang (kode_barang, nama_barang, stok, harga_beli, harga_jual) VALUES (?, ?, ?, ?, ?)";
        String kodeBarang = generateKodeBarang();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, kodeBarang);
            stmt.setString(2, request.getNamaBarang());
            stmt.setInt(3, request.getStok());
            stmt.setDouble(4, request.getHargaBeli());
            stmt.setDouble(5, request.getHargaJual());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return new BarangResponseDTO(request.getNamaBarang(), request.getStok(), request.getHargaBeli(), request.getHargaJual(), kodeBarang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                DBConnection.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<BarangResponseDTO> getAll() {
        String sql = "SELECT kode_barang, nama_barang, stok, harga_beli, harga_jual FROM barang";
        List<BarangResponseDTO> list = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String kodeBarang = rs.getString("kode_barang");
                String namaBarang = rs.getString("nama_barang");
                int stok = rs.getInt("stok");
                double hargaBeli = rs.getDouble("harga_beli");
                double hargaJual = rs.getDouble("harga_jual");
                list.add(new BarangResponseDTO(namaBarang, stok, hargaBeli, hargaJual, kodeBarang));
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

    public BarangResponseDTO getByKode(String kodeBarang) {
        String sql = "SELECT kode_barang, nama_barang, stok, harga_beli, harga_jual FROM barang WHERE kode_barang = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, kodeBarang);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String namaBarang = rs.getString("nama_barang");
                int stok = rs.getInt("stok");
                double hargaBeli = rs.getDouble("harga_beli");
                double hargaJual = rs.getDouble("harga_jual");
                return new BarangResponseDTO(namaBarang, stok, hargaBeli, hargaJual, kodeBarang);
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
        return null;
    }

    public List<BarangResponseDTO> getByNama(String namaBarang) {
        String sql = "SELECT kode_barang, nama_barang, stok, harga_beli, harga_jual FROM barang WHERE nama_barang LIKE ?";
        List<BarangResponseDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + namaBarang + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String kodeBarang = rs.getString("kode_barang");
                String nama = rs.getString("nama_barang");
                int stok = rs.getInt("stok");
                double hargaBeli = rs.getDouble("harga_beli");
                double hargaJual = rs.getDouble("harga_jual");
                list.add(new BarangResponseDTO(nama, stok, hargaBeli, hargaJual, kodeBarang));
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

    public boolean update(String kodeBarang, BarangRequestDTO request) {
        String sql = "UPDATE barang SET nama_barang = ?, stok = ?, harga_beli = ?, harga_jual = ? WHERE kode_barang = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, request.getNamaBarang());
            stmt.setInt(2, request.getStok());
            stmt.setDouble(3, request.getHargaBeli());
            stmt.setDouble(4, request.getHargaJual());
            stmt.setString(5, kodeBarang);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                DBConnection.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean delete(String kodeBarang) {
        String sql = "DELETE FROM barang WHERE kode_barang = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, kodeBarang);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                DBConnection.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private String generateKodeBarang() {
        String prefix = "BRG-";
        String sql = "SELECT MAX(kode_barang) FROM barang WHERE kode_barang LIKE ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, prefix + "%");
            rs = stmt.executeQuery();

            if (rs.next()) {
                String maxKode = rs.getString(1);
                if (maxKode != null && maxKode.startsWith(prefix)) {
                    String numStr = maxKode.substring(prefix.length());
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
}
