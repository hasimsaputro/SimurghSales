package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Bank")
public class Bank {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NamaBank", length = 100, nullable = false)
    private String namaBank;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "bankMitraAgen")
    private List<MitraAgen> mitraAgenList;
}
