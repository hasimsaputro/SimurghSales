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
@Table(name = "IntervalPembayaran")
public class IntervalPembayaran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "NamaIntervalPembayaran", length = 50, nullable = false)
    private String namaIntervalPembayaran;
}
