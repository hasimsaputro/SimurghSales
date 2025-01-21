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
@Table(name = "Kecamatan")
public class Kecamatan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "NamaKecamatan", length = 100, nullable = false)
    private String namaKecamatan;

    @Column(name = "IdProvinsi", nullable = false)
    private Integer idProvinsi;

    @Column(name = "IdKabupaten", nullable = false)
    private Integer idKabupaten;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "IdProvinsi", insertable = false,updatable = false)
    private Provinsi provinsi;

    @ManyToOne
    @JoinColumn(name = "IdKabupaten", insertable = false,updatable = false)
    private Kabupaten kabupaten;

    @OneToMany(mappedBy = "kecamatan")
    private List<Kelurahan> kelurahanList;
}
