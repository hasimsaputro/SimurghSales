package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "HargaPasar")
public class HargaPasar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "IdKategori", nullable = false)
    private Integer idKategori;

    @Column(name = "IdWilayah",length = 5, nullable = false)
    private String idWilayah;

    @Column(name = "IdMerk", length = 10, nullable = false)
    private String idMerk;

    @Column(name = "IdTipe", length = 10, nullable = false)
    private String idTipe;

    @Column(name = "IdModel", length = 10, nullable = false)
    private String idModel;

    @Column(name = "IdJenis", length = 10, nullable = false)
    private String idJenis;

    @Column(name = "TipeUnit", length = 10, nullable = false)
    private String idUnit;

    @Column(name = "Tahun", length = 5, nullable = false)
    private String tahun;

    @Column(name = "Harga", nullable = false)
    private BigDecimal harga;

    @Column(name = "TanggalBerlaku")
    private LocalDate tanggalBerlaku;

    @Column(name = "Status", nullable = false)
    private Boolean status;


    @ManyToOne
    @JoinColumn(name = "IdKategori",insertable = false,updatable = false)
    private Kategori kategoriHargaPasar;

    @ManyToOne
    @JoinColumn(name = "IdWilayah",insertable = false,updatable = false)
    private WilayahHargaPasar wilayahHargaPasar;

    @ManyToOne
    @JoinColumn(name = "IdMerk",insertable = false,updatable = false)
    private Merk merkHargaPasar;

    @ManyToOne
    @JoinColumn(name = "IdTipe",insertable = false,updatable = false)
    private Tipe tipeHargaPasar;

    @ManyToOne
    @JoinColumn(name = "TipeUnit",insertable = false,updatable = false)
    private TipeUnit tipeUnitHargaPasar;

    @ManyToOne
    @JoinColumn(name = "IdModel",insertable = false,updatable = false)
    private Model modelHargaPasar;

    @ManyToOne
    @JoinColumn(name = "IdJenis",insertable = false,updatable = false)
    private Jenis jenisHargaPasar;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;
}