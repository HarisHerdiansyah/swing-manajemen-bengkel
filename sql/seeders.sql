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

-- Seeding data untuk tabel pelanggan (diperbanyak)
INSERT INTO pelanggan (nopol, nama_pemilik, no_hp, jenis_motor) VALUES
                                                                    ('B 1234 XYZ', 'Rina Marlina', '083222333444', 'Honda Beat'),
                                                                    ('B 5678 ABC', 'Hendra Gunawan', '083222333445', 'Yamaha NMAX'),
                                                                    ('B 9012 DEF', 'Linda Wijayanti', '083222333446', 'Honda Vario'),
                                                                    ('B 3456 GHI', 'Fahmi Abdullah', '083222333447', 'Suzuki Satria'),
                                                                    ('B 7890 JKL', 'Sari Rahayu', '083222333448', 'Yamaha Mio'),
                                                                    ('B 1111 MNO', 'Dedi Kurniawan', '083222333449', 'Honda Scoopy'),
                                                                    ('B 2222 PQR', 'Maya Sari', '083222333450', 'Yamaha Aerox'),
                                                                    ('B 3333 STU', 'Roni Firmansyah', '083222333451', 'Honda PCX'),
                                                                    ('B 4444 VWX', 'Fitri Handayani', '083222333452', 'Suzuki Nex'),
                                                                    ('B 5555 YZA', 'Andi Pratama', '083222333453', 'Yamaha R15');

-- Seeding data untuk tabel barang (diperbanyak)
INSERT INTO barang (kode_barang, nama_barang, stok, harga_beli, harga_jual) VALUES
                                                                                ('BRG001', 'Oli Mesin 1 Liter', 50, 35000.00, 45000.00),
                                                                                ('BRG002', 'Ban Depan Motor', 20, 150000.00, 200000.00),
                                                                                ('BRG003', 'Kampas Rem Set', 30, 45000.00, 65000.00),
                                                                                ('BRG004', 'Busi NGK', 100, 15000.00, 25000.00),
                                                                                ('BRG005', 'Filter Oli', 40, 20000.00, 30000.00),
                                                                                ('BRG006', 'Ban Belakang Motor', 20, 170000.00, 220000.00),
                                                                                ('BRG007', 'Oli Transmisi', 30, 40000.00, 55000.00),
                                                                                ('BRG008', 'Rantai Motor', 15, 80000.00, 120000.00),
                                                                                ('BRG009', 'Gear Depan', 25, 50000.00, 75000.00),
                                                                                ('BRG010', 'Aki Motor', 10, 200000.00, 280000.00);

-- Seeding data untuk tabel mekanik
INSERT INTO mekanik (nama_mekanik, status_aktif) VALUES
                                                     ('Agus Wijaya', 1),
                                                     ('Rudi Hartono', 1),
                                                     ('Andi Suryanto', 0),
                                                     ('Bambang Setiawan', 1),
                                                     ('Cahyo Nugroho', 1);

