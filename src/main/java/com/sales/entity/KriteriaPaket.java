package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "KriteriaPaket")
public class KriteriaPaket {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NamaKriteria", length = 50, nullable = false)
    private String namaKriteria;

    @OneToMany(mappedBy = "kriteriaPaket")
    private List<POT> potList;
}
