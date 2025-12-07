/*
Nama            : (08) Raissa Christabel Sebayang
                  (44) Abraham Gomes Samosir
                  (74) Haris Herdiansyah
*/

package service;

import dto.request.TransaksiRequestDTO;
import dto.response.TransaksiResponseDTO;
import repository.TransaksiRepository;
import util.Response;

import java.util.List;

public class TransaksiService {
    private final TransaksiRepository repository = new TransaksiRepository();

    public Response<String> createTransaksi(TransaksiRequestDTO request) {
        try {
            String noFaktur = repository.createTransaksi(request);
            if (noFaktur != null) {
                return Response.success("Transaksi berhasil dibuat dengan nomor faktur: " + noFaktur, noFaktur);
            } else {
                return Response.failure("Gagal membuat transaksi");
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            String errorMessage = e.getMessage();
            if (errorMessage != null && (errorMessage.contains("Stok tidak cukup") ||
                                        errorMessage.contains("tidak ditemukan") ||
                                        errorMessage.contains("Gagal mengurangi stok"))) {
                return Response.failure(errorMessage);
            }
            return Response.failure("Terjadi kesalahan saat membuat transaksi: " + errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat membuat transaksi: " + e.getMessage());
        }
    }

    public Response<List<TransaksiResponseDTO>> getAllTransaksi() {
        try {
            List<TransaksiResponseDTO> transaksiList = repository.getAllTransaksi();
            return Response.success("Berhasil mengambil semua transaksi", transaksiList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mengambil transaksi: " + e.getMessage());
        }
    }

    public Response<List<TransaksiResponseDTO>> searchTransaksi(String noFaktur, String tanggal, String namaMekanik, String namaPelanggan) {
        try {
            boolean hasFilter = false;

            if (noFaktur != null && !noFaktur.trim().isEmpty()) {
                hasFilter = true;
            }
            if (tanggal != null && !tanggal.trim().isEmpty()) {
                hasFilter = true;
            }
            if (namaMekanik != null && !namaMekanik.trim().isEmpty()) {
                hasFilter = true;
            }
            if (namaPelanggan != null && !namaPelanggan.trim().isEmpty()) {
                hasFilter = true;
            }

            if (!hasFilter) {
                return Response.failure("Minimal 1 field harus terisi untuk melakukan pencarian");
            }

            List<TransaksiResponseDTO> transaksiList = repository.searchTransaksi(noFaktur, tanggal, namaMekanik, namaPelanggan);

            if (transaksiList.isEmpty()) {
                return Response.success("Tidak ada transaksi yang sesuai dengan kriteria pencarian", transaksiList);
            }

            return Response.success("Berhasil menemukan " + transaksiList.size() + " transaksi", transaksiList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("Terjadi kesalahan saat mencari transaksi: " + e.getMessage());
        }
    }
}
