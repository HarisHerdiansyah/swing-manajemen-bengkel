/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

package model;

public class Pelanggan {
    private String nopol;
    private String namaPemilik;
    private String noHp;
    private String jenisMotor;

    public Pelanggan() {
    }

    public Pelanggan(String nopol, String namaPemilik, String noHp, String jenisMotor) {
        this.nopol = nopol;
        this.namaPemilik = namaPemilik;
        this.noHp = noHp;
        this.jenisMotor = jenisMotor;
    }

    public String getNopol() {
        return nopol;
    }

    public void setNopol(String nopol) {
        this.nopol = nopol;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getJenisMotor() {
        return jenisMotor;
    }

    public void setJenisMotor(String jenisMotor) {
        this.jenisMotor = jenisMotor;
    }
}
