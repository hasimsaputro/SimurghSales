package com.sales.repository;

import com.sales.entity.Debitur;
import com.sales.entity.Referensi;
import com.sales.entity.TipeAplikasi;
import com.sales.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReferensiRepository extends JpaRepository<Referensi, String> {
    @Query("""
    SELECT ref
    FROM Referensi ref
    WHERE CONCAT(ref.namaDepan, " ", ref.namaTengah, " ", ref.namaAkhir) = :name
            """)
    Referensi getUserByName(String name);

    @Query("""
        SELECT t
        FROM Referensi t
        WHERE
            (:id IS NULL OR t.id LIKE %:id%)
            AND (:nama IS NULL OR CONCAT(t.namaDepan,' ', t.namaTengah,' ', t.namaAkhir) LIKE %:nama%)
            AND (:status IS NULL OR t.status = :status)
        """)
    List<Referensi> getAllBySearch(Pageable pagination, String id, String nama, Boolean status);

    @Query("""
        SELECT COUNT(*)
        FROM Referensi t
        WHERE
            (:id IS NULL OR t.id LIKE %:id%)
            AND (:nama IS NULL OR CONCAT(t.namaDepan,' ', t.namaTengah,' ', t.namaAkhir) LIKE %:nama%)
            AND (:status IS NULL OR t.status = :status)
        """)
    Integer countAllBySearch(String id, String nama, Boolean status);


    @Query("""
        SELECT t
        FROM Referensi t
        WHERE t.id = :id
        """)
    Referensi getReferensiById(String id);
}
