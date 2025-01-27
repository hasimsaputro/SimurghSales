package com.sales.repository;

import com.sales.entity.DataLeads;
import com.sales.entity.HargaPasar;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HargaPasarRepository extends JpaRepository<HargaPasar, Integer> {
    @Query("""
            SELECT hp.harga
            FROM HargaPasar hp
            WHERE hp.idKategori = :idKategori
            AND hp.idMerk = :idMerk
            AND hp.idTipe = :idTipe
            AND hp.idModel = :idModel
            """)
    BigDecimal getHarga(Integer idKategori, String idMerk, String idTipe, String idModel);
}
