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
@Table(name = "PosisiJabatan")
public class PosisiJabatan {
    @Id
    @Column(name = "Id", length = 5)
    private String id;

    @Column(name = "NamaPosisiJabatan",length = 50, nullable = false)
    private String namaPosisiJabatan;

    @Column(name = "Status",nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "posisiJabatan")
    private List<User> userList;
}
