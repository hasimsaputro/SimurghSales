package com.sales.dto.merk;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MerkFormDTO {
    private String id;
    @NotNull
    private Integer idKategori;
    private String namaKategori;
    @NotBlank(message = "Nama harus diisi")
    private String namaMerk;
    @NotNull
    private Integer idNegara;
    private String namaNegara;
    @NotNull
    private Boolean status;
}
