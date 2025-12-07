/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

package dto.request;

public class AdminRequestDTO {
    private final String username;
    private final String password;
    private final String namaLengkap;

    public AdminRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
        this.namaLengkap = null;
    }

    public AdminRequestDTO(String username, String password, String namaLengkap) {
        this.username = username;
        this.password = password;
        this.namaLengkap = namaLengkap;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }
}
