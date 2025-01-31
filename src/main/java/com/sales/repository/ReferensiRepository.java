package com.sales.repository;

import com.sales.entity.Debitur;
import com.sales.entity.Referensi;
import com.sales.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReferensiRepository extends JpaRepository<Referensi, String> {
    @Query("""
    SELECT ref
    FROM Referensi ref
    WHERE CONCAT(ref.namaDepan, " ", ref.namaTengah, " ", ref.namaAkhir) = :name
            """)
    Referensi getUserByName(String name);
}
