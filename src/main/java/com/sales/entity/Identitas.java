package com.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Identitas")
public class Identitas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "NamaIdentitas", length = 100, nullable = false)
    private String namaIdentitas;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "identitas")
    private List<Debitur> debiturList;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @OneToMany(mappedBy = "identitasMitraAgen")
    private List<MitraAgen> mitraAgenList;
}
