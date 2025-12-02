package dto.request;

public class TransaksiDetailRequestDTO {
    private final String jenis;
    private final String namaBarang;
    private final int jumlah;
    private final double hargaDibayar;
    private final String catatan;

    public TransaksiDetailRequestDTO(String jenis, String namaBarang, int jumlah, double hargaDibayar, String catatan) {
        this.jenis = jenis;
        this.namaBarang = namaBarang;
        this.jumlah = jumlah;
        this.hargaDibayar = hargaDibayar;
        this.catatan = catatan;
    }

    public String getJenis() {
        return jenis;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getHargaDibayar() {
        return hargaDibayar;
    }

    public String getCatatan() {
        return catatan;
    }
}

