package com.sales.repository;

import com.sales.entity.Produk;
import org.springframework.data.jpa.repository.Query;

public interface CabangRepository {
    @Query("""
            SELECT cab
            FROM Cabang cab
            WHERE cab.namaCabang = :namaCabang
            """)
    Produk getCabangByName(String namaCabang);
}
