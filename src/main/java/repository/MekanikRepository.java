package repository;

import dto.request.MekanikRequestDTO;
import dto.response.MekanikResponseDTO;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MekanikRepository {

    public MekanikResponseDTO create(MekanikRequestDTO request) {
        String sql = "INSERT INTO mekanik (nama_mekanik, status_aktif) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, request.getNama());
            stmt.setInt(2, request.getStatus());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return new MekanikResponseDTO(request.getNama(), request.getStatus());
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

    public List<MekanikResponseDTO> getAll() {
        String sql = "SELECT nama_mekanik, status_aktif FROM mekanik";
        List<MekanikResponseDTO> list = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String namaMekanik = rs.getString("nama_mekanik");
                int statusAktif = rs.getInt("status_aktif");
                list.add(new MekanikResponseDTO(namaMekanik, statusAktif));
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

    public MekanikResponseDTO getById(int idMekanik) {
        String sql = "SELECT nama_mekanik, status_aktif FROM mekanik WHERE id_mekanik = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idMekanik);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String namaMekanik = rs.getString("nama_mekanik");
                int statusAktif = rs.getInt("status_aktif");
                return new MekanikResponseDTO(namaMekanik, statusAktif);
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

    public MekanikResponseDTO getByExactName(String namaMekanik) {
        String sql = "SELECT nama_mekanik, status_aktif FROM mekanik WHERE nama_mekanik = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, namaMekanik);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int statusAktif = rs.getInt("status_aktif");
                return new MekanikResponseDTO(namaMekanik, statusAktif);
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

    public List<MekanikResponseDTO> getByLikeName(String namaMekanik) {
        String sql = "SELECT nama_mekanik, status_aktif FROM mekanik WHERE nama_mekanik LIKE ?";
        List<MekanikResponseDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + namaMekanik + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nama = rs.getString("nama_mekanik");
                int statusAktif = rs.getInt("status_aktif");
                list.add(new MekanikResponseDTO(nama, statusAktif));
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

    public boolean update(int idMekanik, MekanikRequestDTO request) {
        String sql = "UPDATE mekanik SET nama_mekanik = ?, status_aktif = ? WHERE id_mekanik = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, request.getNama());
            stmt.setInt(2, request.getStatus());
            stmt.setInt(3, idMekanik);

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

    public boolean delete(int idMekanik) {
        String sql = "DELETE FROM mekanik WHERE id_mekanik = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idMekanik);

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
