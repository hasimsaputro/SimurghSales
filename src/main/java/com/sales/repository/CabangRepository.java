package com.sales.repository;

import com.sales.entity.Cabang;
import com.sales.entity.Produk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CabangRepository extends JpaRepository<Cabang, Integer> {
    @Query("""
            SELECT cab
            FROM Cabang cab
            WHERE cab.namaCabang = :namaCabang
            """)
    Produk getCabangByName(String namaCabang);
}
