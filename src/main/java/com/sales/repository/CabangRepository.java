package com.sales.repository;

import com.sales.entity.Cabang;
import com.sales.entity.Debitur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CabangRepository extends JpaRepository<Cabang, Integer> {
    @Query("""
            SELECT ca
            FROM Cabang ca
            WHERE ca.namaCabang = :namaCab
            """)
    Cabang getCabangByNama(String namaCab);
}
