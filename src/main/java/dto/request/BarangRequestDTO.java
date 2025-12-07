/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

package dto.request;

public class BarangRequestDTO {
    private final String namaBarang;
    private final int stok;
    private final double hargaBeli;
    private final double hargaJual;

    public BarangRequestDTO(String namaBarang, int stok, double hargaBeli, double hargaJual) {
        this.namaBarang = namaBarang;
        this.stok = stok;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public int getStok() {
        return stok;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public double getHargaJual() {
        return hargaJual;
    }
}
