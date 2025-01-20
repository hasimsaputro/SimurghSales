package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TipeStruktur")
public class TipeStruktur {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NamaStruktur", length = 20, nullable = false)
    private String namaStruktur;
}
