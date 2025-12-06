CREATE DATABASE IF NOT EXISTS db_bengkel_umkm;
USE db_bengkel_umkm;

CREATE TABLE admin (
    id INT AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nama_lengkap VARCHAR(100),
    PRIMARY KEY (id),
    UNIQUE KEY unique_username (username)
) ENGINE=InnoDB;

CREATE TABLE pelanggan (
    nopol VARCHAR(20) NOT NULL,
    nama_pemilik VARCHAR(100) NOT NULL,
    no_hp VARCHAR(20),
    jenis_motor VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (nopol)
) ENGINE=InnoDB;

CREATE TABLE barang (
    kode_barang VARCHAR(50) NOT NULL,
    nama_barang VARCHAR(100) NOT NULL,
    stok INT DEFAULT 0,
    harga_beli DECIMAL(10, 2) DEFAULT 0,
    harga_jual DECIMAL(10, 2) DEFAULT 0,
    PRIMARY KEY (kode_barang)
) ENGINE=InnoDB;

CREATE TABLE mekanik (
    id_mekanik INT AUTO_INCREMENT,
    nama_mekanik VARCHAR(100) NOT NULL,
    status_aktif TINYINT(1) DEFAULT 1,
    PRIMARY KEY (id_mekanik)
) ENGINE=InnoDB;

CREATE TABLE transaksi (
    no_faktur VARCHAR(50) NOT NULL,
    tanggal DATETIME DEFAULT CURRENT_TIMESTAMP,
    nopol VARCHAR(20),
    nama_pelanggan VARCHAR(100),
    nama_mekanik VARCHAR(100),
    keluhan TEXT,
    total_belanja DECIMAL(12, 2) DEFAULT 0,
    PRIMARY KEY (no_faktur)
) ENGINE=InnoDB;

CREATE TABLE transaksi_detail (
    id_detail INT AUTO_INCREMENT,
    no_faktur VARCHAR(50) NOT NULL,
    nama_item VARCHAR(150) NOT NULL,
    jenis ENUM('BARANG', 'JASA') NOT NULL,
    qty INT DEFAULT 1,
    harga_modal DECIMAL(10, 2) DEFAULT 0,
    harga_deal DECIMAL(10, 2) DEFAULT 0,
    catatan TEXT,
    PRIMARY KEY (id_detail),
    FOREIGN KEY (no_faktur) REFERENCES transaksi(no_faktur) ON DELETE CASCADE
) ENGINE=InnoDB;
