package dto.request;

public class PelangganRequestDTO {
    private final String nopol;
    private final String nama;
    private final String noTelp;
    private final String jenisMotor;

    public PelangganRequestDTO(String nopol, String nama, String noTelp, String jenisMotor) {
        this.nopol = nopol;
        this.nama = nama;
        this.noTelp = noTelp;
        this.jenisMotor = jenisMotor;
    }

    public String getNopol() {
        return nopol;
    }

    public String getNama() {
        return nama;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public String getJenisMotor() {
        return jenisMotor;
    }
}
