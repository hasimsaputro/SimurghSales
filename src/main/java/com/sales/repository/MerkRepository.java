package com.sales.repository;

import com.sales.entity.Merk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerkRepository extends JpaRepository<Merk, String> {

    @Query("""
            SELECT me.namaMerk
            FROM Merk me
            WHERE me.idKategori :
            """)
    List<String> getMerkByKategori(Integer idKategori);
}
