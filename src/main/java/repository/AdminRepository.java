package repository;

import dto.request.AdminRequestDTO;
import model.Admin;
import util.DBConnection;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class AdminRepository {

    public Admin login(AdminRequestDTO request) {
        String sql = "SELECT id, username, password, nama_lengkap FROM admin WHERE username = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, request.getUsername());
            rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                if (BCrypt.checkpw(request.getPassword(), hashedPassword)) {
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    String namaLengkap = rs.getString("nama_lengkap");
                    return new Admin(id, username, namaLengkap);
                }
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

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean createAdmin(AdminRequestDTO request) {
        String sql = "INSERT INTO admin (username, password, nama_lengkap) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, request.getUsername());
            stmt.setString(2, hashPassword(request.getPassword()));
            stmt.setString(3, request.getNamaLengkap());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                DBConnection.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public java.util.List<Admin> getAllAdmins() {
        String sql = "SELECT id, username, nama_lengkap FROM admin ORDER BY id";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        java.util.List<Admin> admins = new java.util.ArrayList<>();

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String namaLengkap = rs.getString("nama_lengkap");
                admins.add(new Admin(id, username, namaLengkap));
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
        return admins;
    }

    public Admin getAdminById(int id) {
        String sql = "SELECT id, username, nama_lengkap FROM admin WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                String namaLengkap = rs.getString("nama_lengkap");
                return new Admin(id, username, namaLengkap);
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

    public boolean updateAdmin(int id, AdminRequestDTO request) {
        StringBuilder sql = new StringBuilder("UPDATE admin SET username = ?, nama_lengkap = ?");
        boolean updatePassword = request.getPassword() != null && !request.getPassword().trim().isEmpty();

        if (updatePassword) {
            sql.append(", password = ?");
        }
        sql.append(" WHERE id = ?");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql.toString());
            stmt.setString(1, request.getUsername());
            stmt.setString(2, request.getNamaLengkap());

            if (updatePassword) {
                stmt.setString(3, hashPassword(request.getPassword()));
                stmt.setInt(4, id);
            } else {
                stmt.setInt(3, id);
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                DBConnection.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean deleteAdmin(String username) {
        String sql = "DELETE FROM admin WHERE username = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                DBConnection.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Admin getAdminByUsername(String username) {
        String sql = "SELECT id, username, nama_lengkap FROM admin WHERE username = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String namaLengkap = rs.getString("nama_lengkap");
                return new Admin(id, username, namaLengkap);
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

    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM admin WHERE username = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
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
        return false;
    }

    public boolean isUsernameExistsExcludingId(String username, int excludeId) {
        String sql = "SELECT COUNT(*) FROM admin WHERE username = ? AND id != ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setInt(2, excludeId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
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
        return false;
    }
}
