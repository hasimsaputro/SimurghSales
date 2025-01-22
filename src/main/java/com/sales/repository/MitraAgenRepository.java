package com.sales.repository;

import com.sales.entity.MitraAgen;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
            AND (mit.id LIKE %:search% OR :search IS NULL)
            """)
    int getTotalPagesById(String search);

    @Query("""
            SELECT COUNT(1)
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND (mit.idTipeMaster = :search OR :search IS NULL)
            """)
    int getTotalpagesByTipe(String search);

    @Query("""
            SELECT COUNT(1)
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND (mit.namaMitraAgen LIKE %:search% OR :search IS NULL)
            """)
    int getTotalpagesByName(String name);

    @Query("""
            SELECT COUNT(1)
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND (mit.idKelurahanDomisili = :search OR :search IS NULL)
            """)
    int getTotalpagesByKelurahan(String search);

    @Query("""
            SELECT COUNT(1)
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND (mit.idCabang = :search OR :search IS NULL)
            """)
    int getTotalpagesByCabang(String search);

    @Query("""
            SELECT COUNT(1)
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND (mit.status = :search OR :search IS NULL)
            """)
    int getTotalpagesByStatus(String search);



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
    List<MitraAgen> getMitraAgenById(Pageable pageable, String search);

    @Query("""
            SELECT mit
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND mit.idTipeMaster = :search
            """)
    List<MitraAgen> getMitraAgenByTipe(Pageable pageable, String search);

    @Query("""
            SELECT mit
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND mit.namaMitraAgen LIKE %:search%
            """)
    List<MitraAgen> getMitraAgenByName(Pageable pageable, String search);

    @Query("""
            SELECT mit
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND mit.idKelurahanDomisili = :search
            """)
    List<MitraAgen> getMitraAgenByKelurahan(Pageable pageable, String search);

    @Query("""
            SELECT mit
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND mit.idCabang = :search
            """)
    List<MitraAgen> getMitraAgenByCabang(Pageable pageable, String search);

    @Query("""
            SELECT mit
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND mit.status = :search
            """)
    List<MitraAgen> getMitraAgenByStatus(Pageable pageable, String search);



    @Query("""
            SELECT mit.id
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            """)
    List<String> getMitraAgenItemsById();

    @Query("""
            SELECT tm.namaTipeMaster
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            JOIN mit.tipeMasterMitraAgen tm
            """)
    List<String> getMitraAgenItemsByTipe();

    @Query("""
            SELECT mit.namaMitraAgen
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            """)
    List<String> getMitraAgenItemsByName();

    @Query("""
            SELECT kel.namaKelurahan
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            AND mit.kelurahanDomisiliMitraAgen kel
            """)
    List<String> getMitraAgenByItemsKelurahan();

    @Query("""
            SELECT cb.namaCabang
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            JOIN mit.cabangMitraAgen cb
            """)
    List<String> getMitraAgenItemsByCabang();

    @Query(value = """
            SELECT CASE
            WHEN mit.status = 1
            THEN 'Aktif' ELSE 'Tidak Aktif'
            END
            FROM MitraAgen mit
            WHERE mit.deleteDate IS NULL
            """, nativeQuery = true)
    List<String> getMitraAgenItemsByStatus();



    @Query("""
            SELECT mit.id
            FROM MitraAgen mit
            WHERE mit.Id LIKE :prefix
            ORDER BY mit.id DESC
            """)
    String getLastNumberById(String prefix);

}
