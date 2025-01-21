package com.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "POT")
public class POT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "NamaPOT", length = 50, nullable = false)
    private String namaPOT;

    @Column(name = "IdProduk", nullable = false)
    private Integer idProduk;

    @Column(name = "IdKriteriaPaket", nullable = false)
    private Integer idKriteriaPaket;

    @Column(name = "TanggalMulai")
    private Date tanggalMulai;

    @Column(name = "TanggalAkhir")
    private Date tanggalAkhir;

    @Column(name = "PokokHutangAwal")
    private BigDecimal pokokHutangAwal;

    @Column(name = "PokokHutangAkhir")
    private BigDecimal pokokHutangAkhir;

    @Column(name = "IdInterval", nullable = false)
    private Integer idInterval;

    @Column(name = "Tenor")
    private Integer tenor;

    @Column(name = "EffectRate")
    private Double effectRate;

    @Column(name = "NilaiAdmin")
    private BigDecimal nilaiAdmin;

    @Column(name = "NilaiProvisi")
    private BigDecimal nilaiProvisi;

    @Column(name = "DP")
    private Integer dp;

    @Column(name = "StatusMerchandise")
    private Boolean statusMerchandise;

    @Column(name = "IdKategori", nullable = false)
    private Integer idKategori;

    @Column(name = "IdMerk", length = 10, nullable = false)
    private String idMerk;

    @Column(name = "IdTipe",length = 10, nullable = false)
    private String idTipe;

    @Column(name = "IdModel",length = 10, nullable = false)
    private String idModel;

    @ManyToOne
    @JoinColumn(name = "IdProduk", insertable = false, updatable = false)
    private Produk produk;

    @ManyToOne
    @JoinColumn(name = "IdKriteriaPaket", insertable = false, updatable = false)
    private KriteriaPaket kriteriaPaket;

    @ManyToOne
    @JoinColumn(name = "IdInterval", insertable = false, updatable = false)
    private IntervalPembayaran intervalPembayaran;

    @ManyToOne
    @JoinColumn(name = "IdKategori", insertable = false, updatable = false)
    private Kategori kategoriPOT;

    @ManyToOne
    @JoinColumn(name = "IdMerk", insertable = false, updatable = false)
    private Merk merkPOT;

    @ManyToOne
    @JoinColumn(name = "IdTipe", insertable = false, updatable = false)
    private Tipe tipePOT;

    @ManyToOne
    @JoinColumn(name = "IdModel", insertable = false, updatable = false)
    private Model modelPOT;

    @ManyToMany
    @JoinTable(
            name = "POTCabang",
            joinColumns = @JoinColumn(name = "IdPOT"),
            inverseJoinColumns = @JoinColumn(name = "IdCabang")
    )
    private Set<Cabang> cabangSet;

    @OneToMany(mappedBy = "potDataLeads")
    private List<DataLeads> dataLeadsList;
}
