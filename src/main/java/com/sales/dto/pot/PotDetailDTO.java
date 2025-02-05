package com.sales.dto.pot;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PotDetailDTO {
    private Integer id;
    private String namaPot;
    private String namaProduk;
    private String idKriteriaPaket;
    private String namaKriteriaPaket;
    private String tanggalMulai;
    private String tanggalAkhir;
    private String pokokHutangAwal;
    private String pokokHutangAkhir;
    private String namaInterval;
    private String tenor;
    private String effectiveRate;
    private String nilaiAdmin;
    private String nilaiProvisi;
    private String dp;
    private Boolean statusMerchandise;
    private Integer idKategori;
    private String namaKategori;
    private String idMerk;
    private String namaMerk;
    private String idTipe;
    private String namaTipe;
    private String idModel;
    private String namaModel;
}
