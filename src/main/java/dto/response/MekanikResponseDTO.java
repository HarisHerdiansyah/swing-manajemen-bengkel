package dto.response;

import dto.request.MekanikRequestDTO;

public class MekanikResponseDTO extends MekanikRequestDTO {
    private int id;

    public MekanikResponseDTO(int id, String nama, int status) {
        super(nama, status);
        this.id = id;
    }

    public MekanikResponseDTO(String nama, int status) {
        super(nama, status);
        this.id = 0; // Default id if not provided
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

