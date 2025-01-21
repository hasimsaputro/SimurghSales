package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Model")
public class Model {
    @Id
    @Column(name = "Id", length = 10, nullable = false)
    private String id;

    @Column(name = "IdKategori", nullable = false)
    private Integer idKategori;

    @Column(name = "IdMerk", length = 10, nullable = false)
    private String idMerk;

    @Column(name = "IdTipe", length = 10, nullable = false)
    private String idTipe;

    @Column(name = "NamaModel", length = 100, nullable = false)
    private String namaModel;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "IdKategori", insertable = false, updatable = false)
    private Kategori kategoriModel;

    @ManyToOne
    @JoinColumn(name = "IdMerk", insertable = false, updatable = false)
    private Merk merkModel;

    @ManyToOne
    @JoinColumn(name = "IdTipe", insertable = false, updatable = false)
    private Tipe tipeModel;

    @OneToMany(mappedBy = "modelDataLeads")
    private List<DataLeads> dataLeadsList;

    @OneToMany(mappedBy = "modelPOT")
    private List<POT> potList;
}