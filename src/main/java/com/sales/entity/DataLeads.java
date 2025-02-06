package com.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "DataLeads")
public class DataLeads {
    @Id
    @Column(name = "Id", length = 20)
    private String id;

    @Column(name = "IdProduk", nullable = false)
    private Integer idProduk;

    @Column(name = "NomorAplikasi", length = 12, nullable = false)
    private String nomorAplikasi;

    @Column(name = "TipeDebitur", length = 2, nullable = false)
    private String tipeDebitur;

    @Column(name = "IdTipeAplikasi", nullable = false)
    private Integer idTipeAplikasi;

    @Column(name = "IdDebitur", length = 10, nullable = false)
    private String idDebitur;

    @Column(name = "IdCabang", nullable = false)
    private Integer idCabang;

    @Column(name = "IdMitraAgen", length = 10, nullable = false)
    private String idMitraAgen;

    @Column(name = "IdDebiturReferensi", length = 10)
    private String idDebiturReferensi;

    @Column(name = "JenisUsaha", length = 50)
    private String jenisUsaha;

    @Column(name = "IdKeteranganAplikasi", nullable = false)
    private Integer idKeteranganAplikasi;

    @Column(name = "IdUser", length = 7)
    private String idUser;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @Column(name = "IdPOT", nullable = false)
    private Integer idPOT;

    @Column(name = "EstimasiNilaiFunding")
    private BigDecimal estimasiNilaiFunding;

    @Column(name = "IdKategori", nullable = false)
    private Integer idKategori;

    @Column(name = "IdMerk", length = 10)
    private String idMerk;

    @Column(name = "IdTipe", length = 10)
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

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @ManyToOne
    @JoinColumn(name = "IdProduk", insertable = false, updatable = false)
    private Produk produkDataleads;

    @ManyToOne
    @JoinColumn(name = "IdTipeAplikasi", insertable = false, updatable = false)
    private TipeAplikasi tipeAplikasiDataLeads;

    @ManyToOne
    @JoinColumn(name = "IdDebitur", insertable = false, updatable = false)
    private Debitur debiturDataLeads;

    @ManyToOne
    @JoinColumn(name = "IdCabang", insertable = false, updatable = false)
    private Cabang cabangDataLeads;

    @ManyToOne
    @JoinColumn(name = "IdMitraAgen", insertable = false, updatable = false)
    private MitraAgen mitraAgenDataLeads;

    @ManyToOne
    @JoinColumn(name = "IdDebiturReferensi", insertable = false, updatable = false)
    private Debitur debiturReferensiDataLeads;

    @ManyToOne
    @JoinColumn(name = "IdKeteranganAplikasi", insertable = false, updatable = false)
    private KeteranganAplikasi keteranganAplikasi;

    @ManyToOne
    @JoinColumn(name = "IdUser", insertable = false, updatable = false)
    private User userDataLeads;

    @ManyToOne
    @JoinColumn(name = "IdPOT", insertable = false, updatable = false)
    private POT potDataLeads;

    @ManyToOne
    @JoinColumn(name = "IdKategori", insertable = false, updatable = false)
    private Kategori kategoriDataLeads;

    @ManyToOne
    @JoinColumn(name = "IdMerk", insertable = false, updatable = false)
    private Merk merkDataLeads;

    @ManyToOne
    @JoinColumn(name = "IdTipe", insertable = false, updatable = false)
    private Tipe tipeDataLeads;

    @ManyToOne
    @JoinColumn(name = "IdModel", insertable = false, updatable = false)
    private Model modelDataLeads;
}
