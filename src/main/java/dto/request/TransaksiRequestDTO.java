package dto.request;

import java.util.ArrayList;
import java.util.List;

public class TransaksiRequestDTO {
    private String nopol;
    private String namaPelangan;
    private String namaMekanik;
    private String keluhan;
    private double totalBelanja;
    private List<TransaksiDetailRequestDTO> detailRequestDTOList;

    public TransaksiRequestDTO() {
        this.detailRequestDTOList = new ArrayList<>();
    }

    public TransaksiRequestDTO(String nopol, String namaPelangan, String namaMekanik, String keluhan, double totalBelanja) {
        this.nopol = nopol;
        this.namaPelangan = namaPelangan;
        this.namaMekanik = namaMekanik;
        this.keluhan = keluhan;
        this.totalBelanja = totalBelanja;
        this.detailRequestDTOList = new ArrayList<>();
    }

    public String getNopol() {
        return nopol;
    }

    public void setNopol(String nopol) {
        this.nopol = nopol;
    }

    public String getNamaPelangan() {
        return namaPelangan;
    }

    public void setNamaPelangan(String namaPelangan) {
        this.namaPelangan = namaPelangan;
    }

    public String getNamaMekanik() {
        return namaMekanik;
    }

    public void setNamaMekanik(String namaMekanik) {
        this.namaMekanik = namaMekanik;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public double getTotalBelanja() {
        return totalBelanja;
    }

    public void setTotalBelanja() {
        totalBelanja = 0.0;
        for (TransaksiDetailRequestDTO detail : detailRequestDTOList) {
            totalBelanja += detail.getSubtotal();
        }
    }

    public List<TransaksiDetailRequestDTO> getDetailRequestDTOList() {
        return detailRequestDTOList;
    }

    public void setDetailRequestDTOList(TransaksiDetailRequestDTO detailRequestDTOList) {
        this.detailRequestDTOList.add(detailRequestDTOList);
    }

    public void removeDetailRequestDTOList(int index) {
        this.detailRequestDTOList.remove(index);
    }

    public void cleanUpDetailRequestDTOList() {
        this.detailRequestDTOList.clear();
    }
}
