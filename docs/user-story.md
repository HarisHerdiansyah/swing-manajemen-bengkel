# User Stories: Sistem Manajemen Bengkel (SIM-BENGKEL)

**Aktor Utama:** Admin Bengkel (Kasir/Pengelola)

## Epik 1: Manajemen Inventaris & Data Master

_Fokus pada tabel `barang` dan `mekanik`._

### US-01: Manajemen Stok Sparepart

Sebagai Admin,

Saya ingin menambah data sparepart baru dan memperbarui jumlah stok/harga,

Agar ketersediaan barang di bengkel tercatat akurat dan saya tahu modal harga belinya.

- **Acceptance Criteria:**
    
    - [ ] Bisa melakukan `INSERT` ke tabel `barang` (Kode, Nama, Harga Beli, Harga Jual, Stok).
    - [ ] Bisa melakukan `UPDATE` stok dan harga jual.
    - [ ] Validasi: Kode barang tidak boleh duplikat.

### US-02: Referensi Mekanik

Sebagai Admin,

Saya ingin memiliki daftar nama mekanik yang aktif,

Agar saya bisa memilih nama mereka saat membuat nota transaksi tanpa harus mengetik ulang.

- **Acceptance Criteria:**
    
    - [ ] Data mekanik muncul di `JComboBox` (Dropdown) saat form transaksi dibuka.

---

## Epik 2: Manajemen Pelanggan

_Fokus pada tabel `pelanggan`._

### US-03: Identifikasi Kendaraan (Nopol)

Sebagai Admin,

Saya ingin sistem mengenali pelanggan berdasarkan Nomor Polisi (Nopol),

Agar saya tidak perlu menginput ulang Nama dan No HP untuk pelanggan yang sudah pernah datang.

- **Acceptance Criteria:**
    
    - [ ] Saat Nopol diketik, sistem melakukan query `SELECT` ke tabel `pelanggan`.
    - [ ] Jika **Ditemukan**: Form Nama dan HP terisi otomatis (Auto-fill).
    - [ ] Jika **Tidak Ditemukan**: Form Nama dan HP terbuka untuk diinput manual sebagai pelanggan baru.

---

## Epik 3: Transaksi & Layanan (Core Features)

_Fokus pada tabel `transaksi` dan `transaksi_detail`._

### US-04: Inisiasi Servis & Diagnosa

Sebagai Admin,

Saya ingin membuat faktur baru dengan mencatat keluhan/diagnosa awal dan mekanik yang bertugas,

Agar ada dokumentasi mengenai kondisi awal motor sebelum dikerjakan.

- **Acceptance Criteria:**
    
    - [ ] Input text area untuk `keluhan` (misal: "Rem bunyi, tarikan berat").
    - [ ] Pilihan mekanik tersimpan ke object Transaksi.
    - [ ] Tanggal transaksi otomatis terisi waktu saat ini (`NOW()`).

### US-05: Input Item Sparepart (Barang)

Sebagai Admin,

Saya ingin menambahkan sparepart ke dalam daftar belanja servis,

Agar sistem menghitung biaya dan mengecek ketersediaan stok.

- **Acceptance Criteria:**
    
    - [ ] Admin dapat mencari barang dari master data.
    - [ ] Sistem memvalidasi apakah `stok > 0`. Jika 0, tolak input.
    - [ ] Item masuk ke tabel sementara dengan jenis `BARANG`.
    - [ ] Harga default diambil dari `harga_jual` master barang.

### US-06: Input Jasa Manual (Service)

Sebagai Admin,

Saya ingin mengetik nama jasa perbaikan dan harganya secara manual,

Agar saya bisa mencatat perbaikan unik yang tidak ada di daftar menu standar.

- **Acceptance Criteria:**
    
    - [ ] Input teks bebas untuk nama jasa (tidak ambil dari master).
    - [ ] Item masuk ke tabel sementara dengan jenis `JASA`.
    - [ ] `harga_modal` otomatis di-set `0`.

### US-07: Negosiasi Harga (Editable Price)

Sebagai Admin,

Saya ingin bisa mengubah harga akhir (Deal Price) langsung di tabel keranjang belanja,

Agar saya bisa memberikan diskon atau menaikkan harga jasa sesuai tingkat kesulitan di lapangan.

- **Acceptance Criteria:**
    
    - [ ] Kolom harga pada `JTable` bersifat _editable_.
    - [ ] Saat harga diedit, total belanja (`Grand Total`) harus terhitung ulang secara otomatis.
    - [ ] Nilai yang diedit tersimpan sebagai `harga_deal`.

### US-08: Finalisasi Transaksi (Checkout)

Sebagai Admin,

Saya ingin menyimpan transaksi yang statusnya "Selesai",

Agar stok barang resmi berkurang dan pendapatan tercatat.

- **Acceptance Criteria:**
    
    - [ ] Data Header disimpan ke tabel `transaksi`.
    - [ ] Data Detail disimpan ke tabel `transaksi_detail`.
    - [ ] **PENTING:** Trigger/Logic pengurangan stok di tabel `barang` hanya terjadi di tahap ini.
    - [ ] Form transaksi kembali bersih (Reset) setelah simpan berhasil.

---

## Epik 4: Laporan & Riwayat

_Fokus pada Read Data & Reporting._

### US-09: Riwayat Servis Kendaraan

Sebagai Admin,

Saya ingin melihat daftar tanggal kunjungan dan perbaikan masa lalu saat mencari Nopol,

Agar mekanik bisa mengetahui histori kerusakan motor tersebut.

- **Acceptance Criteria:**
    
    - [ ] Menampilkan tabel pop-up berisi `tanggal`, `keluhan`, dan `total_belanja` berdasarkan `nopol`.

### US-10: Laporan Pendapatan Harian

Sebagai Admin,

Saya ingin melihat ringkasan total uang masuk hari ini,

Agar saya bisa mencocokkan uang di laci kasir (Closing).

- **Acceptance Criteria:**
    
    - [ ] Query `SUM(total_belanja)` dari tabel `transaksi` filter `WHERE date = today`.