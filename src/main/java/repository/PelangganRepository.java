package repository;

import dto.request.PelangganRequestDTO;
import dto.response.PelangganResponseDTO;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PelangganRepository {

    public PelangganResponseDTO create(PelangganRequestDTO request) {
        String sql = "INSERT INTO pelanggan (nopol, nama_pemilik) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, request.getNopol());
            stmt.setString(2, request.getNama());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return new PelangganResponseDTO(request.getNopol(), request.getNama(), null, null);
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

    public List<PelangganResponseDTO> getAll() {
        String sql = "SELECT nopol, nama_pemilik, no_hp, jenis_motor FROM pelanggan";
        List<PelangganResponseDTO> list = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String nopol = rs.getString("nopol");
                String namaPemilik = rs.getString("nama_pemilik");
                String noHp = rs.getString("no_hp");
                String jenisMotor = rs.getString("jenis_motor");
                list.add(new PelangganResponseDTO(nopol, namaPemilik, noHp, jenisMotor));
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

    public PelangganResponseDTO getByNopol(String nopol) {
        String sql = "SELECT nopol, nama_pemilik, no_hp, jenis_motor FROM pelanggan WHERE nopol = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nopol);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String namaPemilik = rs.getString("nama_pemilik");
                String noHp = rs.getString("no_hp");
                String jenisMotor = rs.getString("jenis_motor");
                return new PelangganResponseDTO(nopol, namaPemilik, noHp, jenisMotor);
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

    public PelangganResponseDTO getByExactName(String namaPemilik) {
        String sql = "SELECT nopol, nama_pemilik, no_hp, jenis_motor FROM pelanggan WHERE nama_pemilik = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, namaPemilik);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String nopol = rs.getString("nopol");
                String noHp = rs.getString("no_hp");
                String jenisMotor = rs.getString("jenis_motor");
                return new PelangganResponseDTO(nopol, namaPemilik, noHp, jenisMotor);
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

    public List<PelangganResponseDTO> getByLikeName(String namaPemilik) {
        String sql = "SELECT nopol, nama_pemilik, no_hp, jenis_motor FROM pelanggan WHERE nama_pemilik LIKE ?";
        List<PelangganResponseDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + namaPemilik + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nopol = rs.getString("nopol");
                String nama = rs.getString("nama_pemilik");
                String noHp = rs.getString("no_hp");
                String jenisMotor = rs.getString("jenis_motor");
                list.add(new PelangganResponseDTO(nopol, nama, noHp, jenisMotor));
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

    public boolean update(String nopol, PelangganRequestDTO request) {
        String sql = "UPDATE pelanggan SET nama_pemilik = ? WHERE nopol = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, request.getNama());
            stmt.setString(2, nopol);

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

    public boolean delete(String nopol) {
        String sql = "DELETE FROM pelanggan WHERE nopol = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nopol);

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
}
