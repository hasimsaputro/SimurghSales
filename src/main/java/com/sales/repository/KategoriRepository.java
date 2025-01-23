package com.sales.repository;

import com.sales.entity.DataLeads;
import com.sales.entity.Kategori;
import com.sales.entity.MitraAgen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface KategoriRepository extends JpaRepository<Kategori, Integer> {
    @Query("""
            SELECT kat
            FROM Kategori kat
            """)
    List<Kategori> getAll();
}
