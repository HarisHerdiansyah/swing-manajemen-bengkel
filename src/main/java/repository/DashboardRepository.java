package repository;

import dto.response.TransaksiResponseDTO;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DashboardRepository {
    public Object[] getStatistikDashboard() {
        String sql = "SELECT " +
                "    (SELECT COALESCE(SUM(total_belanja), 0) FROM transaksi WHERE DATE(tanggal) = CURDATE()) AS total_pendapatan, " +
                "    (SELECT COUNT(*) FROM transaksi WHERE DATE(tanggal) = CURDATE()) AS total_transaksi, " +
                "    (SELECT COUNT(*) FROM barang WHERE stok < 5) AS barang_stok_rendah";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                double totalPendapatan = rs.getDouble("total_pendapatan");
                int totalTransaksi = rs.getInt("total_transaksi");
                int barangStokRendah = rs.getInt("barang_stok_rendah");

                return new Object[]{totalPendapatan, totalTransaksi, barangStokRendah};
            }

            return new Object[]{0.0, 0, 0};

        } catch (Exception e) {
            System.err.println("Error mengambil statistik dashboard: " + e.getMessage());
            e.printStackTrace();
            return new Object[]{0.0, 0, 0};
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) DBConnection.close(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<TransaksiResponseDTO> getTransaksiTerakhir(int limit) {
        String sql = "SELECT t.no_faktur, t.tanggal, t.nopol, p.nama_pemilik, t.nama_mekanik, t.total_belanja " +
                "FROM transaksi t " +
                "LEFT JOIN pelanggan p ON t.nopol = p.nopol " +
                "ORDER BY t.tanggal DESC " +
                "LIMIT ?";

        List<TransaksiResponseDTO> listTransaksi = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.get();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            rs = ps.executeQuery();

            while (rs.next()) {
                TransaksiResponseDTO transaksi = new TransaksiResponseDTO(
                        rs.getString("no_faktur"),
                        rs.getTimestamp("tanggal"),
                        rs.getString("nopol"),
                        rs.getString("nama_pemilik"),
                        rs.getString("nama_mekanik"),
                        rs.getDouble("total_belanja")
                );
                listTransaksi.add(transaksi);
            }

        } catch (Exception e) {
            System.err.println("Error mengambil transaksi terakhir: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) DBConnection.close(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listTransaksi;
    }
}
