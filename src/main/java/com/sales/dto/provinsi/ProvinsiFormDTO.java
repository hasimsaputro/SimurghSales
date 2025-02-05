package com.sales.dto.provinsi;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ProvinsiFormDTO {
    private Integer kode;
    @NotBlank(message = "Nama Provinsi harus diisi")
    @Length(max = 100, message = "Nama Provinsi tidak boleh melebihi 100 karakter")
    private String provinsi;
    private Boolean status;
}
