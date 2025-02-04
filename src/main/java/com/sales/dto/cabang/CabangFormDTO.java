package com.sales.dto.cabang;

import com.sales.entity.Produk;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CabangFormDTO {

    private Integer id;

    @NotBlank(message = "Nama cabang harus ada")
    private String namaCabang;

    @NotBlank(message = "Tipe struktur harus ada")
    private String tipeStruktur;

    @NotBlank(message = "Alamat harus ada")
    private String alamat;

    @NotBlank(message = "Kode pos harus ada")
    private String kodePos;

    @NotBlank(message = "Kelurahan harus ada")
    private String kelurahan;

    @NotBlank(message = "Kecamatan harus ada")
    private String kecamatan;

    @NotBlank(message = "Kota/Kabupaten harus ada")
    private String kotaKabupaten;

    @NotBlank(message = "Provinsi harus ada")
    private String provinsi;

    @NotBlank(message = "Nomor telepon harus ada")
    private String nomorTelepon;

    @NotBlank(message = "NPWP harus ada")
    private String npwp;

    private LocalDate tanggalBerdiri;

    private List<Integer> produkList;

    private Boolean status;
}
