package com.sales.dto.kelurahan;

import lombok.Data;

@Data
public class KelurahanDetailDTO {
    private String provinsi;
    private String kabupaten;
    private String kecamatan;
    private Integer kode;
    private String kelurahan;
    private String kodePos;
    private String status;
}
