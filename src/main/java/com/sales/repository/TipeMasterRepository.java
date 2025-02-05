package com.sales.repository;

import com.sales.entity.MitraAgen;
import com.sales.entity.TipeMaster;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
            WHERE tip.deleteDate IS NULL
            """)
    List<TipeMaster> getAllTipeMaster(Pageable pageable);

    @Query("""
            SELECT COUNT(1)
            FROM TipeMaster tip
            WHERE tip.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
            SELECT COUNT(1)
            FROM TipeMaster tip
            WHERE tip.id = %:search%
            """)
    int getTotalPagesById(String search);

    @Query("""
            SELECT COUNT(1)
            FROM TipeMaster tip
            WHERE tip.deleteDate IS NULL
            AND tip.namaTipeMaster LIKE %:search%
            """)
    int getTotalpagesByName(String search);

    @Query("""
            SELECT COUNT(1)
            FROM TipeMaster tip
            WHERE tip.deleteDate IS NULL
            AND tip.status = :search
            """)
    int getTotalPagesByStatus(Boolean search);


    @Query("""
            SELECT tip
            FROM TipeMaster tip
            WHERE tip.deleteDate IS NULL
            AND tip.id = :search
            """)
    List<TipeMaster> getTipeMasterById(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT tip
            FROM TipeMaster tip
            WHERE tip.deleteDate IS NULL
            AND tip.namaTipeMaster = :search
            """)
    List<TipeMaster> getTipeMasterByName(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT tip
            FROM TipeMaster tip
            WHERE tip.deleteDate IS NULL
            AND tip.status = :search
            """)
    List<TipeMaster> getTipeMasterByStatus(Pageable pageable, @Param("search") Boolean search);

    @Query("""
            SELECT tip.id
            FROM TipeMaster tip
            WHERE tip.deleteDate IS NULL
            """)
    List<String> getTipeMasterItemsById();

    @Query("""
            SELECT tip.namaTipeMaster
            FROM TipeMaster tip
            WHERE tip.deleteDate IS NULL
            """)
    List<String> getTipeMasterItemsByName();

    @Query(value = "SELECT CASE WHEN tip.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM TipeMaster tip", nativeQuery = true)
    List<String> getTipeMasterItemsByStatus();


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
