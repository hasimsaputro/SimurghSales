package com.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Referensi")
public class Referensi {
    @Id
    private String id;  // Tambahkan ID yang sama dengan Debitur

    @OneToOne
    @MapsId  // Menggunakan ID Debitur sebagai ID Referensi
    @JoinColumn(name = "id")
    private Debitur debitur;

    @Column(name = "NamaDepan", length = 50, nullable = false)
    private String namaDepan;

    @Column(name = "NamaTengah", length = 50)
    private String namaTengah;

    @Column(name = "NamaAkhir", length = 50)
    private String namaAkhir;

    @Column(name = "Status")
    private Boolean status;
}
