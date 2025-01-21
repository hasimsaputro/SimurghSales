package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Jenis")
public class Jenis {
    @Id
    @Column(name = "Id", length = 10, nullable = false)
    private String id;

    @Column(name = "IdKategori", nullable = false)
    private Integer idKategori;

    @Column(name = "NamaJenis", length = 100, nullable = false)
    private String namaJenis;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "IdKategori", insertable = false, updatable = false)
    private Kategori kategoriJenis;

    @OneToMany(mappedBy = "jenisTipe")
    private List<Tipe> tipeList;
}
