package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "MitraAgen")
public class MitraAgen {
    @Id
    @Column(name = "Id", length = 10, nullable = false)
    private String id;

    @Column(name = "IdTipeMaster", nullable = false)
    private Integer idTipeMaster;

    @Column(name = "IdProduk", nullable = false)
    private Integer idProduk;

    @Column(name = "IdCabang", nullable = false)
    private Integer idCabang;

    @Column(name = "IdIdentitas", nullable = false)
    private Integer idIdentitas;

    @Column(name = "NomorIdentitas", length = 20, nullable = false)
    private String nomorIdentitas;

    @Column(name = "NamaMitraAgen", length = 100, nullable = false)
    private String namaMitraAgen;

    @Column(name = "JenisKelamin", length = 1, nullable = false)
    private String jenisKelamin;

    @Column(name = "NPWP", length = 20)
    private String npwp;

    @Column(name = "AlamatIdentitas", length = 200, nullable = false)
    private String alamatIdentitas;

    @Column(name = "IdKelurahanIdentitas", nullable = false)
    private Integer idKelurahanIdentitas;

    @Column(name = "AlamatDomisili", length = 200, nullable = false)
    private String alamatDomisili;

    @Column(name = "IdKelurahanDomisili", nullable = false)
    private Integer idKelurahanDomisili;

    @Column(name = "TempatLahir", length = 50, nullable = false)
    private String tempatLahir;

    @Column(name = "TanggalLahir", nullable = false)
    private LocalDate tanggalLahir;

    @Column(name = "NomorTelepon", length = 20)
    private String nomorTelepon;

    @Column(name = "NomorHandphone", length = 20, nullable = false)
    private String nomorHandphone;

    @Column(name = "IdBank", nullable = false)
    private Integer idBank;

    @Column(name = "NomorRekening", length = 20, nullable = false)
    private String nomorRekening;

    @Column(name = "NamaRekening", length = 100, nullable = false)
    private String namaRekening;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "IdTipeMaster", insertable = false, updatable = false)
    private TipeMaster tipeMasterMitraAgen;

    @ManyToOne
    @JoinColumn(name = "IdProduk", insertable = false, updatable = false)
    private Produk produkMitraAgen;

    @ManyToOne
    @JoinColumn(name = "IdCabang", insertable = false, updatable = false)
    private Cabang cabangMitraAgen;

    @ManyToOne
    @JoinColumn(name = "IdIdentitas", insertable = false, updatable = false)
    private Identitas identitasMitraAgen;

    @ManyToOne
    @JoinColumn(name = "IdKelurahanIdentitas", insertable = false, updatable = false)
    private Kelurahan kelurahanIdentitasMitraAgen;

    @ManyToOne
    @JoinColumn(name = "IdKelurahanDomisili", insertable = false, updatable = false)
    private Kelurahan kelurahanDomisiliMitraAgen;

    @ManyToOne
    @JoinColumn(name = "IdBank", insertable = false, updatable = false)
    private Bank bankMitraAgen;
}
