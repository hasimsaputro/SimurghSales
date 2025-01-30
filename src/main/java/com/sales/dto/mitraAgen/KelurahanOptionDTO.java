package com.sales.dto.mitraAgen;

import lombok.Data;

@Data
public class KelurahanOptionDTO {
    private Integer id;
    private String namaKelurahan;
    private String kecamatan;
    private String kotaKabupaten;
    private String provinsi;
    private String kodePos;
}