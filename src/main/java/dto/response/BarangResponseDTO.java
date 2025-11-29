package dto.response;

import dto.request.BarangRequestDTO;

public class BarangResponseDTO extends BarangRequestDTO {
    public BarangResponseDTO(String namaBarang, int stok, double hargaBeli, double hargaJual) {
        super(namaBarang, stok, hargaBeli, hargaJual);
    }
}
