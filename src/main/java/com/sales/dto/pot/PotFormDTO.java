package com.sales.dto.pot;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PotFormDTO {
    private Integer id;
    @NotBlank(message = "Nama harus diisi")
    private String namaPot;
    @NotBlank(message = "Produk Harus Dipilih")
    private Integer idProduk;
    private String idKriteriaPaket;
    private LocalDate tanggalMulai;
    private LocalDate tanggalAkhir;
    private BigDecimal pokokHutangAwal;
    private BigDecimal pokokHutangAkhir;
    @NotBlank(message = "Interval Pembayaran Harus Dipilih")
    private Integer idIntevalPembayaran;
    private Integer tenor;
    private Double effectiveRate;
    private BigDecimal nilaiAdmin;
    private BigDecimal nilaiProvisi;
    private Integer dp;
    private Boolean statusMerchandise;
    @NotBlank(message = "Kategori Harus Dipilih")
    private Integer idKategori;
    private String idMerk;
    private String idTipe;
    private String idModel;
}
