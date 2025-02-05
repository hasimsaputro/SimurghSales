package com.sales.dto.kecamatan;

import lombok.Data;

@Data
public class KecamatanIndexDTO {
    private String provinsi;
    private String kabupaten;
    private Integer kode;
    private String kecamatan;
    private String status;
}
