/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

package dto.response;

import dto.request.PelangganRequestDTO;

public class PelangganResponseDTO extends PelangganRequestDTO {
    public PelangganResponseDTO(String nopol, String nama, String noTelp, String jenisMotor) {
        super(nopol, nama, noTelp, jenisMotor);
    }
}

