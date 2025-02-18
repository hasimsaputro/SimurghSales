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
@Table(name = "Provinsi")
public class Provinsi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "NamaProvinsi", length = 100, nullable = false)
    private String namaProvinsi;

    @Column(name = "IdNegara", nullable = false)
    private Integer idNegara;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @ManyToOne
    @JoinColumn(name = "IdNegara", insertable = false,updatable = false)
    private Negara negara;

    @OneToMany(mappedBy = "provinsi")
    private List<Kabupaten> kabupatenList;

    @OneToMany(mappedBy = "provinsi")
    private List<Kecamatan> kecamatanList;

    @OneToMany(mappedBy = "provinsi")
    private List<Kelurahan> kelurahanList;
}
