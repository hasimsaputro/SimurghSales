package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "KeteranganAplikasi")
public class KeteranganAplikasi {
    @Id
    @Column(name = "KeteranganAplikasi", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NamaKeteranganAplikasi", length = 100, nullable = false)
    private String namaKeteranganAplikasi;

    @Column(name = "Status", nullable = false)
    private Boolean status;
}
