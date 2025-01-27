package com.sales.repository;

import com.sales.entity.Merk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerkRepository extends JpaRepository<Merk, String> {

    @Query("""
            SELECT mer
            FROM Merk mer
            WHERE mer.idKategori = :kategoriId
            """)
    List<Merk> getAll(Integer kategoriId);
}
