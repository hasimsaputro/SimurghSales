package com.sales.dto.hargaPasar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HargaPasarFormDTO {
    private Integer id;
    @NotNull
    private Integer idKategori;
    private String namaKategori;
    @NotNull
    private String idWilayah;
    private String namaWilayah;
    @NotNull
    private String idMerk;
    private String namaMerk;
    @NotNull
    private String idTipe;
    private String namaTipe;
    @NotNull
    private String idModel;
    private String namaModel;
    private String idJenis;
    private String namaJenis;
    @NotNull
    private String idTipeUnit;
    private String namaTipeUnit;
    @NotNull
    private String tahun;
    @NotNull
    private String harga;
    private LocalDate tanggalMulai;
    @NotNull
    private Boolean status;
}
