package model;

public class TransaksiDetail {
    private String id;
    private String noFaktur;
    private String namaItem;
    private String jenis;
    private int qty;
    private double hargaModal;
    private double hargaDeal;

    public TransaksiDetail() {
    }

    public TransaksiDetail(String id, String noFaktur, String namaItem, String jenis, int qty, double hargaModal, double hargaDeal) {
        this.id = id;
        this.noFaktur = noFaktur;
        this.namaItem = namaItem;
        this.jenis = jenis;
        this.qty = qty;
        this.hargaModal = hargaModal;
        this.hargaDeal = hargaDeal;
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