package com.sales.dto.pot;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PotDetailDTO {
    private Integer id;
    private String namaPot;
    private String produk;
    private String kriteriaPaket;
    private String tanggalMulai;
    private String tanggalAkhir;
    private String pokokAwal;
    private String pokokAkhir;
    private String interval;
    private String tenor;
    private String effectRate;
    private String nilaiAdmin;
    private String nilaiProvisi;
    private String dp;
    private String statusMerchandise;
    private String kategori;
    private String merk;
    private String tipe;
    private String model;
}