-- Seeding data untuk tabel transaksi dengan tanggal yang lebih diverge
INSERT INTO transaksi (no_faktur, tanggal, nopol, nama_pelanggan, nama_mekanik, keluhan, total_belanja) VALUES
                                                                                                            -- Transaksi 30 hari yang lalu
                                                                                                            ('TRX2024001', DATE_SUB(CURDATE(), INTERVAL 30 DAY), 'B 1234 XYZ', 'Rina Marlina', 'Agus Wijaya', 'Ganti oli dan service rutin', 145000.00),
                                                                                                            ('TRX2024002', DATE_SUB(CURDATE(), INTERVAL 28 DAY), 'B 5678 ABC', 'Hendra Gunawan', 'Rudi Hartono', 'Ganti ban depan', 250000.00),

                                                                                                            -- Transaksi 25 hari yang lalu
                                                                                                            ('TRX2024003', DATE_SUB(CURDATE(), INTERVAL 25 DAY), 'B 9012 DEF', 'Linda Wijayanti', 'Bambang Setiawan', 'Ganti kampas rem', 115000.00),

                                                                                                            -- Transaksi 20 hari yang lalu
                                                                                                            ('TRX2024004', DATE_SUB(CURDATE(), INTERVAL 20 DAY), 'B 3456 GHI', 'Fahmi Abdullah', 'Cahyo Nugroho', 'Tune up mesin', 105000.00),
                                                                                                            ('TRX2024005', DATE_SUB(CURDATE(), INTERVAL 20 DAY), 'B 7890 JKL', 'Sari Rahayu', 'Agus Wijaya', 'Ganti filter oli dan busi', 105000.00),

                                                                                                            -- Transaksi 15 hari yang lalu
                                                                                                            ('TRX2024006', DATE_SUB(CURDATE(), INTERVAL 15 DAY), 'B 1111 MNO', 'Dedi Kurniawan', 'Rudi Hartono', 'Service berkala', 320000.00),
                                                                                                            ('TRX2024007', DATE_SUB(CURDATE(), INTERVAL 15 DAY), 'B 2222 PQR', 'Maya Sari', 'Bambang Setiawan', 'Ganti ban belakang', 270000.00),

                                                                                                            -- Transaksi 10 hari yang lalu
                                                                                                            ('TRX2024008', DATE_SUB(CURDATE(), INTERVAL 10 DAY), 'B 3333 STU', 'Roni Firmansyah', 'Agus Wijaya', 'Ganti rantai dan gear', 245000.00),
                                                                                                            ('TRX2024009', DATE_SUB(CURDATE(), INTERVAL 10 DAY), 'B 4444 VWX', 'Fitri Handayani', 'Cahyo Nugroho', 'Ganti aki', 330000.00),

                                                                                                            -- Transaksi 7 hari yang lalu
                                                                                                            ('TRX2024010', DATE_SUB(CURDATE(), INTERVAL 7 DAY), 'B 5555 YZA', 'Andi Pratama', 'Rudi Hartono', 'Service mesin dan ganti oli', 155000.00),
                                                                                                            ('TRX2024011', DATE_SUB(CURDATE(), INTERVAL 7 DAY), 'B 1234 XYZ', 'Rina Marlina', 'Bambang Setiawan', 'Ganti kampas rem depan', 115000.00),

                                                                                                            -- Transaksi 5 hari yang lalu
                                                                                                            ('TRX2024012', DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'B 5678 ABC', 'Hendra Gunawan', 'Agus Wijaya', 'Ganti oli transmisi', 105000.00),
                                                                                                            ('TRX2024013', DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'B 9012 DEF', 'Linda Wijayanti', 'Cahyo Nugroho', 'Tune up dan ganti busi', 95000.00),

                                                                                                            -- Transaksi 3 hari yang lalu
                                                                                                            ('TRX2024014', DATE_SUB(CURDATE(), INTERVAL 3 DAY), 'B 3456 GHI', 'Fahmi Abdullah', 'Rudi Hartono', 'Ganti filter oli', 80000.00),
                                                                                                            ('TRX2024015', DATE_SUB(CURDATE(), INTERVAL 3 DAY), 'B 7890 JKL', 'Sari Rahayu', 'Bambang Setiawan', 'Service rutin', 135000.00),

                                                                                                            -- Transaksi kemarin
                                                                                                            ('TRX2024016', DATE_SUB(CURDATE(), INTERVAL 1 DAY), 'B 1111 MNO', 'Dedi Kurniawan', 'Agus Wijaya', 'Ganti ban depan dan belakang', 470000.00),
                                                                                                            ('TRX2024017', DATE_SUB(CURDATE(), INTERVAL 1 DAY), 'B 2222 PQR', 'Maya Sari', 'Cahyo Nugroho', 'Ganti kampas rem set', 115000.00),

                                                                                                            -- Transaksi hari ini
                                                                                                            ('TRX2024018', CURDATE(), 'B 3333 STU', 'Roni Firmansyah', 'Rudi Hartono', 'Tune up dan ganti oli', 145000.00),
                                                                                                            ('TRX2024019', CURDATE(), 'B 4444 VWX', 'Fitri Handayani', 'Bambang Setiawan', 'Ganti rantai motor', 170000.00),
                                                                                                            ('TRX2024020', CURDATE(), 'B 5555 YZA', 'Andi Pratama', 'Agus Wijaya', 'Service lengkap', 385000.00);

-- Seeding data untuk tabel transaksi_detail
INSERT INTO transaksi_detail (no_faktur, nama_item, jenis, qty, harga_modal, harga_deal, catatan) VALUES
-- Detail TRX2024001
('TRX2024001', 'Oli Mesin 1 Liter', 'BARANG', 2, 35000.00, 45000.00, NULL),
('TRX2024001', 'Jasa Service Rutin', 'JASA', 1, 30000.00, 55000.00, 'Cek keseluruhan motor'),

