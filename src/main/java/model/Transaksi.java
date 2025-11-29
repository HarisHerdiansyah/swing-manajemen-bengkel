package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Transaksi {
    private String noFaktur;
    private Date tanggal;
    private String nopolPelanggan;
    private String namaMekanik;
    private String keluhan;
    private double totalBelanja;
    private List<TransaksiDetail> listDetail = new ArrayList<>();

    public Transaksi() {
    }

    public Transaksi(String noFaktur, Date tanggal, String nopolPelanggan, String namaMekanik, String keluhan, double totalBelanja) {
        this.noFaktur = noFaktur;
        this.tanggal = tanggal;
        this.nopolPelanggan = nopolPelanggan;
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

    public void setListDetail(List<TransaksiDetail> listDetail) {
        this.listDetail = listDetail;
    }
}


/**
 * // Method bantuan untuk hitung total otomatis di aplikasi
 *     public void hitungTotal() {
 *         double total = 0;
 *         for (TransaksiDetail item : listDetail) {
 *             total += item.getSubtotal();
 *         }
 *         this.totalBelanja = total;
 *     }
 */