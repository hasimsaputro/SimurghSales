package com.sales.dto.kelurahan;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class KelurahanFormDTO {
    private String provinsi;
    private String kabupaten;
    private String kecamatan;
    private Integer kode;
    @NotBlank(message = "Nama Kelurahan harus diisi")
    @Length(max = 100, message = "Nama Kelurahan tidak boleh melebihi 100 karakter")
    private String kelurahan;
    @NotBlank(message = "Kode Pos harus diisi")
    @Length(min = 3, message = "Kode Pos tidak boleh kurang dari 3 digit")
    private String kodePos;
    private Boolean status;
}
