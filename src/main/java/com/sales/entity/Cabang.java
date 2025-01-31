package com.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Cabang")
public class Cabang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "NamaCabang", length = 50, nullable = false)
    private String namaCabang;

    @Column(name = "IdTipeStruktur", nullable = false)
    private Integer idTipeStruktur;

    @Column(name = "Alamat", length = 100, nullable = false)
    private String Alamat;

    @Column(name = "IdKelurahan", nullable = false)
    private Integer idKelurahan;

    @Column(name = "NomorTelepon", length = 20)
    private String nomorTelepon;

    @Column(name = "NomorNPWP", length = 20)
    private String nomorNPWP;

    @Column(name = "TanggalBerdiri")
    private Date tanggalBerdiri;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "IdTipeStruktur",insertable = false,updatable = false)
    private TipeStruktur tipeStruktur;

    @ManyToOne
    @JoinColumn(name = "IdKelurahan",insertable = false,updatable = false)
    private Kelurahan kelurahan;

    @ManyToMany
    @JoinTable(
            name = "ProdukCabang",
            joinColumns = @JoinColumn(name = "IdCabang"),
            inverseJoinColumns = @JoinColumn(name = "IdProduk")
    )
    private Set<Produk> produkSet;

    @OneToMany(mappedBy = "cabangMitraAgen")
    private List<MitraAgen> mitraAgenList;

    @ManyToMany(mappedBy = "cabangSet")
    private Set<WilayahHargaPasar> wilayahHargaPasarSet;
}
