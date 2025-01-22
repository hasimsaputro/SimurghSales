package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Divisi")
public class Divisi {
    @Id
    @Column(name = "Id", length = 5, nullable = false)
    private String id;

    @Column(name = "NamaDivisi", length = 100, nullable = false)
    private String namaDivisi;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "divisi")
    private List<Departemen> departemen;
}
