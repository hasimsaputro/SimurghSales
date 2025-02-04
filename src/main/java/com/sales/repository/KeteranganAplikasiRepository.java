package com.sales.repository;

import com.sales.entity.DataLeads;
import com.sales.entity.Kategori;
import com.sales.entity.KeteranganAplikasi;
import com.sales.entity.TipeAplikasi;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeteranganAplikasiRepository extends JpaRepository<KeteranganAplikasi, Integer> {
    @Query("""
            SELECT ka
            FROM KeteranganAplikasi ka
            WHERE ka.namaKeteranganAplikasi = :namaKeteranganAplikasi
            """)
    KeteranganAplikasi getKeteranganAplikasiByName(String namaKeteranganAplikasi);

    @Query("""
        SELECT t
        FROM KeteranganAplikasi t
        WHERE
            (:id IS NULL OR CAST(t.id AS string) LIKE %:id%)
            AND (:nama IS NULL OR t.namaKeteranganAplikasi LIKE %:nama%)
            AND (:status IS NULL OR t.status = :status)
        """)
    List<KeteranganAplikasi> getAllBySearch(Pageable pagination, Integer id, String nama, Boolean status);

    @Query("""
        SELECT COUNT(*)
        FROM KeteranganAplikasi t
        WHERE
            (:id IS NULL OR CAST(t.id AS string) LIKE %:id%)
            AND (:nama IS NULL OR t.namaKeteranganAplikasi LIKE %:nama%)
            AND (:status IS NULL OR t.status = :status)
        """)
    Integer countAllBySearch(Integer id, String nama, Boolean status);


    @Query("""
        SELECT t
        FROM KeteranganAplikasi t
        WHERE t.id = :id
        """)
    KeteranganAplikasi getKeteranganAplikasiById(Integer id);
}
