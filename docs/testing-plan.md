# Dokumen Rencana Testing - Sistem Manajemen Bengkel (SIM-BENGKEL)

## 📋 Daftar Isi
1. [Ringkasan Proyek](#ringkasan-proyek)
2. [Strategi Testing](#strategi-testing)
3. [Unit Testing](#unit-testing)
4. [Integration Testing](#integration-testing)
5. [System Testing](#system-testing)
6. [User Acceptance Testing (UAT)](#user-acceptance-testing-uat)
7. [Performance Testing](#performance-testing)
8. [Security Testing](#security-testing)
9. [Test Cases Detail](#test-cases-detail)
10. [Tools dan Framework](#tools-dan-framework)

---

## 🎯 Ringkasan Proyek

**Nama Proyek:** Sistem Manajemen Bengkel (SIM-BENGKEL)  
**Teknologi:** Java Swing, MySQL, Maven  
**Arsitektur:** MVC (Model-View-Controller) dengan layered architecture (Repository-Service-View)

### Komponen Utama:
- **Database:** MySQL (db_bengkel_umkm)
- **Framework UI:** Java Swing (NetBeans Form)
- **Dependencies:** 
  - MySQL Connector (8.0.33)
  - jBCrypt (0.4) - Password hashing
  - java-dotenv (5.2.2) - Environment configuration
  - AbsoluteLayout (NetBeans)

### Modul Aplikasi:
1. **Autentikasi** - Login Admin
2. **Dashboard** - Statistik dan ringkasan
3. **Manajemen Barang** - CRUD sparepart/inventory
4. **Manajemen Pelanggan** - CRUD data pelanggan
5. **Manajemen Mekanik** - CRUD data mekanik
6. **Transaksi** - Pembuatan nota servis dan penjualan
7. **Histori Transaksi** - Riwayat transaksi

---

## 📊 Strategi Testing

### Piramida Testing
```
        /\
       /  \
      / UI \          ← System Testing (10%)
     /------\
    /        \
   / Integ.  \        ← Integration Testing (20%)
  /----------\
 /            \
/  Unit Tests \       ← Unit Testing (70%)
----------------
```

### Pendekatan Testing:
- **Bottom-Up Testing:** Mulai dari unit terkecil (Repository) → Service → View
- **Test-Driven Development (TDD):** Tulis test sebelum implementasi (recommended)
- **Continuous Testing:** Jalankan test setiap kali ada perubahan kode

---

## 🔬 Unit Testing

Unit testing fokus pada testing komponen individual secara terisolasi.

### 1. Repository Layer Testing

#### 1.1 AdminRepository
**File:** `AdminRepository.java`

| Test Case ID | Deskripsi | Input | Expected Output |
|--------------|-----------|-------|-----------------|
| UT-ADMIN-001 | Login dengan kredensial valid | Username: "admin", Password: "admin123" | Return Admin object dengan data lengkap |
| UT-ADMIN-002 | Login dengan username tidak ada | Username: "usernotexist" | Return null |
| UT-ADMIN-003 | Login dengan password salah | Username: "admin", Password: "wrongpass" | Return null |
| UT-ADMIN-004 | Login dengan null username | Username: null | Handle exception/return null |
| UT-ADMIN-005 | Login dengan empty string | Username: "", Password: "" | Return null |
| UT-ADMIN-006 | Hash password | Password: "password123" | Return hashed string (BCrypt format) |
| UT-ADMIN-007 | Verify hashed password | Original: "password123" | BCrypt.checkpw returns true |

**Mock Objects:** Database connection (menggunakan H2 in-memory atau mock JDBC)

---

#### 1.2 BarangRepository
**File:** `BarangRepository.java`

| Test Case ID | Deskripsi | Input | Expected Output |
|--------------|-----------|-------|-----------------|
| UT-BARANG-001 | Create barang baru | BarangRequestDTO valid | Return BarangResponseDTO dengan kode_barang ter-generate |
| UT-BARANG-002 | Create barang dengan nama duplikat | Nama barang yang sudah ada | Handle constraint exception |
| UT-BARANG-003 | Get all barang | - | Return List<BarangResponseDTO> |
| UT-BARANG-004 | Get all barang (empty database) | - | Return empty list |
| UT-BARANG-005 | Get barang by kode (exist) | Kode: "OLI-01" | Return BarangResponseDTO |
| UT-BARANG-006 | Get barang by kode (not exist) | Kode: "NOTEXIST" | Return null |
| UT-BARANG-007 | Get barang by nama (partial match) | Nama: "Oli" | Return List dengan items yang match |
| UT-BARANG-008 | Update barang (exist) | Kode valid, BarangRequestDTO | Return true |
| UT-BARANG-009 | Update barang (not exist) | Kode invalid | Return false |
| UT-BARANG-010 | Delete barang (exist) | Kode: "TEST-01" | Return true |
| UT-BARANG-011 | Delete barang (not exist) | Kode: "NOTEXIST" | Return false |
| UT-BARANG-012 | Delete barang yang ada di transaksi | Kode yang sudah digunakan | Handle foreign key constraint |
| UT-BARANG-013 | Generate kode barang | - | Return unique kode (format sesuai pattern) |
| UT-BARANG-014 | Update dengan stok negatif | Stok: -5 | Handle validation |
| UT-BARANG-015 | Update dengan harga negatif | Harga: -10000 | Handle validation |

---

#### 1.3 PelangganRepository
**File:** `PelangganRepository.java`

| Test Case ID | Deskripsi | Input | Expected Output |
|--------------|-----------|-------|-----------------|
| UT-PELANGGAN-001 | Create pelanggan baru | PelangganRequestDTO valid | Return PelangganResponseDTO |
| UT-PELANGGAN-002 | Create dengan nopol duplikat | Nopol yang sudah ada | Handle unique constraint |
| UT-PELANGGAN-003 | Get all pelanggan | - | Return List<PelangganResponseDTO> |
| UT-PELANGGAN-004 | Get pelanggan by nopol (exist) | Nopol: "D 1234 ABC" | Return PelangganResponseDTO |
| UT-PELANGGAN-005 | Get pelanggan by nopol (not exist) | Nopol: "NOTEXIST" | Return null |
| UT-PELANGGAN-006 | Get pelanggan by nama exact | Nama: "Budi Santoso" | Return PelangganResponseDTO |
| UT-PELANGGAN-007 | Get pelanggan by nama partial | Nama: "Budi" | Return List dengan match items |
| UT-PELANGGAN-008 | Update pelanggan | Nopol valid, PelangganRequestDTO | Return true |
| UT-PELANGGAN-009 | Delete pelanggan (no transaksi) | Nopol: "D 9999 ZZZ" | Return true |
| UT-PELANGGAN-010 | Delete pelanggan (ada transaksi) | Nopol dengan foreign key | Handle constraint |
| UT-PELANGGAN-011 | Validasi format nopol | Nopol: "INVALID" | Handle validation |
| UT-PELANGGAN-012 | Validasi no HP | No HP: "abc123" | Handle validation |

---

#### 1.4 MekanikRepository
**File:** `MekanikRepository.java`

| Test Case ID | Deskripsi | Input | Expected Output |
|--------------|-----------|-------|-----------------|
| UT-MEKANIK-001 | Create mekanik baru | MekanikRequestDTO valid | Return MekanikResponseDTO |
| UT-MEKANIK-002 | Get all mekanik | - | Return List<MekanikResponseDTO> |
| UT-MEKANIK-003 | Get mekanik by ID (exist) | ID: 1 | Return MekanikResponseDTO |
| UT-MEKANIK-004 | Get mekanik by ID (not exist) | ID: 999 | Return null |
| UT-MEKANIK-005 | Get mekanik by nama exact | Nama: "Udin" | Return MekanikResponseDTO |
| UT-MEKANIK-006 | Get mekanik by nama partial | Nama: "Ud" | Return List dengan match items |
| UT-MEKANIK-007 | Update mekanik | ID valid, MekanikRequestDTO | Return true |
| UT-MEKANIK-008 | Update status aktif mekanik | ID: 1, status: 0 (non-aktif) | Return true |
| UT-MEKANIK-009 | Delete mekanik | ID: 1 | Return true |
| UT-MEKANIK-010 | Get only active mekanik | - | Return List dengan status_aktif=1 |

---

#### 1.5 TransaksiRepository
**File:** `TransaksiRepository.java`

| Test Case ID | Deskripsi | Input | Expected Output |
|--------------|-----------|-------|-----------------|
| UT-TRANSAKSI-001 | Create transaksi valid (barang saja) | TransaksiRequestDTO dengan barang | Return no_faktur |
| UT-TRANSAKSI-002 | Create transaksi valid (jasa saja) | TransaksiRequestDTO dengan jasa | Return no_faktur |
| UT-TRANSAKSI-003 | Create transaksi mixed (barang + jasa) | TransaksiRequestDTO kombinasi | Return no_faktur |
| UT-TRANSAKSI-004 | Create transaksi - stok tidak cukup | Qty > stok tersedia | Throw SQLException dengan message stok |
| UT-TRANSAKSI-005 | Create transaksi - barang tidak ada | Nama barang tidak exist | Throw SQLException |
| UT-TRANSAKSI-006 | Create transaksi - pelanggan tidak ada | Nopol tidak exist di DB | Throw SQLException (FK constraint) |
| UT-TRANSAKSI-007 | Validate stok sebelum transaksi | List detail dengan stok cukup | Return null (no error) |
| UT-TRANSAKSI-008 | Validate stok - insufficient | List detail dengan stok kurang | Return error message |
| UT-TRANSAKSI-009 | Generate no faktur | - | Return unique faktur (format: TRX-DDMMYY-XXX) |
| UT-TRANSAKSI-010 | Get all transaksi | - | Return List<TransaksiResponseDTO> |
| UT-TRANSAKSI-011 | Transaction rollback on error | Error di tengah proses | Database state tetap konsisten |
| UT-TRANSAKSI-012 | Update stok after transaksi | Create transaksi dengan barang | Stok berkurang sesuai qty |
| UT-TRANSAKSI-013 | Multiple details in one transaksi | 5+ detail items | Semua detail tersimpan |

---

#### 1.6 DashboardRepository
**File:** `DashboardRepository.java`

| Test Case ID | Deskripsi | Input | Expected Output |
|--------------|-----------|-------|-----------------|
| UT-DASHBOARD-001 | Get total pelanggan | - | Return count integer |
| UT-DASHBOARD-002 | Get total barang | - | Return count integer |
| UT-DASHBOARD-003 | Get total transaksi | - | Return count integer |
| UT-DASHBOARD-004 | Get total transaksi hari ini | Date: today | Return count untuk hari ini |
| UT-DASHBOARD-005 | Get total pendapatan | - | Return sum amount |
| UT-DASHBOARD-006 | Get statistik dengan database kosong | Empty DB | Return zeros/empty data |

---

### 2. Service Layer Testing

#### 2.1 AdminService
**File:** `AdminService.java`

| Test Case ID | Deskripsi | Input | Expected Output | Mock Behavior |
|--------------|-----------|-------|-----------------|---------------|
| UT-ADMIN-SVC-001 | Login sukses | Valid credentials | Response.success dengan Admin data | Repository returns Admin object |
| UT-ADMIN-SVC-002 | Login gagal | Invalid credentials | Response.failure | Repository returns null |
| UT-ADMIN-SVC-003 | Login exception | Valid credentials | Response.failure dengan error message | Repository throws exception |
| UT-ADMIN-SVC-004 | ApplicationState update after login | Valid credentials | ApplicationState.admin is set | Repository returns Admin object |

---

#### 2.2 BarangService
**File:** `BarangService.java`

| Test Case ID | Deskripsi | Expected Behavior | Mock Behavior |
|--------------|-----------|-------------------|---------------|
| UT-BARANG-SVC-001 | createBarang sukses | Response.success dengan BarangResponseDTO | Repository returns BarangResponseDTO |
| UT-BARANG-SVC-002 | createBarang gagal | Response.failure | Repository returns null |
| UT-BARANG-SVC-003 | createBarang exception | Response.failure dengan error message | Repository throws exception |
| UT-BARANG-SVC-004 | getAllBarang sukses | Response.success dengan List | Repository returns list |
| UT-BARANG-SVC-005 | getAllBarang empty | Response.success dengan empty list | Repository returns empty list |
| UT-BARANG-SVC-006 | getBarangByKode found | Response.success dengan data | Repository returns object |
| UT-BARANG-SVC-007 | getBarangByKode not found | Response.failure | Repository returns null |
| UT-BARANG-SVC-008 | updateBarang sukses | Response.success | Repository returns true |
| UT-BARANG-SVC-009 | updateBarang gagal | Response.failure | Repository returns false |
| UT-BARANG-SVC-010 | deleteBarang sukses | Response.success | Repository returns true |
| UT-BARANG-SVC-011 | deleteBarang gagal | Response.failure | Repository returns false |

---

#### 2.3 PelangganService
**File:** `PelangganService.java`

| Test Case ID | Deskripsi | Expected Behavior | Mock Behavior |
|--------------|-----------|-------------------|---------------|
| UT-PELANGGAN-SVC-001 | createPelanggan sukses | Response.success | Repository returns object |
| UT-PELANGGAN-SVC-002 | createPelanggan gagal | Response.failure | Repository returns null |
| UT-PELANGGAN-SVC-003 | getAllPelanggan sukses | Response.success dengan list | Repository returns list |
| UT-PELANGGAN-SVC-004 | getPelangganByNopol found | Response.success | Repository returns object |
| UT-PELANGGAN-SVC-005 | getPelangganByNopol not found | Response.failure | Repository returns null |
| UT-PELANGGAN-SVC-006 | Search pelanggan by nama | Response.success dengan filtered list | Repository returns list |

---

#### 2.4 MekanikService
**File:** `MekanikService.java`

| Test Case ID | Deskripsi | Expected Behavior | Mock Behavior |
|--------------|-----------|-------------------|---------------|
| UT-MEKANIK-SVC-001 | createMekanik sukses | Response.success | Repository returns object |
| UT-MEKANIK-SVC-002 | getAllMekanik sukses | Response.success dengan list | Repository returns list |
| UT-MEKANIK-SVC-003 | getMekanikById found | Response.success | Repository returns object |
| UT-MEKANIK-SVC-004 | getMekanikById not found | Response.failure | Repository returns null |

---

#### 2.5 TransaksiService
**File:** `TransaksiService.java`

| Test Case ID | Deskripsi | Input | Expected Behavior | Mock Behavior |
|--------------|-----------|-------|-------------------|---------------|
| UT-TRANSAKSI-SVC-001 | createTransaksi sukses | Valid TransaksiRequestDTO | Response.success dengan no_faktur | Repository returns no_faktur |
| UT-TRANSAKSI-SVC-002 | createTransaksi - stok habis | Transaksi dengan qty > stok | Response.failure dengan message stok | Repository throws SQLException |
| UT-TRANSAKSI-SVC-003 | createTransaksi exception | Valid request | Response.failure | Repository throws exception |
| UT-TRANSAKSI-SVC-004 | getAllTransaksi sukses | - | Response.success dengan list | Repository returns list |
| UT-TRANSAKSI-SVC-005 | Error message handling | SQLException dengan message khusus | Response dengan message yang sesuai | Repository throws specific SQLException |

---

### 3. Util Class Testing

#### 3.1 DBConnection
**File:** `DBConnection.java`

| Test Case ID | Deskripsi | Expected Output |
|--------------|-----------|-----------------|
| UT-DB-001 | Get connection sukses | Return valid Connection object |
| UT-DB-002 | Get connection - invalid credentials | Return null atau throw exception |
| UT-DB-003 | Get connection - database not exist | Return null atau throw exception |
| UT-DB-004 | Close connection | Connection closed successfully |
| UT-DB-005 | Close null connection | No exception thrown |
| UT-DB-006 | Close already closed connection | No exception thrown |
| UT-DB-007 | Load env variables | Credentials dari .env file loaded |
| UT-DB-008 | Fallback to defaults | Gunakan default values jika .env tidak ada |

---

#### 3.2 Response
**File:** `Response.java`

| Test Case ID | Deskripsi | Expected Output |
|--------------|-----------|-----------------|
| UT-RESPONSE-001 | Create success response | success=true, message set, data present |
| UT-RESPONSE-002 | Create failure response | success=false, message set, data=null |
| UT-RESPONSE-003 | Get success status | Return boolean correctly |
| UT-RESPONSE-004 | Get message | Return message string |
| UT-RESPONSE-005 | Get data | Return data object |
| UT-RESPONSE-006 | Generic type handling | Data type sesuai dengan generic type |

---

#### 3.3 ApplicationState (Singleton)
**File:** `ApplicationState.java`

| Test Case ID | Deskripsi | Expected Output |
|--------------|-----------|-----------------|
| UT-APPSTATE-001 | Get instance (first call) | Create new instance |
| UT-APPSTATE-002 | Get instance (subsequent) | Return same instance |
| UT-APPSTATE-003 | Set and get admin | Admin object stored and retrieved |
| UT-APPSTATE-004 | Set and get content panel | JPanel stored and retrieved |
| UT-APPSTATE-005 | Set and get form object | Object stored and retrieved |
| UT-APPSTATE-006 | Singleton pattern | Only one instance exists |

---

## 🔗 Integration Testing

Integration testing menguji interaksi antar komponen.

### 1. Database Integration Tests

| Test Case ID | Deskripsi | Komponen | Expected Result |
|--------------|-----------|----------|-----------------|
| IT-DB-001 | Repository → Database (Admin) | AdminRepository + MySQL | Login flow works end-to-end |
| IT-DB-002 | Repository → Database (Barang) | BarangRepository + MySQL | CRUD operations work |
| IT-DB-003 | Repository → Database (Pelanggan) | PelangganRepository + MySQL | CRUD operations work |
| IT-DB-004 | Repository → Database (Mekanik) | MekanikRepository + MySQL | CRUD operations work |
| IT-DB-005 | Repository → Database (Transaksi) | TransaksiRepository + MySQL | Transaction integrity maintained |
| IT-DB-006 | Foreign key constraints | Multiple repositories | FK violations handled properly |
| IT-DB-007 | Transaction rollback | TransaksiRepository | Rollback works on error |
| IT-DB-008 | Connection pooling | All repositories | No connection leaks |

---

### 2. Service-Repository Integration

| Test Case ID | Deskripsi | Komponen | Expected Result |
|--------------|-----------|----------|-----------------|
| IT-SVC-001 | AdminService → AdminRepository | Service + Repository | Login flow integrated |
| IT-SVC-002 | BarangService → BarangRepository | Service + Repository | CRUD flow integrated |
| IT-SVC-003 | PelangganService → PelangganRepository | Service + Repository | CRUD flow integrated |
| IT-SVC-004 | MekanikService → MekanikRepository | Service + Repository | CRUD flow integrated |
| IT-SVC-005 | TransaksiService → TransaksiRepository | Service + Repository | Transaction flow integrated |
| IT-SVC-006 | Error handling propagation | Service + Repository | Exceptions handled properly |
| IT-SVC-007 | Response object mapping | All services | Response format consistent |

---

### 3. Cross-Module Integration

| Test Case ID | Deskripsi | Modules | Scenario |
|--------------|-----------|---------|----------|
| IT-CROSS-001 | Transaksi → Barang integration | TransaksiService + BarangService | Stok update after transaction |
| IT-CROSS-002 | Transaksi → Pelanggan integration | TransaksiService + PelangganService | Pelanggan validation in transaction |
| IT-CROSS-003 | Dashboard data aggregation | DashboardService + All repositories | Dashboard shows correct statistics |
| IT-CROSS-004 | Login → Navigation flow | AdminService + ApplicationState | Admin data persists across views |
| IT-CROSS-005 | Form navigation | All panels + ApplicationState | Navigation state maintained |

---

## 🖥️ System Testing

System testing menguji aplikasi secara keseluruhan.

### 1. Functional System Tests

#### 1.1 Modul Autentikasi

| Test Case ID | Deskripsi | Steps | Expected Result |
|--------------|-----------|-------|-----------------|
| ST-AUTH-001 | Login sukses | 1. Buka aplikasi<br>2. Input username/password valid<br>3. Click login | MainFrame muncul dengan dashboard |
| ST-AUTH-002 | Login gagal | 1. Buka aplikasi<br>2. Input credentials invalid<br>3. Click login | Error message ditampilkan |
| ST-AUTH-003 | Login dengan empty fields | 1. Buka aplikasi<br>2. Biarkan field kosong<br>3. Click login | Validation error |
| ST-AUTH-004 | Logout | 1. Login<br>2. Click logout | Kembali ke LoginFrame |
| ST-AUTH-005 | Session persistence | 1. Login<br>2. Navigate berbagai panel | User tetap login |

---

#### 1.2 Modul Dashboard

| Test Case ID | Deskripsi | Expected Result |
|--------------|-----------|-----------------|
| ST-DASH-001 | Load dashboard setelah login | Statistik tampil dengan benar |
| ST-DASH-002 | Dashboard dengan data kosong | Tampil angka 0 atau message "Belum ada data" |
| ST-DASH-003 | Dashboard refresh | Data update setelah transaksi baru |
| ST-DASH-004 | Dashboard menampilkan chart/graph | Visual data ditampilkan |

---

#### 1.3 Modul Manajemen Barang

| Test Case ID | Deskripsi | Steps | Expected Result |
|--------------|-----------|-------|-----------------|
| ST-BARANG-001 | Tambah barang baru | 1. Click menu Barang<br>2. Click Tambah<br>3. Isi form<br>4. Submit | Barang tersimpan dan muncul di table |
| ST-BARANG-002 | Tambah barang - kode duplikat | 1. Tambah barang dengan kode yang sama | Error message duplikat |
| ST-BARANG-003 | Edit barang | 1. Pilih barang di table<br>2. Click Edit<br>3. Ubah data<br>4. Submit | Data barang terupdate |
| ST-BARANG-004 | Hapus barang (unused) | 1. Pilih barang<br>2. Click Hapus<br>3. Konfirmasi | Barang terhapus dari table |
| ST-BARANG-005 | Hapus barang (used in transaksi) | 1. Pilih barang yang sudah di transaksi<br>2. Click Hapus | Error message FK constraint |
| ST-BARANG-006 | Search barang by nama | 1. Input keyword di search box<br>2. Enter | Table filter sesuai keyword |
| ST-BARANG-007 | Search barang by kode | 1. Input kode di search box<br>2. Enter | Barang spesifik ditampilkan |
| ST-BARANG-008 | View all barang | 1. Click menu Barang | Table menampilkan semua barang |
| ST-BARANG-009 | Sort table barang | 1. Click column header | Table tersortir by column |
| ST-BARANG-010 | Validasi input - negative stock | 1. Input stok negatif<br>2. Submit | Validation error |
| ST-BARANG-011 | Validasi input - negative price | 1. Input harga negatif<br>2. Submit | Validation error |
| ST-BARANG-012 | Validasi input - empty required fields | 1. Kosongkan field required<br>2. Submit | Validation error |

---

#### 1.4 Modul Manajemen Pelanggan

| Test Case ID | Deskripsi | Steps | Expected Result |
|--------------|-----------|-------|-----------------|
| ST-PELANGGAN-001 | Tambah pelanggan baru | 1. Click menu Pelanggan<br>2. Click Tambah<br>3. Isi form<br>4. Submit | Pelanggan tersimpan |
| ST-PELANGGAN-002 | Tambah pelanggan - nopol duplikat | 1. Tambah dengan nopol sama | Error message duplikat |
| ST-PELANGGAN-003 | Edit pelanggan | 1. Pilih pelanggan<br>2. Edit data<br>3. Submit | Data terupdate |
| ST-PELANGGAN-004 | Hapus pelanggan (no transaksi) | 1. Pilih pelanggan<br>2. Hapus | Pelanggan terhapus |
| ST-PELANGGAN-005 | Hapus pelanggan (ada transaksi) | 1. Pilih pelanggan dengan transaksi<br>2. Hapus | Error FK constraint |
| ST-PELANGGAN-006 | Search by nopol | 1. Input nopol<br>2. Search | Pelanggan ditemukan |
| ST-PELANGGAN-007 | Search by nama | 1. Input nama<br>2. Search | List pelanggan dengan nama match |
| ST-PELANGGAN-008 | View all pelanggan | 1. Click menu Pelanggan | Table menampilkan semua pelanggan |
| ST-PELANGGAN-009 | Validasi format nopol | 1. Input nopol invalid<br>2. Submit | Validation error |
| ST-PELANGGAN-010 | Validasi no HP | 1. Input no HP invalid<br>2. Submit | Validation error |

---

#### 1.5 Modul Manajemen Mekanik

| Test Case ID | Deskripsi | Steps | Expected Result |
|--------------|-----------|-------|-----------------|
| ST-MEKANIK-001 | Tambah mekanik baru | 1. Open form mekanik<br>2. Isi data<br>3. Submit | Mekanik tersimpan |
| ST-MEKANIK-002 | Edit mekanik | 1. Pilih mekanik<br>2. Edit<br>3. Submit | Data terupdate |
| ST-MEKANIK-003 | Hapus mekanik | 1. Pilih mekanik<br>2. Hapus | Mekanik terhapus |
| ST-MEKANIK-004 | Set mekanik non-aktif | 1. Pilih mekanik<br>2. Set status = 0 | Status update, tidak muncul di dropdown |
| ST-MEKANIK-005 | View active mekanik only | 1. View list mekanik | Hanya yang aktif ditampilkan |
| ST-MEKANIK-006 | Mekanik dropdown in transaksi | 1. Open form transaksi | ComboBox berisi list mekanik aktif |

---

#### 1.6 Modul Transaksi

| Test Case ID | Deskripsi | Steps | Expected Result |
|--------------|-----------|-------|-----------------|
| ST-TRANSAKSI-001 | Create transaksi barang saja | 1. Click Transaksi<br>2. Pilih pelanggan<br>3. Pilih mekanik<br>4. Tambah barang<br>5. Submit | Transaksi tersimpan, stok berkurang |
| ST-TRANSAKSI-002 | Create transaksi jasa saja | 1. Buat transaksi<br>2. Tambah jasa<br>3. Submit | Transaksi tersimpan |
| ST-TRANSAKSI-003 | Create transaksi mixed (barang + jasa) | 1. Buat transaksi<br>2. Tambah barang dan jasa<br>3. Submit | Transaksi tersimpan |
| ST-TRANSAKSI-004 | Transaksi - stok tidak cukup | 1. Tambah barang qty > stok<br>2. Submit | Error message stok tidak cukup |
| ST-TRANSAKSI-005 | Transaksi - pelanggan baru | 1. Input nopol baru<br>2. Isi data pelanggan<br>3. Lanjut transaksi | Pelanggan auto-created |
| ST-TRANSAKSI-006 | Transaksi - pelanggan existing | 1. Input nopol existing<br>2. Data auto-fill | Data pelanggan muncul otomatis |
| ST-TRANSAKSI-007 | Add multiple items | 1. Tambah 5+ items<br>2. Submit | Semua items tersimpan |
| ST-TRANSAKSI-008 | Edit qty before submit | 1. Tambah item<br>2. Edit qty<br>3. Submit | Qty terupdate |
| ST-TRANSAKSI-009 | Remove item from cart | 1. Tambah item<br>2. Remove<br>3. Submit | Item tidak tersimpan |
| ST-TRANSAKSI-010 | Calculate total otomatis | 1. Tambah multiple items | Total calculated correctly |
| ST-TRANSAKSI-011 | Generate no faktur | 1. Submit transaksi | No faktur unique dengan format benar |
| ST-TRANSAKSI-012 | Print nota | 1. Setelah transaksi sukses<br>2. Click Print | Nota tercetak/preview |
| ST-TRANSAKSI-013 | Cancel transaksi | 1. Tengah mengisi form<br>2. Click Cancel | Form clear, kembali ke state awal |
| ST-TRANSAKSI-014 | Validasi - empty cart | 1. Submit tanpa item | Validation error |
| ST-TRANSAKSI-015 | Validasi - no pelanggan | 1. Submit tanpa pilih pelanggan | Validation error |
| ST-TRANSAKSI-016 | Validasi - no mekanik | 1. Submit tanpa pilih mekanik | Validation error |

---

#### 1.7 Modul Histori Transaksi

| Test Case ID | Deskripsi | Steps | Expected Result |
|--------------|-----------|-------|-----------------|
| ST-HISTORI-001 | View all transaksi | 1. Click menu Histori | Table menampilkan semua transaksi |
| ST-HISTORI-002 | View detail transaksi | 1. Pilih transaksi<br>2. Click Detail | Dialog menampilkan detail items |
| ST-HISTORI-003 | Filter by date range | 1. Input tanggal mulai & akhir<br>2. Apply filter | Table filter sesuai range |
| ST-HISTORI-004 | Filter by nopol | 1. Input nopol<br>2. Apply filter | Transaksi pelanggan ditampilkan |
| ST-HISTORI-005 | Filter by no faktur | 1. Input no faktur<br>2. Apply filter | Transaksi spesifik ditampilkan |
| ST-HISTORI-006 | Sort by tanggal | 1. Click column tanggal | Sort desc/asc |
| ST-HISTORI-007 | Sort by total belanja | 1. Click column total | Sort desc/asc |
| ST-HISTORI-008 | Export histori to CSV/Excel | 1. Click Export<br>2. Pilih format | File downloaded |

---

### 2. Non-Functional System Tests

#### 2.1 Usability Testing

| Test Case ID | Deskripsi | Criteria | Expected Result |
|--------------|-----------|----------|-----------------|
| ST-USABILITY-001 | Form validation feedback | User-friendly error messages | Clear error messages with field highlight |
| ST-USABILITY-002 | Navigation intuitiveness | Menu structure | User dapat navigate tanpa kebingungan |
| ST-USABILITY-003 | Button placement consistency | UI consistency | Button posisi konsisten di semua form |
| ST-USABILITY-004 | Keyboard shortcuts | Shortcut support | Enter to submit, Esc to cancel works |
| ST-USABILITY-005 | Tab order | Form field navigation | Tab order logis dan konsisten |
| ST-USABILITY-006 | Loading indicators | Long operations | Loading/progress indicator ditampilkan |
| ST-USABILITY-007 | Success feedback | After save/update/delete | Success message/notification muncul |
| ST-USABILITY-008 | Confirmation dialogs | Destructive actions | Konfirmasi sebelum hapus/cancel |

---

## 👥 User Acceptance Testing (UAT)

UAT dilakukan oleh end-user (Admin Bengkel).

### UAT Test Scenarios

| UAT ID | Scenario | Actor | Acceptance Criteria |
|--------|----------|-------|---------------------|
| UAT-001 | **Daily Opening - Login** | Admin | Admin bisa login dengan credentials yang diberikan |
| UAT-002 | **Pelanggan Datang - Servis Motor** | Admin | 1. Input nopol pelanggan<br>2. Data pelanggan muncul otomatis (jika exist)<br>3. Pilih mekanik<br>4. Input keluhan<br>5. Tambah barang yang digunakan<br>6. Tambah jasa<br>7. Total otomatis terhitung<br>8. Transaksi tersimpan |
| UAT-003 | **Pelanggan Baru** | Admin | 1. Input nopol baru<br>2. Isi data pelanggan<br>3. Data tersimpan untuk transaksi berikutnya |
| UAT-004 | **Stok Barang Habis** | Admin | 1. Coba tambah barang dengan stok habis<br>2. System memberi warning<br>3. Tidak bisa proses transaksi |
| UAT-005 | **Update Harga Barang** | Admin | 1. Buka menu Barang<br>2. Cari barang<br>3. Edit harga<br>4. Harga terupdate untuk transaksi selanjutnya |
| UAT-006 | **Restock Barang** | Admin | 1. Buka menu Barang<br>2. Edit stok barang<br>3. Tambah jumlah stok<br>4. Stok terupdate |
| UAT-007 | **Lihat Pendapatan Hari Ini** | Admin | 1. Buka Dashboard<br>2. Lihat total transaksi hari ini<br>3. Angka sesuai dengan transaksi yang dilakukan |
| UAT-008 | **Cari Riwayat Transaksi Pelanggan** | Admin | 1. Buka Histori<br>2. Filter by nopol<br>3. List transaksi pelanggan muncul |
| UAT-009 | **Print Nota** | Admin | 1. Setelah transaksi<br>2. Print nota<br>3. Nota bisa diberikan ke pelanggan |
| UAT-010 | **Tambah Mekanik Baru** | Admin | 1. Buka menu Mekanik<br>2. Tambah mekanik<br>3. Mekanik muncul di dropdown transaksi |

---

## ⚡ Performance Testing

### Performance Metrics

| Test Case ID | Deskripsi | Metrics | Expected Result |
|--------------|-----------|---------|-----------------|
| PT-001 | **Login response time** | Time to authenticate | < 2 seconds |
| PT-002 | **Load barang list** | Time to load all barang | < 3 seconds untuk 1000 records |
| PT-003 | **Load pelanggan list** | Time to load all pelanggan | < 3 seconds untuk 1000 records |
| PT-004 | **Create transaksi** | Time to save transaksi | < 5 seconds |
| PT-005 | **Search barang** | Time to search and display | < 1 second |
| PT-006 | **Load dashboard** | Time to load all statistics | < 3 seconds |
| PT-007 | **Load histori transaksi** | Time to load 100 transaksi | < 3 seconds |
| PT-008 | **Database connection pooling** | Connection reuse | No connection timeout |
| PT-009 | **Memory usage** | Application memory | < 200 MB for normal operation |
| PT-010 | **CPU usage** | Application CPU | < 50% on normal operation |

### Load Testing Scenarios

| Scenario | Description | Expected Result |
|----------|-------------|-----------------|
| LOAD-001 | 50 transaksi dalam 1 jam | Semua transaksi processed tanpa error |
| LOAD-002 | 1000 barang records | Table loading smooth, no lag |
| LOAD-003 | 1000 pelanggan records | Table loading smooth, no lag |
| LOAD-004 | 5000 transaksi records | Histori loading dengan pagination |

---

## 🔒 Security Testing

| Test Case ID | Deskripsi | Test Steps | Expected Result |
|--------------|-----------|------------|-----------------|
| SEC-001 | **Password hashing** | 1. Create admin<br>2. Check DB | Password stored as BCrypt hash |
| SEC-002 | **SQL Injection - Login** | 1. Input: `' OR '1'='1`<br>2. Attempt login | Login gagal, no SQL injection |
| SEC-003 | **SQL Injection - Search** | 1. Search: `'; DROP TABLE barang;--`<br>2. Execute search | Search gagal safely, table tidak terhapus |
| SEC-004 | **SQL Injection - Insert** | 1. Insert dengan malicious input<br>2. Submit | Input sanitized, no injection |
| SEC-005 | **Session management** | 1. Login<br>2. Check ApplicationState | Only logged-in admin can access |
| SEC-006 | **Database credentials** | 1. Check .env file<br>2. Check code | Credentials tidak hardcoded |
| SEC-007 | **Connection string security** | 1. Review DBConnection | Connection properly secured |
| SEC-008 | **Input validation** | 1. Input special chars di form<br>2. Submit | Special chars handled properly |
| SEC-009 | **PreparedStatement usage** | 1. Review all queries<br>2. Check SQL injection prevention | All queries use PreparedStatement |
| SEC-010 | **Authorization** | 1. Attempt access tanpa login | Redirect ke LoginFrame |

---

## 📝 Test Cases Detail

### Contoh Test Case Template

```
Test Case ID: ST-TRANSAKSI-001
Title: Create Transaksi dengan Barang Saja

Preconditions:
- User sudah login sebagai Admin
- Database memiliki minimal 1 pelanggan
- Database memiliki minimal 1 mekanik
- Database memiliki minimal 1 barang dengan stok > 0

Test Steps:
1. Navigate ke menu "Transaksi"
2. Click button "Buat Transaksi Baru"
3. Input nopol pelanggan: "D 1234 ABC"
4. Pilih mekanik dari dropdown: "Udin"
5. Input keluhan: "Ganti oli dan busi"
6. Click "Tambah Barang"
7. Pilih barang: "Oli Mesin MPX2"
8. Input qty: 1
9. Click "Tambah"
10. Click "Tambah Barang"
11. Pilih barang: "Busi Standar"
12. Input qty: 2
13. Click "Tambah"
14. Verify total belanja otomatis calculated
15. Click "Simpan Transaksi"

Expected Result:
- Transaksi tersimpan ke database
- No faktur di-generate (format: TRX-DDMMYY-XXX)
- Stok barang berkurang:
  - Oli Mesin MPX2: stok - 1
  - Busi Standar: stok - 2
- Success message ditampilkan
- Dialog print nota muncul
- Form transaksi clear untuk transaksi berikutnya

Actual Result:
[To be filled during testing]

Status: [Pass/Fail]

Notes:
[Any additional observations]
```

---

## 🛠️ Tools dan Framework

### 1. Testing Framework

#### JUnit 5 (Jupiter)
Untuk unit testing dan integration testing.

**Setup Maven Dependency:**
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
```

#### Mockito
Untuk mocking objects dalam unit testing.

```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.5.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>5.5.0</version>
    <scope>test</scope>
</dependency>
```

#### AssertJ
Untuk fluent assertions.

```xml
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.24.2</version>
    <scope>test</scope>
</dependency>
```

---

### 2. Database Testing

#### H2 Database
In-memory database untuk testing.

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>2.2.224</version>
    <scope>test</scope>
</dependency>
```

#### DBUnit
Untuk database state management dalam testing.

```xml
<dependency>
    <groupId>org.dbunit</groupId>
    <artifactId>dbunit</artifactId>
    <version>2.7.3</version>
    <scope>test</scope>
</dependency>
```

---

### 3. UI Testing (Optional)

#### AssertJ Swing
Untuk automated Swing UI testing.

```xml
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-swing-junit</artifactId>
    <version>3.17.1</version>
    <scope>test</scope>
</dependency>
```

---

### 4. Code Coverage

#### JaCoCo
Untuk mengukur code coverage.

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

**Target Coverage:**
- Line Coverage: > 80%
- Branch Coverage: > 70%
- Method Coverage: > 80%

---

### 5. Test Organization

#### Project Structure
```
src/
  test/
    java/
      repository/
        AdminRepositoryTest.java
        BarangRepositoryTest.java
        PelangganRepositoryTest.java
        MekanikRepositoryTest.java
        TransaksiRepositoryTest.java
        DashboardRepositoryTest.java
      service/
        AdminServiceTest.java
        BarangServiceTest.java
        PelangganServiceTest.java
        MekanikServiceTest.java
        TransaksiServiceTest.java
        DashboardServiceTest.java
      util/
        DBConnectionTest.java
        ResponseTest.java
        ApplicationStateTest.java
      integration/
        DatabaseIntegrationTest.java
        ServiceRepositoryIntegrationTest.java
        CrossModuleIntegrationTest.java
      system/
        AuthenticationSystemTest.java
        BarangSystemTest.java
        PelangganSystemTest.java
        MekanikSystemTest.java
        TransaksiSystemTest.java
        HistoriSystemTest.java
    resources/
      test-data.sql
      test-schema.sql
      test.properties
```

---

## 📊 Test Reporting

### Test Metrics to Track

1. **Test Execution Metrics**
   - Total tests: XXX
   - Tests passed: XXX
   - Tests failed: XXX
   - Tests skipped: XXX
   - Pass rate: XX%

2. **Code Coverage Metrics**
   - Line coverage: XX%
   - Branch coverage: XX%
   - Method coverage: XX%
   - Class coverage: XX%

3. **Defect Metrics**
   - Total defects found: XXX
   - Critical: XX
   - High: XX
   - Medium: XX
   - Low: XX
   - Defect density: XX defects per KLOC

4. **Test Efficiency**
   - Average test execution time: XX seconds
   - Total test suite execution time: XX minutes
   - Defects found per hour of testing: XX

---

## 🚀 Test Execution Plan

### Phase 1: Unit Testing (Week 1-2)
- [ ] Setup testing framework dan dependencies
- [ ] Implement repository unit tests
- [ ] Implement service unit tests
- [ ] Implement util class tests
- [ ] Target: 70% code coverage

### Phase 2: Integration Testing (Week 3)
- [ ] Database integration tests
- [ ] Service-Repository integration tests
- [ ] Cross-module integration tests
- [ ] Target: All integrations tested

### Phase 3: System Testing (Week 4-5)
- [ ] Functional system tests
- [ ] UI tests (manual atau automated)
- [ ] Non-functional tests (usability, performance)
- [ ] Target: All user scenarios covered

### Phase 4: UAT (Week 6)
- [ ] Prepare UAT environment
- [ ] UAT with actual users
- [ ] Collect feedback
- [ ] Fix critical issues

### Phase 5: Final Testing (Week 7)
- [ ] Regression testing
- [ ] Performance testing
- [ ] Security testing
- [ ] Final acceptance

---

## 📌 Testing Best Practices

1. **Write Clean Test Code**
   - Test names should describe what they test
   - Use Arrange-Act-Assert pattern
   - One assertion per test (when possible)
   - Keep tests independent

2. **Test Data Management**
   - Use test fixtures
   - Clean up after tests
   - Use test databases
   - Don't depend on production data

3. **Continuous Testing**
   - Run tests on every commit
   - Integrate with CI/CD (GitHub Actions, Jenkins)
   - Fail build on test failure
   - Monitor test trends

4. **Test Maintenance**
   - Keep tests up to date with code changes
   - Refactor tests when needed
   - Remove obsolete tests
   - Document complex test scenarios

5. **Coverage Goals**
   - Aim for high coverage but prioritize critical paths
   - 100% coverage is not always necessary
   - Focus on business logic coverage
   - Test edge cases and error paths

---

## 📄 Test Documentation

### Documents to Maintain
1. ✅ **Testing Plan** (this document)
2. **Test Cases Spreadsheet** - Detail semua test cases
3. **Test Execution Report** - Hasil testing per iteration
4. **Defect Log** - List bugs ditemukan dan status
5. **Test Coverage Report** - JaCoCo reports
6. **UAT Sign-off Document** - Approval dari user

---

## 🎯 Exit Criteria

Testing dianggap complete jika:
- ✅ 80%+ unit tests passed
- ✅ 100% integration tests passed
- ✅ 90%+ system tests passed
- ✅ No critical/high priority defects open
- ✅ UAT signed off by users
- ✅ Performance meets requirements
- ✅ Security vulnerabilities addressed
- ✅ Code coverage > 70%

---

## 📞 Contact & Support

**Testing Team:**
- Test Lead: [Name]
- QA Engineers: [Names]
- Developers: [Names]

**Reporting Issues:**
- Issue Tracker: [Link/Tool]
- Email: [Email]
- Meeting Schedule: [Schedule]

---

## 📚 References

1. JUnit 5 Documentation: https://junit.org/junit5/docs/current/user-guide/
2. Mockito Documentation: https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html
3. AssertJ Documentation: https://assertj.github.io/doc/
4. Java Testing Best Practices: https://phauer.com/2019/modern-best-practices-testing-java/
5. Test Pyramid: https://martinfowler.com/articles/practical-test-pyramid.html

---

**Document Version:** 1.0  
**Last Updated:** December 6, 2025  
**Status:** Draft  

---

## Appendix A: Sample Test Code

### Example: BarangRepositoryTest.java

```java
package repository;

import dto.request.BarangRequestDTO;
import dto.response.BarangResponseDTO;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

class BarangRepositoryTest {
    
    private BarangRepository repository;
    
    @BeforeEach
    void setUp() {
        // Setup test database connection
        repository = new BarangRepository();
        // Insert test data
    }
    
    @AfterEach
    void tearDown() {
        // Clean up test data
    }
    
    @Test
    @DisplayName("Should create barang successfully")
    void testCreateBarangSuccess() {
        // Arrange
        BarangRequestDTO request = new BarangRequestDTO(
            "Test Barang", 10, 10000.0, 15000.0
        );
        
        // Act
        BarangResponseDTO result = repository.create(request);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getNamaBarang()).isEqualTo("Test Barang");
        assertThat(result.getStok()).isEqualTo(10);
        assertThat(result.getKodeBarang()).isNotEmpty();
    }
    
    @Test
    @DisplayName("Should return null when create fails")
    void testCreateBarangFailure() {
        // Arrange
        BarangRequestDTO request = null;
        
        // Act
        BarangResponseDTO result = repository.create(request);
        
        // Assert
        assertThat(result).isNull();
    }
    
    // More tests...
}
```

### Example: BarangServiceTest.java with Mockito

```java
package service;

import dto.request.BarangRequestDTO;
import dto.response.BarangResponseDTO;
import repository.BarangRepository;
import util.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BarangServiceTest {
    
    @Mock
    private BarangRepository repository;
    
    @InjectMocks
    private BarangService service;
    
    @Test
    @DisplayName("Should return success response when barang created")
    void testCreateBarangSuccess() {
        // Arrange
        BarangRequestDTO request = new BarangRequestDTO(
            "Test Barang", 10, 10000.0, 15000.0
        );
        BarangResponseDTO responseDTO = new BarangResponseDTO(
            "Test Barang", 10, 10000.0, 15000.0, "BRG-001"
        );
        when(repository.create(request)).thenReturn(responseDTO);
        
        // Act
        Response<BarangResponseDTO> response = service.createBarang(request);
        
        // Assert
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getData()).isEqualTo(responseDTO);
        verify(repository, times(1)).create(request);
    }
    
    @Test
    @DisplayName("Should return failure response when repository returns null")
    void testCreateBarangFailure() {
        // Arrange
        BarangRequestDTO request = new BarangRequestDTO(
            "Test Barang", 10, 10000.0, 15000.0
        );
        when(repository.create(request)).thenReturn(null);
        
        // Act
        Response<BarangResponseDTO> response = service.createBarang(request);
        
        // Assert
        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getData()).isNull();
    }
    
    // More tests...
}
```

---

**END OF DOCUMENT**

