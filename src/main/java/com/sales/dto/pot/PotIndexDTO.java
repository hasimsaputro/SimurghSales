package com.sales.dto.pot;

import lombok.Data;

@Data
public class PotIndexDTO {
    private String id;
    private String namaPot;
    private String namaProduk;
    private String tanggalAwal;
    private String tanggalAkhir;
    private String pokokHutangAwal;
    private String pokokHutangAkhir;
    private String tenor;
    private String effectiveRate;
}
