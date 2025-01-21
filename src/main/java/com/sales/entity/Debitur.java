package com.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Debitur")
public class Debitur {
    @Id
    @Column(name = "Id", length = 10)
    private String id;

    @Column(name = "NamaDepan", length = 50, nullable = false)
    private String namaDepan;

    @Column(name = "NamaTengah", length = 50)
    private String namaTengah;

    @Column(name = "NamaAkhir", length = 50)
    private String namaAkhir;

    @Column(name = "NamaPanggilan", length = 10, nullable = false)
    private String namaPanggilan;

    @Column(name = "IdIdentitas", nullable = false)
    private Integer idIdentitas;

    @Column(name = "NomorIdentitas", length = 20, nullable = false)
    private String nomorIdentitas;

    @Column(name = "JenisKelamin", length = 1, nullable = false)
    private String jenisKelamin;

    @Column(name = "AlamatIdentitas", length = 100, nullable = false)
    private String alamatIdentitas;

    @Column(name = "IdKelurahan", nullable = false)
    private Integer idKelurahan;

    @Column(name = "AlamatDomisili", length = 100, nullable = false)
    private String alamatDomisili;

    @Column(name = "IdKelurahanDomisili", nullable = false)
    private Integer idKelurahanDomisili;

    @Column(name = "NomorHP1", length = 20, nullable = false)
    private String nomorHp1;

    @Column(name = "NomorHP2", length = 20)
    private String nomorHP2;

    @Column(name = "NomorTelepon", length = 20)
    private String nomorTelepon;

    @ManyToOne
    @JoinColumn(name = "IdIdentitas", insertable = false,updatable = false)
    private Identitas identitas;

    @ManyToOne
    @JoinColumn(name = "IdKelurahan", insertable = false,updatable = false)
    private Kelurahan kelurahan;

    @ManyToOne
    @JoinColumn(name = "IdKelurahanDomisili", insertable = false,updatable = false)
    private Kelurahan kelurahanDomisili;
}
