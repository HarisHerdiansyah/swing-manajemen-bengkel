USE db_bengkel_umkm;

-- 1. Hapus Foreign Key constraint
ALTER TABLE transaksi
DROP FOREIGN KEY transaksi_ibfk_1;

-- 2. Tambah kolom nama_pelanggan
ALTER TABLE transaksi
    ADD COLUMN nama_pelanggan VARCHAR(100) AFTER nopol;

-- 3. Isi data nama_pelanggan dari data existing (optional - jika sudah ada data)
UPDATE transaksi t
    JOIN pelanggan p ON t.nopol = p.nopol
    SET t.nama_pelanggan = p.nama_pemilik;

-- 4. Ubah nopol menjadi nullable (optional - jika ingin tetap simpan nopol sebagai reference)
ALTER TABLE transaksi
    MODIFY COLUMN nopol VARCHAR(20) NULL;
