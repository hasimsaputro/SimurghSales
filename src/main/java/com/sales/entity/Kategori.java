package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Kategori")
public class Kategori {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NamaKategori", length = 100, nullable = false)
    private String namaKategori;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "kategoriJenis")
    private List<Jenis> jenisList;

    @OneToMany(mappedBy = "kategoriMerk")
    private List<Merk> merkList;

    @OneToMany(mappedBy = "kategoriTipe")
    private List<Tipe> tipeList;

    @OneToMany(mappedBy = "kategoriModel")
    private List<Model> modelList;

    @OneToMany(mappedBy = "kategoriPOT")
    private List<POT> potList;

    @OneToMany(mappedBy = "ketegoriDataLeads")
    private List<DataLeads> dataLeadsList;
}
