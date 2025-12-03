package dto.response;

import java.util.List;

public class DashboardResponseDTO {
    private final double totalPendapatan;
    private final int totalTransaksi;
    private final int barangStokRendah;
    private final List<TransaksiResponseDTO> transaksiTerakhir;

    public DashboardResponseDTO(double totalPendapatan, int totalTransaksi, int barangStokRendah, List<TransaksiResponseDTO> transaksiTerakhir) {
        this.totalPendapatan = totalPendapatan;
        this.totalTransaksi = totalTransaksi;
        this.barangStokRendah = barangStokRendah;
        this.transaksiTerakhir = transaksiTerakhir;
    }

    public double getTotalPendapatan() {
        return totalPendapatan;
    }

    public int getTotalTransaksi() {
        return totalTransaksi;
    }

    public int getBarangStokRendah() {
        return barangStokRendah;
    }

    public List<TransaksiResponseDTO> getTransaksiTerakhir() {
        return transaksiTerakhir;
    }
}

