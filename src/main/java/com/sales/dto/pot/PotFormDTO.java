package com.sales.dto.pot;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PotFormDTO {
    private Integer id;
    @NotBlank(message = "Nama harus diisi")
    private String namaPot;
    @NotBlank(message = "Produk Harus Dipilih")
    private Integer idProduk;
    private String namaProduk;
    private String idKriteriaPaket;
    private String namaKriteriaPaket;
    private LocalDate tanggalMulai;
    private LocalDate tanggalAkhir;
    private String pokokHutangAwal;
    private String pokokHutangAkhir;
    @NotBlank(message = "Interval Pembayaran Harus Dipilih")
    private Integer idIntevalPembayaran;
    private String namaInterval;
    private String tenor;
    private String effectiveRate;
    private String nilaiAdmin;
    private String nilaiProvisi;
    private String dp;
    private Boolean statusMerchandise;
    @NotBlank(message = "Kategori Harus Dipilih")
    private Integer idKategori;
    private String namaKategori;
    private String idMerk;
    private String namaMerk;
    private String idTipe;
    private String namaTipe;
    private String idModel;
    private String namaModel;
    private List<Integer> cabangList;
}
