/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

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
        String sql = "SELECT id_mekanik, nama_mekanik, status_aktif FROM mekanik";
        List<MekanikResponseDTO> list = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int idMekanik = rs.getInt("id_mekanik");
                String namaMekanik = rs.getString("nama_mekanik");
                int statusAktif = rs.getInt("status_aktif");
                list.add(new MekanikResponseDTO(idMekanik, namaMekanik, statusAktif));
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
        String sql = "SELECT id_mekanik, nama_mekanik, status_aktif FROM mekanik WHERE id_mekanik = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idMekanik);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id_mekanik");
                String namaMekanik = rs.getString("nama_mekanik");
                int statusAktif = rs.getInt("status_aktif");
                return new MekanikResponseDTO(id, namaMekanik, statusAktif);
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
        String sql = "SELECT id_mekanik, nama_mekanik, status_aktif FROM mekanik WHERE nama_mekanik = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, namaMekanik);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int idMekanik = rs.getInt("id_mekanik");
                String nama = rs.getString("nama_mekanik");
                int statusAktif = rs.getInt("status_aktif");
                return new MekanikResponseDTO(idMekanik, nama, statusAktif);
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
        String sql = "SELECT id_mekanik, nama_mekanik, status_aktif FROM mekanik WHERE nama_mekanik LIKE ?";
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
                int idMekanik = rs.getInt("id_mekanik");
                String nama = rs.getString("nama_mekanik");
                int statusAktif = rs.getInt("status_aktif");
                list.add(new MekanikResponseDTO(idMekanik, nama, statusAktif));
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

    public List<MekanikResponseDTO> getByStatus(int status) {
        String sql = "SELECT id_mekanik, nama_mekanik, status_aktif FROM mekanik WHERE status_aktif = ?";
        List<MekanikResponseDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idMekanik = rs.getInt("id_mekanik");
                String nama = rs.getString("nama_mekanik");
                int statusAktif = rs.getInt("status_aktif");
                list.add(new MekanikResponseDTO(idMekanik, nama, statusAktif));
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

    public boolean delete(String namaMekanik) {
        String sqlGet = "SELECT id_mekanik FROM mekanik WHERE nama_mekanik = ?";
        String sqlDelete = "DELETE FROM mekanik WHERE id_mekanik = ?";
        Connection conn = null;
        PreparedStatement stmtGet = null;
        PreparedStatement stmtDelete = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();

            // Get id_mekanik by nama_mekanik
            stmtGet = conn.prepareStatement(sqlGet);
            stmtGet.setString(1, namaMekanik);
            rs = stmtGet.executeQuery();

            if (rs.next()) {
                int idMekanik = rs.getInt("id_mekanik");

                // Delete by id_mekanik
                stmtDelete = conn.prepareStatement(sqlDelete);
                stmtDelete.setInt(1, idMekanik);
                int rowsAffected = stmtDelete.executeUpdate();
                return rowsAffected > 0;
            }

            return false; // No record found with that name
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmtGet != null) stmtGet.close();
                if (stmtDelete != null) stmtDelete.close();
                DBConnection.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
