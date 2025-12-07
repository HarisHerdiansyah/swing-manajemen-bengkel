/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

package dto.response;

public class AdminResponseDTO {
    private int id;
    private String username;
    private String namaLengkap;

    public AdminResponseDTO() {
    }

    public AdminResponseDTO(int id, String username, String namaLengkap) {
        this.id = id;
        this.username = username;
        this.namaLengkap = namaLengkap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
}
