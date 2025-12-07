/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

package service;

import dto.request.AdminRequestDTO;
import dto.response.AdminResponseDTO;
import model.Admin;
import repository.AdminRepository;
import util.ApplicationState;
import util.Response;

import java.util.List;
import java.util.stream.Collectors;

public class AdminService {
    private final AdminRepository repository = new AdminRepository();

    public Response<String> login(AdminRequestDTO request) {
        try {
            // Validasi input
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return Response.failure("Username tidak boleh kosong");
            }
            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return Response.failure("Password tidak boleh kosong");
            }

            Admin admin = repository.login(request);
            if (admin != null) {
                ApplicationState.getInstance().setAdmin(admin);
                return Response.success("Login berhasil", "Selamat datang, " + admin.getNamaLengkap());
            } else {
                return Response.failure("Username atau password salah");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat login: " + e.getMessage());
        }
    }

    public Response<AdminResponseDTO> createAdmin(AdminRequestDTO request) {
        try {
            // Validasi input
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return Response.failure("Username tidak boleh kosong");
            }
            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return Response.failure("Password tidak boleh kosong");
            }
            if (request.getNamaLengkap() == null || request.getNamaLengkap().trim().isEmpty()) {
                return Response.failure("Nama lengkap tidak boleh kosong");
            }

            // Validasi panjang password minimal
            if (request.getPassword().length() < 6) {
                return Response.failure("Password minimal 6 karakter");
            }

            // Cek username sudah ada atau belum
            if (repository.isUsernameExists(request.getUsername())) {
                return Response.failure("Username sudah digunakan");
            }

            boolean success = repository.createAdmin(request);
            if (success) {
                return Response.success("Admin berhasil ditambahkan", null);
            } else {
                return Response.failure("Gagal menambahkan admin");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan: " + e.getMessage());
        }
    }

    public Response<List<AdminResponseDTO>> getAllAdmins() {
        try {
            List<Admin> admins = repository.getAllAdmins();
            List<AdminResponseDTO> response = admins.stream()
                    .map(admin -> new AdminResponseDTO(
                            admin.getId(),
                            admin.getUsername(),
                            admin.getNamaLengkap()
                    ))
                    .collect(Collectors.toList());

            return Response.success("Data admin berhasil diambil", response);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan: " + e.getMessage());
        }
    }

    public Response<AdminResponseDTO> getAdminById(int id) {
        try {
            if (id <= 0) {
                return Response.failure("ID admin tidak valid");
            }

            Admin admin = repository.getAdminById(id);
            if (admin != null) {
                AdminResponseDTO response = new AdminResponseDTO(
                        admin.getId(),
                        admin.getUsername(),
                        admin.getNamaLengkap()
                );
                return Response.success("Data admin ditemukan", response);
            } else {
                return Response.failure("Admin tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan: " + e.getMessage());
        }
    }

    public Response<AdminResponseDTO> updateAdmin(int id, AdminRequestDTO request) {
        try {
            // Validasi input
            if (id <= 0) {
                return Response.failure("ID admin tidak valid");
            }
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return Response.failure("Username tidak boleh kosong");
            }
            if (request.getNamaLengkap() == null || request.getNamaLengkap().trim().isEmpty()) {
                return Response.failure("Nama lengkap tidak boleh kosong");
            }

            // Cek admin exists
            Admin existingAdmin = repository.getAdminById(id);
            if (existingAdmin == null) {
                return Response.failure("Admin tidak ditemukan");
            }

            // Validasi password jika diisi
            if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
                if (request.getPassword().length() < 6) {
                    return Response.failure("Password minimal 6 karakter");
                }
            }

            // Cek username sudah digunakan admin lain
            if (repository.isUsernameExistsExcludingId(request.getUsername(), id)) {
                return Response.failure("Username sudah digunakan oleh admin lain");
            }

            boolean success = repository.updateAdmin(id, request);
            if (success) {
                return Response.success("Admin berhasil diupdate", null);
            } else {
                return Response.failure("Gagal mengupdate admin");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan: " + e.getMessage());
        }
    }

    public Response<String> deleteAdmin(String username) {
        try {
            // Validasi input
            if (username == null || username.trim().isEmpty()) {
                return Response.failure("Username tidak boleh kosong");
            }

            // Cek admin exists
            Admin existingAdmin = repository.getAdminByUsername(username);
            if (existingAdmin == null) {
                return Response.failure("Admin tidak ditemukan");
            }

            // Cek apakah admin yang akan dihapus adalah admin yang sedang login
            Admin currentAdmin = ApplicationState.getInstance().getAdmin();
            if (currentAdmin != null && currentAdmin.getUsername().equals(username)) {
                return Response.failure("Tidak dapat menghapus akun Anda sendiri");
            }

            boolean success = repository.deleteAdmin(username);
            if (success) {
                return Response.success("Admin berhasil dihapus", null);
            } else {
                return Response.failure("Gagal menghapus admin");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan: " + e.getMessage());
        }
    }

    public Response<List<AdminResponseDTO>> getAdminsByLikeName(String namaLengkap) {
        try {
            // Validasi input
            if (namaLengkap == null || namaLengkap.trim().isEmpty()) {
                return Response.failure("Nama lengkap tidak boleh kosong");
            }

            List<Admin> admins = repository.getAdminsByLikeName(namaLengkap);
            List<AdminResponseDTO> response = admins.stream()
                    .map(admin -> new AdminResponseDTO(
                            admin.getId(),
                            admin.getUsername(),
                            admin.getNamaLengkap()
                    ))
                    .collect(Collectors.toList());

            if (response.isEmpty()) {
                return Response.failure("Admin dengan nama '" + namaLengkap + "' tidak ditemukan");
            }

            return Response.success("Data admin ditemukan", response);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan: " + e.getMessage());
        }
    }
}
