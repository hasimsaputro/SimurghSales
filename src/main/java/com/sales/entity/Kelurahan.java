package com.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Kelurahan")
public class Kelurahan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "NamaKelurahan", length = 100, nullable = false)
    private String namaKelurahan;

    @Column(name = "KodePos", length = 20, nullable = false)
    private String kodePos;

    @Column(name = "IdProvinsi", nullable = false)
    private Integer idProvinsi;

    @Column(name = "IdKabupaten", nullable = false)
    private Integer idKabupaten;

    @Column(name = "IdKecamatan", nullable = false)
    private Integer idKecamatan;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "IdProvinsi", insertable = false,updatable = false)
    private Provinsi provinsi;

    @ManyToOne
    @JoinColumn(name = "IdKabupaten", insertable = false,updatable = false)
    private Kabupaten kabupaten;

    @ManyToOne
    @JoinColumn(name = "IdKecamatan", insertable = false,updatable = false)
    private Kecamatan kecamatan;

    @OneToMany(mappedBy = "kelurahan")
    private List<Cabang> cabangList;

    @OneToMany(mappedBy = "kelurahan")
    private List<Debitur> debiturList;

    @OneToMany(mappedBy = "kelurahanDomisili")
    private List<Debitur> debiturDomisiliList;

    @OneToMany(mappedBy = "kelurahanIdentitasMitraAgen")
    private List<MitraAgen> mitraAgenIdentitasList;

    @OneToMany(mappedBy = "kelurahanDomisiliMitraAgen")
    private List<MitraAgen> mitraAgenDomisiliList;
}
