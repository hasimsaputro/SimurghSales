package com.sales.dto.kecamatan;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class KecamatanFormDTO {
    private String provinsi;
    private String kabupaten;
    private Integer kode;
    @NotBlank(message = "Nama Kecamatan harus diisi")
    @Length(max = 100, message = "Nama Kecamatan tidak boleh melebihi 100 karakter")
    private String kecamatan;
    private Boolean status;
}
