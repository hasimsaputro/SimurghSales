package com.sales.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "TipeUnit")
public class TipeUnit {
    @Id
    @Column(name = "Id", nullable = false)
    private String id;

    @Column(name = "NamaUnit", length = 50, nullable = false)
    private String namaUnit;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @OneToMany(mappedBy = "tipeUnitHargaPasar")
    private List<HargaPasar> hargaPasarList;
}
