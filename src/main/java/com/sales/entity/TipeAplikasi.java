package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
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

    @Column(name = "DeleteDate", nullable = false)
    private LocalDate deleteDate;

    @OneToMany(mappedBy = "tipeAplikasiDataLeads")
    private List<DataLeads> dataLeadsList;
}
