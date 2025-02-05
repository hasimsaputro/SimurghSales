package com.sales.dto.kabupaten;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class KabupatenFormDTO {
    private String provinsi;
    private Integer kode;
    @NotBlank(message = "Nama Kabupaten harus diisi")
    @Length(max = 100, message = "Nama Kabupaten tidak boleh melebihi 100 karakter")
    private String kabupaten;
    private Boolean status;
}
