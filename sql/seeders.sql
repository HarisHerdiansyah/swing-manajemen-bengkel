-- Disable foreign key checks untuk menghindari error saat delete
SET FOREIGN_KEY_CHECKS = 0;

-- Hapus data existing
TRUNCATE TABLE transaksi_detail;
TRUNCATE TABLE transaksi;
TRUNCATE TABLE barang;
TRUNCATE TABLE mekanik;
TRUNCATE TABLE pelanggan;
TRUNCATE TABLE admin;

-- Enable foreign key checks kembali
SET FOREIGN_KEY_CHECKS = 1;

-- Seeding data untuk tabel admin
INSERT INTO admin (username, password, nama_lengkap) VALUES
    ('admin1', '$2a$12$VfsKzJNAusTzgN3qFqgT9ONepfZn8Km0yE8qrOdLSTgFPMxpjhjkS', 'Ahmad Sudrajat'),
    ('admin2', '$2a$12$VfsKzJNAusTzgN3qFqgT9ONepfZn8Km0yE8qrOdLSTgFPMxpjhjkS', 'Siti Nurhaliza'),
    ('admin3', '$2a$12$VfsKzJNAusTzgN3qFqgT9ONepfZn8Km0yE8qrOdLSTgFPMxpjhjkS', 'Budi Santoso'),
    ('admin4', '$2a$12$VfsKzJNAusTzgN3qFqgT9ONepfZn8Km0yE8qrOdLSTgFPMxpjhjkS', 'Dewi Lestari'),
    ('admin5', '$2a$12$VfsKzJNAusTzgN3qFqgT9ONepfZn8Km0yE8qrOdLSTgFPMxpjhjkS', 'Eko Prasetyo');

-- Seeding data untuk tabel pelanggan
INSERT INTO pelanggan (nopol, nama_pemilik, no_hp, jenis_motor) VALUES
    ('B 1234 XYZ', 'Rina Marlina', '083222333444', 'Honda Beat'),
    ('B 5678 ABC', 'Hendra Gunawan', '083222333445', 'Yamaha NMAX'),
    ('B 9012 DEF', 'Linda Wijayanti', '083222333446', 'Honda Vario'),
    ('B 3456 GHI', 'Fahmi Abdullah', '083222333447', 'Suzuki Satria'),
    ('B 7890 JKL', 'Sari Rahayu', '083222333448', 'Yamaha Mio');

-- Seeding data untuk tabel barang
INSERT INTO barang (kode_barang, nama_barang, stok, harga_beli, harga_jual) VALUES
    ('BRG001', 'Oli Mesin 1 Liter', 50, 35000.00, 45000.00),
    ('BRG002', 'Ban Depan Motor', 20, 150000.00, 200000.00),
    ('BRG003', 'Kampas Rem Set', 30, 45000.00, 65000.00),
    ('BRG004', 'Busi NGK', 100, 15000.00, 25000.00),
    ('BRG005', 'Filter Oli', 40, 20000.00, 30000.00);

-- Seeding data untuk tabel mekanik
INSERT INTO mekanik (nama_mekanik, status_aktif) VALUES
    ('Agus Wijaya', 1),
    ('Rudi Hartono', 1),
    ('Andi Suryanto', 0),
    ('Bambang Setiawan', 1),
    ('Cahyo Nugroho', 1);

-- Seeding data untuk tabel transaksi
INSERT INTO transaksi (no_faktur, tanggal, nopol, nama_pelanggan, nama_mekanik, keluhan, total_belanja) VALUES
    ('TRX20240101001', '2024-01-15 09:30:00', 'B 1234 XYZ', 'Rina Marlina', 'Agus Wijaya', 'Ganti oli dan service rutin', 145000.00),
    ('TRX20240101002', '2024-01-16 10:15:00', 'B 5678 ABC', 'Hendra Gunawan', 'Rudi Hartono', 'Ganti ban depan', 250000.00),
    ('TRX20240101003', '2024-01-17 11:00:00', 'B 9012 DEF', 'Linda Wijayanti', 'Bambang Setiawan', 'Ganti kampas rem', 115000.00),
    ('TRX20240101004', '2024-01-18 14:30:00', 'B 3456 GHI', 'Fahmi Abdullah', 'Cahyo Nugroho', 'Tune up mesin', 105000.00),
    ('TRX20240101005', '2024-01-19 15:45:00', 'B 7890 JKL', 'Sari Rahayu', 'Agus Wijaya', 'Ganti filter oli dan busi', 105000.00);

-- Seeding data untuk tabel transaksi_detail
INSERT INTO transaksi_detail (no_faktur, nama_item, jenis, qty, harga_modal, harga_deal, catatan) VALUES
-- Detail untuk TRX20240101001
('TRX20240101001', 'Oli Mesin 1 Liter', 'BARANG', 2, 35000.00, 45000.00, NULL),
('TRX20240101001', 'Jasa Service Rutin', 'JASA', 1, 30000.00, 55000.00, 'Cek keseluruhan motor'),

-- Detail untuk TRX20240101002
('TRX20240101002', 'Ban Depan Motor', 'BARANG', 1, 150000.00, 200000.00, NULL),
('TRX20240101002', 'Jasa Pasang Ban', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail untuk TRX20240101003
('TRX20240101003', 'Kampas Rem Set', 'BARANG', 1, 45000.00, 65000.00, NULL),
('TRX20240101003', 'Jasa Ganti Kampas', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail untuk TRX20240101004
('TRX20240101004', 'Busi NGK', 'BARANG', 2, 15000.00, 25000.00, NULL),
('TRX20240101004', 'Jasa Tune Up', 'JASA', 1, 30000.00, 55000.00, NULL),

-- Detail untuk TRX20240101005
('TRX20240101005', 'Filter Oli', 'BARANG', 1, 20000.00, 30000.00, NULL),
('TRX20240101005', 'Busi NGK', 'BARANG', 1, 15000.00, 25000.00, NULL),
('TRX20240101005', 'Jasa Ganti Filter & Busi', 'JASA', 1, 20000.00, 50000.00, NULL);
