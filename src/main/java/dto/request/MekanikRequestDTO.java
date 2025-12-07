/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

package dto.request;

public class MekanikRequestDTO {
    private final String nama;
    private final int status;

    public MekanikRequestDTO(String nama, int status) {
        this.nama = nama;
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public int getStatus() {
        return status;
    }
}

