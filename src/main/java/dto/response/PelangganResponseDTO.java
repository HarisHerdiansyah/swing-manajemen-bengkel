package dto.response;

import dto.request.PelangganRequestDTO;

public class PelangganResponseDTO extends PelangganRequestDTO {
    public PelangganResponseDTO(String nopol, String nama, String noTelp, String jenisMotor) {
        super(nopol, nama, noTelp, jenisMotor);
    }
}

