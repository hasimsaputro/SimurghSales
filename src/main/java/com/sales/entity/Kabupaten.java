package com.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Kabupaten")
public class Kabupaten {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "NamaKabupaten", length = 100, nullable = false)
    private String namaKabupatenKota;

    @Column(name = "IdProvinsi", nullable = false)
    private Integer idProvinsi;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @ManyToOne
    @JoinColumn(name = "IdProvinsi",insertable = false,updatable = false)
    private Provinsi provinsi;

    @OneToMany(mappedBy = "kabupaten")
    private List<Kecamatan> kecamatanList;

    @OneToMany(mappedBy = "kabupaten")
    private List<Kelurahan> kelurahanList;
}
