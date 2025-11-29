package service;

import dto.request.PelangganRequestDTO;
import dto.response.PelangganResponseDTO;
import repository.PelangganRepository;
import util.Response;

import java.util.List;

public class PelangganService {
    private final PelangganRepository repository = new PelangganRepository();

    public Response<PelangganResponseDTO> createPelanggan(PelangganRequestDTO request) {
        try {
            PelangganResponseDTO created = repository.create(request);
            if (created != null) {
                return Response.success("Pelanggan berhasil ditambahkan", created);
            } else {
                return Response.failure("Gagal menambahkan pelanggan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat menambahkan pelanggan: " + e.getMessage());
        }
    }

    public Response<List<PelangganResponseDTO>> getAllPelanggan() {
        try {
            List<PelangganResponseDTO> list = repository.getAll();
            return Response.success("Data pelanggan berhasil diambil", list);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mengambil data pelanggan: " + e.getMessage());
        }
    }

    public Response<PelangganResponseDTO> getPelangganByNopol(String nopol) {
        try {
            PelangganResponseDTO pelanggan = repository.getByNopol(nopol);
            if (pelanggan != null) {
                return Response.success("Pelanggan ditemukan", pelanggan);
            } else {
                return Response.failure("Pelanggan dengan nopol " + nopol + " tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mencari pelanggan: " + e.getMessage());
        }
    }

    public Response<PelangganResponseDTO> getPelangganByExactName(String namaPemilik) {
        try {
            PelangganResponseDTO pelanggan = repository.getByExactName(namaPemilik);
            if (pelanggan != null) {
                return Response.success("Pelanggan ditemukan", pelanggan);
            } else {
                return Response.failure("Pelanggan dengan nama '" + namaPemilik + "' tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mencari pelanggan: " + e.getMessage());
        }
    }

    public Response<List<PelangganResponseDTO>> getPelangganByLikeName(String namaPemilik) {
        try {
            List<PelangganResponseDTO> list = repository.getByLikeName(namaPemilik);
            return Response.success("Pencarian pelanggan berhasil", list);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mencari pelanggan: " + e.getMessage());
        }
    }

    public Response<Void> updatePelanggan(String nopol, PelangganRequestDTO request) {
        try {
            boolean updated = repository.update(nopol, request);
            if (updated) {
                return Response.success("Pelanggan berhasil diperbarui", null);
            } else {
                return Response.failure("Gagal memperbarui pelanggan atau pelanggan tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat memperbarui pelanggan: " + e.getMessage());
        }
    }

    public Response<Void> deletePelanggan(String nopol) {
        try {
            boolean deleted = repository.delete(nopol);
            if (deleted) {
                return Response.success("Pelanggan berhasil dihapus", null);
            } else {
                return Response.failure("Gagal menghapus pelanggan atau pelanggan tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat menghapus pelanggan: " + e.getMessage());
        }
    }
}
