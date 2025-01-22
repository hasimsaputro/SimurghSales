package com.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "[User]")
public class User{
    @Id
    @Column(name = "NIK",length = 7)
    private String nik;

    @Column(name = "NamaKaryawan", length = 20, nullable = false)
    private String namaKaryawan;

    @Column(name = "Email", length = 20, nullable = false)
    private String email;

    @Column(name = "IdJabatan", length = 5,nullable = false)
    private String idJabatan;

    @Column(name = "IdCabang", nullable = false)
    private Integer idCabang;

    @Column(name = "IdPosisiJabatan", nullable = false)
    private String idPosisiJabatan;

    @Column(name = "EmailEksternal", length = 20)
    private String emailEksternal;

    @Column(name = "NomorHP", length = 20)
    private String nomorHp;

    @Column(name = "Status", nullable = false)
    private Boolean Status;

    @ManyToOne
    @JoinColumn(name = "IdJabatan",insertable = false,updatable = false)
    private Jabatan jabatan;

    @ManyToOne
    @JoinColumn(name = "IdCabang",insertable = false,updatable = false)
    private Cabang cabang;

    @ManyToOne
    @JoinColumn(name = "IdPosisiJabatan",insertable = false,updatable = false)
    private PosisiJabatan posisiJabatan;

    @OneToMany(mappedBy = "userDataLeads")
    private List<DataLeads> dataLeadsList;
}