-- Detail TRX2024002
('TRX2024002', 'Ban Depan Motor', 'BARANG', 1, 150000.00, 200000.00, NULL),
('TRX2024002', 'Jasa Pasang Ban', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail TRX2024003
('TRX2024003', 'Kampas Rem Set', 'BARANG', 1, 45000.00, 65000.00, NULL),
('TRX2024003', 'Jasa Ganti Kampas', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail TRX2024004
('TRX2024004', 'Busi NGK', 'BARANG', 2, 15000.00, 25000.00, NULL),
('TRX2024004', 'Jasa Tune Up', 'JASA', 1, 30000.00, 55000.00, NULL),

-- Detail TRX2024005
('TRX2024005', 'Filter Oli', 'BARANG', 1, 20000.00, 30000.00, NULL),
('TRX2024005', 'Busi NGK', 'BARANG', 1, 15000.00, 25000.00, NULL),
('TRX2024005', 'Jasa Ganti Filter & Busi', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail TRX2024006
('TRX2024006', 'Oli Mesin 1 Liter', 'BARANG', 2, 35000.00, 45000.00, NULL),
('TRX2024006', 'Oli Transmisi', 'BARANG', 1, 40000.00, 55000.00, NULL),
('TRX2024006', 'Filter Oli', 'BARANG', 1, 20000.00, 30000.00, NULL),
('TRX2024006', 'Jasa Service Berkala', 'JASA', 1, 60000.00, 100000.00, 'Full service'),

-- Detail TRX2024007
('TRX2024007', 'Ban Belakang Motor', 'BARANG', 1, 170000.00, 220000.00, NULL),
('TRX2024007', 'Jasa Pasang Ban', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail TRX2024008
('TRX2024008', 'Rantai Motor', 'BARANG', 1, 80000.00, 120000.00, NULL),
('TRX2024008', 'Gear Depan', 'BARANG', 1, 50000.00, 75000.00, NULL),
('TRX2024008', 'Jasa Pasang Rantai & Gear', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail TRX2024009
('TRX2024009', 'Aki Motor', 'BARANG', 1, 200000.00, 280000.00, NULL),
('TRX2024009', 'Jasa Pasang Aki', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail TRX2024010
('TRX2024010', 'Oli Mesin 1 Liter', 'BARANG', 2, 35000.00, 45000.00, NULL),
('TRX2024010', 'Filter Oli', 'BARANG', 1, 20000.00, 30000.00, NULL),
('TRX2024010', 'Jasa Service Mesin', 'JASA', 1, 30000.00, 35000.00, NULL),

-- Detail TRX2024011
('TRX2024011', 'Kampas Rem Set', 'BARANG', 1, 45000.00, 65000.00, NULL),
('TRX2024011', 'Jasa Ganti Kampas', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail TRX2024012
('TRX2024012', 'Oli Transmisi', 'BARANG', 1, 40000.00, 55000.00, NULL),
('TRX2024012', 'Jasa Ganti Oli Transmisi', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail TRX2024013
('TRX2024013', 'Busi NGK', 'BARANG', 2, 15000.00, 25000.00, NULL),
('TRX2024013', 'Jasa Tune Up', 'JASA', 1, 20000.00, 45000.00, NULL),

-- Detail TRX2024014
('TRX2024014', 'Filter Oli', 'BARANG', 1, 20000.00, 30000.00, NULL),
('TRX2024014', 'Jasa Ganti Filter', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail TRX2024015
('TRX2024015', 'Oli Mesin 1 Liter', 'BARANG', 2, 35000.00, 45000.00, NULL),
('TRX2024015', 'Jasa Service Rutin', 'JASA', 1, 20000.00, 45000.00, NULL),

-- Detail TRX2024016
('TRX2024016', 'Ban Depan Motor', 'BARANG', 1, 150000.00, 200000.00, NULL),
('TRX2024016', 'Ban Belakang Motor', 'BARANG', 1, 170000.00, 220000.00, NULL),
('TRX2024016', 'Jasa Pasang 2 Ban', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail TRX2024017
('TRX2024017', 'Kampas Rem Set', 'BARANG', 1, 45000.00, 65000.00, NULL),
('TRX2024017', 'Jasa Ganti Kampas', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail TRX2024018
('TRX2024018', 'Oli Mesin 1 Liter', 'BARANG', 2, 35000.00, 45000.00, NULL),
('TRX2024018', 'Jasa Tune Up', 'JASA', 1, 30000.00, 55000.00, NULL),

-- Detail TRX2024019
('TRX2024019', 'Rantai Motor', 'BARANG', 1, 80000.00, 120000.00, NULL),
('TRX2024019', 'Jasa Pasang Rantai', 'JASA', 1, 20000.00, 50000.00, NULL),

-- Detail TRX2024020
('TRX2024020', 'Oli Mesin 1 Liter', 'BARANG', 2, 35000.00, 45000.00, NULL),
('TRX2024020', 'Oli Transmisi', 'BARANG', 1, 40000.00, 55000.00, NULL),
('TRX2024020', 'Filter Oli', 'BARANG', 1, 20000.00, 30000.00, NULL),
('TRX2024020', 'Kampas Rem Set', 'BARANG', 1, 45000.00, 65000.00, NULL),
('TRX2024020', 'Jasa Service Lengkap', 'JASA', 1, 80000.00, 100000.00, 'Full service + tune up');
