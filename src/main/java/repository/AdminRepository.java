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
}
