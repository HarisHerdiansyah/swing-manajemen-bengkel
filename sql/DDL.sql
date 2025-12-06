-- 1. Buat Database
CREATE DATABASE IF NOT EXISTS db_bengkel_umkm;
USE db_bengkel_umkm;

-- ==========================================
-- A. TABEL MASTER DATA
-- ==========================================

CREATE TABLE admin (
    id INT AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nama_lengkap VARCHAR(100),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- 2. Tabel Pelanggan
-- Primary Key menggunakan Nopol (String) untuk pencarian cepat
CREATE TABLE pelanggan (
    nopol VARCHAR(20) NOT NULL,
    nama_pemilik VARCHAR(100) NOT NULL,
    no_hp VARCHAR(20),
    jenis_motor VARCHAR(50), -- Misal: Honda Beat, Yamaha Nmax
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (nopol)
) ENGINE=InnoDB;

-- 3. Tabel Barang (Stok Sparepart)
CREATE TABLE barang (
    kode_barang VARCHAR(50) NOT NULL, -- Bisa Barcode atau Kode Manual
    nama_barang VARCHAR(100) NOT NULL,
    stok INT DEFAULT 0,
    harga_beli DECIMAL(10, 2) DEFAULT 0, -- Modal (Penting untuk Laba)
    harga_jual DECIMAL(10, 2) DEFAULT 0, -- Patokan Harga Jual
    PRIMARY KEY (kode_barang)
) ENGINE=InnoDB;

-- 4. Tabel Mekanik
-- Hanya untuk mengisi Dropdown/ComboBox di aplikasi
CREATE TABLE mekanik (
    id_mekanik INT AUTO_INCREMENT,
    nama_mekanik VARCHAR(100) NOT NULL,
    status_aktif TINYINT(1) DEFAULT 1, -- 1 = Aktif, 0 = Resign
    PRIMARY KEY (id_mekanik)
) ENGINE=InnoDB;

-- ==========================================
-- B. TABEL TRANSAKSI (CORE)
-- ==========================================

-- 5. Tabel Transaksi (Header/Nota)
CREATE TABLE transaksi (
    no_faktur VARCHAR(50) NOT NULL,
    tanggal DATETIME DEFAULT CURRENT_TIMESTAMP,
    nopol VARCHAR(20),              -- Tidak lagi FK, hanya reference
    nama_pelanggan VARCHAR(100),    -- SNAPSHOT nama pelanggan
    nama_mekanik VARCHAR(100),
    keluhan TEXT,
    total_belanja DECIMAL(12, 2) DEFAULT 0,
    PRIMARY KEY (no_faktur)
) ENGINE=InnoDB;


-- 6. Tabel Transaksi Detail (Keranjang Belanja)
-- Menyimpan detail Barang DAN Jasa
CREATE TABLE transaksi_detail (
    id_detail INT AUTO_INCREMENT,
    no_faktur VARCHAR(50) NOT NULL, -- FK ke Transaksi
    nama_item VARCHAR(150) NOT NULL,-- Nama Barang atau Nama Jasa (Snapshot)
    jenis ENUM('BARANG', 'JASA') NOT NULL,
    qty INT DEFAULT 1,
    harga_modal DECIMAL(10, 2) DEFAULT 0, -- 0 jika JASA, Harga Beli jika BARANG
    harga_deal DECIMAL(10, 2) DEFAULT 0,  -- Harga Akhir (Bisa diedit/nego)
    catatan TEXT,                   -- Catatan per baris (misal: "Baut diganti baru")
    PRIMARY KEY (id_detail),
    FOREIGN KEY (no_faktur) REFERENCES transaksi(no_faktur) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ==========================================
-- C. CONTOH DATA DUMMY (Opsional - Untuk Test)
-- ==========================================

INSERT INTO mekanik (nama_mekanik) VALUES ('Udin'), ('Asep');

INSERT INTO barang (kode_barang, nama_barang, stok, harga_beli, harga_jual) VALUES
  ('OLI-01', 'Oli Mesin MPX2', 20, 35000, 50000),
  ('BUSI-01', 'Busi Standar', 50, 10000, 15000),
  ('KAMPAS-01', 'Kampas Rem Depan', 10, 25000, 40000);

INSERT INTO pelanggan (nopol, nama_pemilik, no_hp, jenis_motor) VALUES
    ('D 1234 ABC', 'Budi Santoso', '08123456789', 'Honda Vario 125');