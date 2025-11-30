package dto.request;

import java.util.ArrayList;
import java.util.List;

public class TransaksiRequestDTO {
    private final String nopol;
    private final String namaMekanik;
    private final String keluhan;
    private final double totalBelanja;
    private final List<TransaksiDetailRequestDTO> detailRequestDTOList;

    public TransaksiRequestDTO(String nopol, String namaMekanik, String keluhan, double totalBelanja) {
        this.nopol = nopol;
        this.namaMekanik = namaMekanik;
        this.keluhan = keluhan;
        this.totalBelanja = totalBelanja;
        this.detailRequestDTOList = new ArrayList<>();
    }

    public String getNopol() {
        return nopol;
    }

    public String getNamaMekanik() {
        return namaMekanik;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public double getTotalBelanja() {
        return totalBelanja;
    }

    public List<TransaksiDetailRequestDTO> getDetailRequestDTOList() {
        return detailRequestDTOList;
    }

    public void setDetailRequestDTOList(TransaksiDetailRequestDTO detailRequestDTOList) {
        this.detailRequestDTOList.add(detailRequestDTOList);
    }
}
