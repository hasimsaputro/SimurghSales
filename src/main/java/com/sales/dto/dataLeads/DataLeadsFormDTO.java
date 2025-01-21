package com.sales.dto.dataLeads;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DataLeadsFormDTO {
    private String id;
    private Integer idProduk;
    private String nomorAplikasi;

    private String tipeDebitur;

    private Integer idTipeAplikasi;

    private String idDebitur;

    private Integer idCabang;

    private String idMitraAgen;

    private String idDebiturReferensi;

    private String jenisUsaha;

    private Integer idKeteranganAplikasi;

    private String idUser;

    private Boolean status;

    private Integer idPOT;

    private BigDecimal estimasiNilaiFunding;

    private Integer idKategori;

    private String idMerk;

    private String idTipe;

    @Column(name = "IdModel", length = 10)
    private String idModel;

    @Column(name = "Tahun", length = 5)
    private String tahun;

    @Column(name = "TahunPajakSTNK", length = 5)
    private String tahunPajakSTNK;

    @Column(name = "NomorBPKB", length = 8)
    private String nomorBPKB;

    @Column(name = "NomorPolisi", length = 10)
    private String nomorPolisi;
}
