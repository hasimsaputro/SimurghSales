package com.sales.repository;

import com.sales.entity.MitraAgen;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MitraAgenRepository extends JpaRepository<MitraAgen, String> {
    @Query("""
            SELECT COUNT(1)
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
            SELECT COUNT(1)
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND mit.id LIKE %:search%
            """)
    int getTotalPagesById(String search);

    @Query("""
            SELECT COUNT(1)
            FROM MitraAgen mit
            JOIN mit.tipeMasterMitraAgen tm
            WHERE mit.deleteDate IS NULL
            AND tm.namaTipeMaster = :search
            """)
    int getTotalpagesByTipe(String search);

    @Query("""
            SELECT COUNT(1)
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND mit.namaMitraAgen LIKE %:search%
            """)
    int getTotalpagesByName(String search);

    @Query("""
            SELECT COUNT(1)
            FROM MitraAgen mit
            JOIN mit.kelurahanDomisiliMitraAgen kel
            WHERE mit.deleteDate IS NULL
            AND kel.namaKelurahan = :search
            """)
    int getTotalpagesByKelurahan(String search);

    @Query("""
            SELECT COUNT(1)
            FROM MitraAgen mit
            JOIN mit.cabangMitraAgen cb
            WHERE mit.deleteDate IS NULL
            AND cb.namaCabang = :search
            """)
    int getTotalpagesByCabang(String search);

    @Query("""
            SELECT COUNT(1)
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND mit.status = :search
            """)
    int getTotalpagesByStatus(Boolean search);



    @Query("""
            SELECT mit
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            """)
    List<MitraAgen> getAllMitraAgen(Pageable pageable);

    @Query("""
            SELECT mit
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND mit.id LIKE %:search%
            """)
    List<MitraAgen> getMitraAgenById(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT mit
            FROM MitraAgen mit
            JOIN mit.tipeMasterMitraAgen tm
            WHERE mit.deleteDate IS NULL
            AND tm.namaTipeMaster = :search
            """)
    List<MitraAgen> getMitraAgenByTipe(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT mit
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND mit.namaMitraAgen LIKE %:search%
            """)
    List<MitraAgen> getMitraAgenByName(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT mit
            FROM MitraAgen mit
            JOIN mit.kelurahanDomisiliMitraAgen kel
            WHERE mit.deleteDate IS NULL
            AND kel.namaKelurahan = :search
            """)
    List<MitraAgen> getMitraAgenByKelurahan(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT mit
            FROM MitraAgen mit
            JOIN mit.cabangMitraAgen cb
            WHERE mit.deleteDate IS NULL
            AND cb.namaCabang = :search
            """)
    List<MitraAgen> getMitraAgenByCabang(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT mit
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND mit.status = :search
            """)
    List<MitraAgen> getMitraAgenByStatus(Pageable pageable, @Param("search") Boolean search);



    @Query("""
            SELECT DISTINCT mit.id
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            """)
    List<String> getMitraAgenItemsById();

    @Query("""
            SELECT DISTINCT tm.namaTipeMaster
            FROM MitraAgen mit
            JOIN mit.tipeMasterMitraAgen tm
            """)
    List<String> getMitraAgenItemsByTipe();

    @Query("""
            SELECT DISTINCT mit.namaMitraAgen
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            """)
    List<String> getMitraAgenItemsByName();

    @Query("""
            SELECT DISTINCT kel.namaKelurahan
            FROM MitraAgen mit
            JOIN mit.kelurahanDomisiliMitraAgen kel
            """)
    List<String> getMitraAgenByItemsKelurahan();

    @Query("""
            SELECT DISTINCT cb.namaCabang
            FROM MitraAgen mit
            JOIN mit.cabangMitraAgen cb
            """)
    List<String> getMitraAgenItemsByCabang();

    @Query(value = "SELECT DISTINCT CASE WHEN mit.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM MitraAgen mit", nativeQuery = true)
    List<String> getMitraAgenItemsByStatus();



    @Query("""
            SELECT mit.id
            FROM MitraAgen mit
            WHERE mit.Id LIKE :prefix
            ORDER BY mit.id DESC
            LIMIT 1
            """)
    String getLastNumberById(String prefix);

}
