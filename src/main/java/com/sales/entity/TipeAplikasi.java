package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "TipeAplikasi")
public class TipeAplikasi {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NamaTipeAplikasi", length = 100, nullable = false)
    private String namaTipeAplikasi;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "tipeAplikasi")
    private List<DataLeads> dataLeadsList;
}
