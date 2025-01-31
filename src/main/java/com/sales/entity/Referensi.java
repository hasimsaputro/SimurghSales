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
@Table(name = "Referensi")
public class Referensi {
    @Id
    @Column(name = "Id", length = 10)
    private String id;

    @Column(name = "NamaDepan", length = 50, nullable = false)
    private String namaDepan;

    @Column(name = "NamaTengah", length = 50)
    private String namaTengah;

    @Column(name = "NamaAkhir", length = 50)
    private String namaAkhir;


}
