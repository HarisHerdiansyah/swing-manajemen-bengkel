package util;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

// jdbc:mysql://localhost:3306/db_bengkel_umkm
public class DBConnection {
    public static Connection get() {
        try {
            Dotenv dotenv = Dotenv.load();
            String url = dotenv.get("DB_URL", "jdbc:mysql://localhost:3306/db_bengkel_umkm");
            String username = dotenv.get("DB_USERNAME", "root");
            String password = dotenv.get("DB_PASSWORD", "");

            Connection conn = DriverManager.getConnection(url, username, password);

            if (conn != null) {
                System.out.println("Connected to the database successfully.");
            } else {
                System.out.println("Failed to make connection to the database.");
            }

            return conn;
        } catch (Exception e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (Exception e) {
            System.err.println("Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
