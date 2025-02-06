package com.sales.repository;

import com.sales.entity.DataLeads;
import com.sales.entity.Produk;
import com.sales.entity.TipeAplikasi;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipeAplikasiRepository extends JpaRepository<TipeAplikasi, Integer> {
    @Query("""
            SELECT t
            FROM TipeAplikasi t
            WHERE t.namaTipeAplikasi = :tipeAplikasiNama
            """)
    TipeAplikasi getTipeAplikasiByName(String tipeAplikasiNama);

    @Query("""
            SELECT t
            FROM TipeAplikasi t
            WHERE t.namaTipeAplikasi = :tipeAplikasiNama
            """)
    List<TipeAplikasi> getTipeAplikasiBySearchPage(String tipeAplikasiNama);

    @Query("""
        SELECT t
        FROM TipeAplikasi t
        WHERE
            (:id IS NULL OR CAST(t.id AS string) LIKE %:id%)
            AND (:nama IS NULL OR t.namaTipeAplikasi LIKE %:nama%)
            AND (:status IS NULL OR t.status = :status)
            AND t.deleteDate IS NULL
        """)
    List<TipeAplikasi> getAllBySearch(Pageable pagination, Integer id, String nama, Boolean status);

    @Query("""
        SELECT COUNT(*)
        FROM TipeAplikasi t
        WHERE
            (:id IS NULL OR CAST(t.id AS string) LIKE %:id%)
            AND (:nama IS NULL OR t.namaTipeAplikasi LIKE %:nama%)
            AND (:status IS NULL OR t.status = :status)
            AND t.deleteDate IS NULL
        """)
    Integer countAllBySearch(Integer id, String nama, Boolean status);


    @Query("""
        SELECT t
        FROM TipeAplikasi t
        WHERE t.id = :id
        """)
    TipeAplikasi getTipeAplikasiById(Integer id);
}
