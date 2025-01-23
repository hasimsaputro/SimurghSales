package com.sales.repository;

import com.sales.entity.Produk;
import com.sales.entity.TipeAplikasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TipeAplikasiRepository extends JpaRepository<TipeAplikasi, Integer> {
    @Query("""
            SELECT t
            FROM TipeAplikasi t
            WHERE t.namaTipeAplikasi = :tipeAplikasiNama
            """)
    TipeAplikasi getTipeAplikasiByName(String tipeAplikasiNama);
}
