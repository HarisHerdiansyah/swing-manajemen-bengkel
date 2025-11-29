package dto.response;

import dto.request.MekanikRequestDTO;

public class MekanikResponseDTO extends MekanikRequestDTO {
    public MekanikResponseDTO(String nama, int status) {
        super(nama, status);
    }
}

