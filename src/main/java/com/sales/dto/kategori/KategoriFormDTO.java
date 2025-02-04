package com.sales.dto.kategori;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class KategoriFormDTO {
    private Integer id;
    @NotBlank(message = "Nama harus diisi")
    private String namaKategori;
    @NotNull
    private Boolean status;
}
