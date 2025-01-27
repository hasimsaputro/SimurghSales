package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "WilayahHargaPasar")
public class WilayahHargaPasar {
    @Id
    @Column(name = "Id", length = 5)
    private String Id;

    @Column(name = "NamaWilayah", length = 20,nullable = false)
    private String namaWilayah;

    @Column(name = "IdKategori", nullable = false)
    private Integer idKategori;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "IdKategori", insertable = false,updatable = false)
    private Kategori kategoriWilayah;

    @OneToMany(mappedBy = "wilayahHargaPasar")
    private List<HargaPasar> hargaPasarList;

    @ManyToMany
    @JoinTable(
            name = "WilayahCabang",
            joinColumns = @JoinColumn(name = "IdWilayah"),
            inverseJoinColumns = @JoinColumn(name = "IdCabang")
    )
    private Set<Cabang> cabangSet;
}
