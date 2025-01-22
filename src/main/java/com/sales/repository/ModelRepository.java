package com.sales.repository;

import com.sales.entity.Kategori;
import com.sales.entity.Model;
import com.sales.entity.Tipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, String> {
    @Query("""
            SELECT mod
            FROM Model mod
            WHERE mod.idKategori = :kategoriId AND mod.idMerk = :merkId AND mod.idTipe = :tipeId
            """)
    List<Model> getAll(Integer kategoriId, String merkId, String tipeId);
}
