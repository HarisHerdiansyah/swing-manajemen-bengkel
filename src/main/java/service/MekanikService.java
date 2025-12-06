package service;

import dto.request.MekanikRequestDTO;
import dto.response.MekanikResponseDTO;
import repository.MekanikRepository;
import util.Response;

import java.util.List;

public class MekanikService {
    private final MekanikRepository repository = new MekanikRepository();

    public Response<MekanikResponseDTO> createMekanik(MekanikRequestDTO request) {
        try {
            MekanikResponseDTO created = repository.create(request);
            if (created != null) {
                return Response.success("Mekanik berhasil ditambahkan", created);
            } else {
                return Response.failure("Gagal menambahkan mekanik");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat menambahkan mekanik: " + e.getMessage());
        }
    }

    public Response<List<MekanikResponseDTO>> getAllMekanik() {
        try {
            List<MekanikResponseDTO> list = repository.getAll();
            return Response.success("Data mekanik berhasil diambil", list);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mengambil data mekanik: " + e.getMessage());
        }
    }

    public Response<MekanikResponseDTO> getMekanikById(int idMekanik) {
        try {
            MekanikResponseDTO mekanik = repository.getById(idMekanik);
            if (mekanik != null) {
                return Response.success("Mekanik ditemukan", mekanik);
            } else {
                return Response.failure("Mekanik dengan ID " + idMekanik + " tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mencari mekanik: " + e.getMessage());
        }
    }

    public Response<MekanikResponseDTO> getMekanikByExactName(String namaMekanik) {
        try {
            MekanikResponseDTO mekanik = repository.getByExactName(namaMekanik);
            if (mekanik != null) {
                return Response.success("Mekanik ditemukan", mekanik);
            } else {
                return Response.failure("Mekanik dengan nama '" + namaMekanik + "' tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mencari mekanik: " + e.getMessage());
        }
    }

    public Response<List<MekanikResponseDTO>> getMekanikByLikeName(String namaMekanik) {
        try {
            List<MekanikResponseDTO> list = repository.getByLikeName(namaMekanik);
            return Response.success("Pencarian mekanik berhasil", list);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mencari mekanik: " + e.getMessage());
        }
    }

    public Response<List<MekanikResponseDTO>> getMekanikByStatus(int status) {
        try {
            List<MekanikResponseDTO> list = repository.getByStatus(status);
            return Response.success("Data mekanik berhasil diambil berdasarkan status", list);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mengambil data mekanik berdasarkan status: " + e.getMessage());
        }
    }

    public Response<Void> updateMekanik(int idMekanik, MekanikRequestDTO request) {
        try {
            boolean updated = repository.update(idMekanik, request);
            if (updated) {
                return Response.success("Mekanik berhasil diperbarui", null);
            } else {
                return Response.failure("Gagal memperbarui mekanik atau mekanik tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat memperbarui mekanik: " + e.getMessage());
        }
    }

    public Response<Void> deleteMekanik(String namaMekanik) {
        try {
            boolean deleted = repository.delete(namaMekanik);
            if (deleted) {
                return Response.success("Mekanik berhasil dihapus", null);
            } else {
                return Response.failure("Gagal menghapus mekanik atau mekanik tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat menghapus mekanik: " + e.getMessage());
        }
    }
}
