package com.sales.repository;

import com.sales.entity.MitraAgen;
import com.sales.entity.TipeMaster;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipeMasterRepository extends JpaRepository<TipeMaster, Integer> {
    @Query("""
            SELECT tip
            FROM TipeMaster tip
            """)
    List<TipeMaster> getAllTipeMaster();

    @Query("""
            SELECT tip
            FROM TipeMaster tip
            WHERE tip.id = :id
            """)
    TipeMaster getTipeMasterById(Integer id);

    @Query("""
            SELECT tip
            FROM TipeMaster tip
            WHERE tip.namaTipeMaster = :namaTipeMaster
            """)
    TipeMaster getTipeMasterByName(String namaTipeMaster);

    @Query("""
            SELECT tip.namaTipeMaster
            FROM TipeMaster tip
            """)
    List<String> getTipeMasterItems();
}
