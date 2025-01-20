package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Jabatan")
public class Jabatan {
    @Id
    @Column(name = "Id", length = 5, nullable = false)
    private String id;

    @Column(name = "IdDepartemen", length = 5, nullable = false)
    private String idDepartment;

    @Column(name = "NamaJabatan", length = 100, nullable = false)
    private String namaJabatan;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "IdDepartemen", insertable = false, updatable = false)
    private Department department;
}
