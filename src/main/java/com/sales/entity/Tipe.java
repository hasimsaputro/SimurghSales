package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "Tipe")
public class Tipe {
    @Id
    @Column(name = "Id", length = 10, nullable = false)
    private String id;

    @Column(name = "IdKategori", nullable = false)
    private Integer idKategori;

    @Column(name = "IdMerk", length = 10, nullable = false)
    private String idMerk;

    @Column(name = "NamaTipe", length = 100, nullable = false)
    private String namaTipe;

    @Column(name = "IdJenis", length = 10, nullable = false)
    private String idJenis;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @ManyToOne
    @JoinColumn(name = "IdKategori", insertable = false, updatable = false)
    private Kategori kategoriTipe;

    @ManyToOne
    @JoinColumn(name = "IdMerk", insertable = false, updatable = false)
    private Merk merkTipe;

    @ManyToOne
    @JoinColumn(name = "IdJenis", insertable = false, updatable = false)
    private Jenis jenisTipe;

    @OneToMany(mappedBy = "tipeModel")
    private List<Model> modelList;

    @OneToMany(mappedBy = "tipeHargaPasar")
    private List<HargaPasar> hargaPasarList;

    @OneToMany(mappedBy = "tipeDataLeads")
    private List<DataLeads> dataLeadsList;

    @OneToMany(mappedBy = "tipePOT")
    private List<POT> potList;
}
