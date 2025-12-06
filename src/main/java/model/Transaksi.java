package model;

import java.util.Date;

public class Transaksi {
    private String noFaktur;
    private Date tanggal;
    private String nopolPelanggan;
    private String namaPelanggan;
    private String namaMekanik;
    private String keluhan;
    private double totalBelanja;

    public Transaksi() {
    }

    public Transaksi(String noFaktur, Date tanggal, String nopolPelanggan, String namaPelanggan, String namaMekanik, String keluhan, double totalBelanja) {
        this.noFaktur = noFaktur;
        this.tanggal = tanggal;
        this.nopolPelanggan = nopolPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.namaMekanik = namaMekanik;
        this.keluhan = keluhan;
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

    public String getNopolPelanggan() {
        return nopolPelanggan;
    }

    public void setNopolPelanggan(String nopolPelanggan) {
        this.nopolPelanggan = nopolPelanggan;
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

    public void setTotalBelanja(double totalBelanja) {
        this.totalBelanja = totalBelanja;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }
}
