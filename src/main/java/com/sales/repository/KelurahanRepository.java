package com.sales.repository;

import com.sales.entity.Kelurahan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KelurahanRepository extends JpaRepository<Kelurahan, Integer> {
    @Query("""
            SELECT kel
            FROM Kelurahan kel
            """)
    List<Kelurahan> getAllKelurahan();

    @Query("""
            SELECT kel
            FROM Kelurahan kel
            WHERE kel.id = :id
            """)
    Kelurahan getKelurahanById(Integer id);

    @Query("""
            SELECT kel
            FROM Kelurahan kel
            WHERE kel.namaKelurahan = :namaKelurahan
            """)
    Kelurahan getKelurahanByName(String namaKelurahan);
}
