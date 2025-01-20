package com.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Department")
public class Department {
    @Id
    @Column(name = "Id",length = 5)
    private String id;

    @Column(name = "IdDivisi",length = 5,nullable = false)
    private String idDivisi;

    @Column(name = "Department", length = 100,nullable = false)
    private String namaDepartment;

    @Column(name = "Status",nullable = false)
    private Boolean Status;

    @ManyToOne
    @JoinColumn(name = "IdDivisi", insertable = false, updatable = false)
    private Divisi divisi;
}
