package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "KeteranganAplikasi")
public class KeteranganAplikasi {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NamaKeteranganAplikasi", length = 100, nullable = false)
    private String namaKeteranganAplikasi;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "keteranganAplikasi")
    private List<DataLeads> dataLeadsList;
}
