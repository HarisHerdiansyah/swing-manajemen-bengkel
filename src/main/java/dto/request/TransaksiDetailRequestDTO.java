package dto.request;

public class TransaksiDetailRequestDTO {
    private final String jenis;
    private final String namaBarang;
    private final int jumlah;
    private final double hargaBarang;
    private final double subtotal;
    private final String catatan;

    public TransaksiDetailRequestDTO(String jenis, String namaBarang, int jumlah, double hargaBarang, double subtotal, String catatan) {
        this.jenis = jenis;
        this.namaBarang = namaBarang;
        this.jumlah = jumlah;
        this.hargaBarang = hargaBarang;
        this.subtotal = subtotal;
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

    public double getHargaBarang() {
        return hargaBarang;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String getCatatan() {
        return catatan;
    }
}

