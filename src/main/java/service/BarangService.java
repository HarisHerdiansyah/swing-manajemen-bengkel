package service;

import dto.request.BarangRequestDTO;
import dto.response.BarangResponseDTO;
import repository.BarangRepository;
import util.Response;

import java.util.List;

public class BarangService {
    private final BarangRepository repository = new BarangRepository();

    public Response<BarangResponseDTO> createBarang(BarangRequestDTO request) {
        try {
            BarangResponseDTO created = repository.create(request);
            if (created != null) {
                return Response.success("Barang berhasil ditambahkan", created);
            } else {
                return Response.failure("Gagal menambahkan barang");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat menambahkan barang: " + e.getMessage());
        }
    }

    public Response<List<BarangResponseDTO>> getAllBarang() {
        try {
            List<BarangResponseDTO> list = repository.getAll();
            return Response.success("Data barang berhasil diambil", list);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mengambil data barang: " + e.getMessage());
        }
    }

    public Response<BarangResponseDTO> getBarangByKode(String kodeBarang) {
        try {
            BarangResponseDTO barang = repository.getByKode(kodeBarang);
            if (barang != null) {
                return Response.success("Barang ditemukan", barang);
            } else {
                return Response.failure("Barang dengan kode " + kodeBarang + " tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mencari barang: " + e.getMessage());
        }
    }

    public Response<List<BarangResponseDTO>> getBarangByNama(String namaBarang) {
        try {
            List<BarangResponseDTO> list = repository.getByNama(namaBarang);
            return Response.success("Pencarian barang berhasil", list);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mencari barang: " + e.getMessage());
        }
    }

    public Response<Void> updateBarang(String kodeBarang, BarangRequestDTO request) {
        try {
            boolean updated = repository.update(kodeBarang, request);
            if (updated) {
                return Response.success("Barang berhasil diperbarui", null);
            } else {
                return Response.failure("Gagal memperbarui barang atau barang tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat memperbarui barang: " + e.getMessage());
        }
    }

    public Response<Void> deleteBarang(String kodeBarang) {
        try {
            boolean deleted = repository.delete(kodeBarang);
            if (deleted) {
                return Response.success("Barang berhasil dihapus", null);
            } else {
                return Response.failure("Gagal menghapus barang atau barang tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat menghapus barang: " + e.getMessage());
        }
    }
}
