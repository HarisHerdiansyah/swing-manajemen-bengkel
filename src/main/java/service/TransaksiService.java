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
}
