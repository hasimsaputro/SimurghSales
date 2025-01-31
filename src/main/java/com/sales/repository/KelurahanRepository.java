package com.sales.repository;

import com.sales.entity.Debitur;
import com.sales.entity.Kelurahan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KelurahanRepository extends JpaRepository<Kelurahan, String> {
    @Query("""
            SELECT kel
            FROM Kelurahan kel
            WHERE kel.namaKelurahan = :namaKel
            """)
    Kelurahan getKelurahanByNama(String namaKel);

    @Query("""
            SELECT kl.namaKelurahan
            FROM Kelurahan kl
            """)
    List<String> getItemsKelurahan();


}