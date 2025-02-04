package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "Merk")
public class Merk {
    @Id
    @Column(name = "Id", length = 10, nullable = false)
    private String id;

    @Column(name = "IdKategori", nullable = false)
    private Integer idKategori;

    @Column(name = "NamaMerk", length = 100, nullable = false)
    private String namaMerk;

    @Column(name = "IdNegara", nullable = false)
    private Integer idNegara;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @ManyToOne
    @JoinColumn(name = "IdKategori", insertable = false, updatable = false)
    private Kategori kategoriMerk;

    @ManyToOne
    @JoinColumn(name = "IdNegara", insertable = false, updatable = false)
    private Negara negara;

    @OneToMany(mappedBy = "merkTipe")
    private List<Tipe> tipeList;

    @OneToMany(mappedBy = "merkHargaPasar")
    private List<HargaPasar> hargaPasarList;

    @OneToMany(mappedBy = "merkModel")
    private List<Model> modelList;

    @OneToMany(mappedBy = "merkDataLeads")
    private List<DataLeads> dataLeadsList;

    @OneToMany(mappedBy = "merkPOT")
    private List<POT> potList;
}
