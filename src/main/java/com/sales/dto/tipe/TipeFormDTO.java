package com.sales.dto.tipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TipeFormDTO {
    private String id;
    @NotNull
    private Integer idKategori;
    private String namaKategori;
    @NotNull
    private String idMerk;
    private String namaMerk;
    @NotBlank(message = "Nama harus diisi")
    private String namaTipe;
    @NotNull
    private String idJenis;
    private String namaJenis;
    @NotNull
    private Boolean status;
}
