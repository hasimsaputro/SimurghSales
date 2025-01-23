package com.sales.repository;

import com.sales.entity.Kategori;
import com.sales.entity.Tipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipeRepository extends JpaRepository<Tipe, String > {
    @Query("""
            SELECT tip
            FROM Tipe tip
            WHERE tip.idKategori = :kategoriId AND tip.idMerk = :merkId
            """)
    List<Tipe> getAll(Integer kategoriId, String merkId);
}
