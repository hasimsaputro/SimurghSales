package com.sales.dto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModelFormDTO {
    private String id;
    @NotNull
    private Integer idKategori;
    private String namaKategori;
    @NotNull
    private String idMerk;
    private String namaMerk;
    @NotNull
    private String idTipe;
    private String namaTipe;
    @NotNull
    private String idJenis;
    private String namaJenis;
    @NotBlank(message = "Nama harus diisi")
    private String namaModel;
    @NotNull
    private Boolean status;
}
