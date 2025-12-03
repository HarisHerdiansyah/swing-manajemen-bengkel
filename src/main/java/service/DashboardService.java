package service;

import dto.response.DashboardResponseDTO;
import dto.response.TransaksiResponseDTO;
import repository.DashboardRepository;
import util.Response;

import java.util.List;

public class DashboardService {
    private final DashboardRepository dashboardRepository;

    public DashboardService() {
        this.dashboardRepository = new DashboardRepository();
    }

    public Response<DashboardResponseDTO> getDashboardData() {
        try {
            Object[] statistik = dashboardRepository.getStatistikDashboard();
            double totalPendapatan = (double) statistik[0];
            int totalTransaksi = (int) statistik[1];
            int barangStokRendah = (int) statistik[2];

            List<TransaksiResponseDTO> transaksiTerakhir = dashboardRepository.getTransaksiTerakhir(10);

            DashboardResponseDTO dashboard = new DashboardResponseDTO(
                    totalPendapatan,
                    totalTransaksi,
                    barangStokRendah,
                    transaksiTerakhir
            );

            return Response.success("Data dashboard berhasil diambil", dashboard);

        } catch (Exception e) {
            System.err.println("Error service getDashboardData: " + e.getMessage());
            e.printStackTrace();
            return Response.failure("Gagal mengambil data dashboard: " + e.getMessage());
        }
    }
}
