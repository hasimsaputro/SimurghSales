package com.sales.dto.pot;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PotFormDTO {
    private Integer id;
    @NotBlank(message = "Nama harus diisi")
    private String namaPot;
    @NotNull
    private Integer idProduk;
    private String namaProduk;
    private String idKriteriaPaket;
    private String namaKriteriaPaket;
    private LocalDate tanggalMulai;
    private LocalDate tanggalAkhir;
    private String pokokHutangAwal;
    private String pokokHutangAkhir;
    @NotNull
    private Integer idIntevalPembayaran;
    private String namaInterval;
    private String tenor;
    private String effectiveRate;
    private String nilaiAdmin;
    private String nilaiProvisi;
    private String dp;
    private Boolean statusMerchandise;
    @NotNull
    private Integer idKategori;
    private String namaKategori;
    private String idMerk;
    private String namaMerk;
    private String idTipe;
    private String namaTipe;
    private String idModel;
    private String namaModel;

    @JsonProperty("cabangList")
    private List<Integer> cabangList;
}
