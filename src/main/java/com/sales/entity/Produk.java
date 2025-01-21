package com.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Produk")
public class Produk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "NamaProduk", length = 100, nullable = false)
    private String namaProduk;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @ManyToMany(mappedBy = "produkSet")
    private Set<Cabang> cabangSet;

    @OneToMany(mappedBy = "produk")
    private List<POT> potList;

    @OneToMany(mappedBy = "produkMitraAgen")
    private List<MitraAgen> mitraAgenList;
}
