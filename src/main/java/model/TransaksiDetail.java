/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

package model;

public class TransaksiDetail {
    private String id;
    private String noFaktur;
    private String namaItem;
    private String jenis;
    private int qty;
    private double hargaModal;
    private double hargaDeal;
    private String catatan;

    public TransaksiDetail() {
    }

    public TransaksiDetail(String id, String noFaktur, String namaItem, String jenis, int qty, double hargaModal, double hargaDeal, String catatan) {
        this.id = id;
        this.noFaktur = noFaktur;
        this.namaItem = namaItem;
        this.jenis = jenis;
        this.qty = qty;
        this.hargaModal = hargaModal;
        this.hargaDeal = hargaDeal;
        this.catatan = catatan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoFaktur() {
        return noFaktur;
    }

    public void setNoFaktur(String noFaktur) {
        this.noFaktur = noFaktur;
    }

    public String getNamaItem() {
        return namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getHargaModal() {
        return hargaModal;
    }

    public void setHargaModal(double hargaModal) {
        this.hargaModal = hargaModal;
    }

    public double getHargaDeal() {
        return hargaDeal;
    }

    public void setHargaDeal(double hargaDeal) {
        this.hargaDeal = hargaDeal;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}

/*
// Logic hitung profit per item (untuk laporan nanti)
    // Jika Jasa: Profit = (HargaDeal * 0.3) -> sisa 70% buat mekanik (sesuai brief)
    // Jika Barang: Profit = HargaDeal - HargaModal
    public double getKeuntunganBersih() {
        if (this.jenis.equalsIgnoreCase("JASA")) {
            return this.getSubtotal() * 0.3; // Bengkel ambil 30%, Mekanik 70%
        } else {
            return this.getSubtotal() - (this.hargaModal * this.qty);
        }
    }

 */