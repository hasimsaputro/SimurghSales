package com.sales.repository;

import com.sales.entity.DataLeads;
import com.sales.entity.Kategori;
import com.sales.entity.KeteranganAplikasi;
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
}
