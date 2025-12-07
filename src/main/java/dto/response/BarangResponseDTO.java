/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

package dto.response;

import dto.request.BarangRequestDTO;

public class BarangResponseDTO extends BarangRequestDTO {
    private final String kodeBarang;

    public BarangResponseDTO(String namaBarang, int stok, double hargaBeli, double hargaJual, String kodeBarang) {
        super(namaBarang, stok, hargaBeli, hargaJual);
        this.kodeBarang = kodeBarang;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }
}
