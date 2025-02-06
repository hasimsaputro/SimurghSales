package com.sales.repository;


import com.sales.entity.Debitur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebiturRepository extends JpaRepository<Debitur, String> {
    @Query("""
            SELECT de
            FROM Debitur de
            """)
    List<Debitur> getAllOnly();

    @Query("""
            SELECT de
            FROM Debitur de
            WHERE de.nomorIdentitas = :nomorId
            """)
    Debitur getDebiturByNik(String nomorId);

    @Query("""
    SELECT de
    FROM Debitur de LEFT JOIN de.referensi r
            """)
    List<Debitur> getDebiturReferensi();

}
