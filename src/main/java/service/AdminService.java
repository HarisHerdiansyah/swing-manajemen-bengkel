package service;

import dto.request.AdminRequestDTO;
import model.Admin;
import repository.AdminRepository;
import util.ApplicationState;
import util.Response;

public class AdminService {
    private final AdminRepository repository = new AdminRepository();

    public Response<String> login(AdminRequestDTO request) {
        try {
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
}
