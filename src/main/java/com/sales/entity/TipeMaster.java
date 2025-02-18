package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "TipeMaster")
public class TipeMaster {
    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NamaTipeMaster", length = 100, nullable = false)
    private String namaTipeMaster;

    @Column(name = "Status", nullable = false)
    private Boolean status;


    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @OneToMany(mappedBy = "tipeMasterMitraAgen")
    private List<MitraAgen> mitraAgenList;
}
