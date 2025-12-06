package dto.response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TransaksiResponseDTO {
    private String noFaktur;
    private Date tanggal;
    private String nopol;
    private String namaPelanggan;
    private String namaMekanik;
    private double totalBelanja;

    public TransaksiResponseDTO(String noFaktur, Date tanggal, String nopol, String namaPelanggan, String namaMekanik, double totalBelanja) {
        this.noFaktur = noFaktur;
        this.tanggal = tanggal;
        this.nopol = nopol;
        this.namaPelanggan = namaPelanggan;
        this.namaMekanik = namaMekanik;
        this.totalBelanja = totalBelanja;
    }

    public String getNoFaktur() {
        return noFaktur;
    }

    public void setNoFaktur(String noFaktur) {
        this.noFaktur = noFaktur;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getNamaMekanik() {
        return namaMekanik;
    }

    public void setNamaMekanik(String namaMekanik) {
        this.namaMekanik = namaMekanik;
    }

    public double getTotalBelanja() {
        return totalBelanja;
    }

    public void setTotalBelanja(double totalBelanja) {
        this.totalBelanja = totalBelanja;
    }

    public String getNopol() {
        return nopol;
    }

    public void setNopol(String nopol) {
        this.nopol = nopol;
    }

    public String getTanggalFormatted() {
        if (tanggal == null) {
            return "-";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("id-ID"));
        return sdf.format(tanggal);
    }
}
