package com.sales.repository;

import com.sales.entity.WilayahHargaPasar;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WilayahHargaPasarRepository extends JpaRepository<WilayahHargaPasar, String> {
    @Query("""
            SELECT COUNT(1)
            FROM WilayahHargaPasar whp
            WHERE whp.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
            SELECT COUNT(1)
            FROM WilayahHargaPasar whp
            JOIN whp.kategoriWilayah kat
            WHERE whp.deleteDate IS NULL
            AND kat.namaKategori = :search
            """)
    int getTotalPagesByKategori(String search);

    @Query("""
            SELECT COUNT(1)
            FROM WilayahHargaPasar whp
            WHERE whp.deleteDate IS NULL
            AND whp.id = :search
            """)
    int getTotalPagesById(String search);

    @Query("""
            SELECT COUNT(1)
            FROM WilayahHargaPasar whp
            WHERE whp.deleteDate IS NULL
            AND whp.namaWilayah LIKE %:search%
            """)
    int getTotalPagesByName(String search);

    @Query("""
            SELECT COUNT(1)
            FROM WilayahHargaPasar whp
            WHERE whp.deleteDate IS NULL
            AND whp.status = :search
            """)
    int getTotalpagesByStatus(Boolean search);

    //----------------------------------------------------------

    @Query("""
            SELECT whp
            FROM WilayahHargaPasar whp
            WHERE whp.deleteDate IS NULL
            """)
    List<WilayahHargaPasar> getAllWilayahHargaPasar(Pageable pageable);

    @Query("""
            SELECT whp
            FROM WilayahHargaPasar whp
            JOIN whp.kategoriWilayah kat
            WHERE whp.deleteDate IS NULL
            AND kat.namaKategori = :search
            """)
    List<WilayahHargaPasar> getWilayahHargaPasarByKategori(Pageable pageable, String search);

    @Query("""
            SELECT whp
            FROM WilayahHargaPasar whp
            WHERE whp.deleteDate IS NULL
            AND whp.id = :search
            """)
    List<WilayahHargaPasar> getWilayahHargaPasarById(Pageable pageable, String search);

    @Query("""
            SELECT whp
            FROM WilayahHargaPasar whp
            WHERE whp.deleteDate IS NULL
            AND whp.namaWilayah LIKE %:search%
            """)
    List<WilayahHargaPasar> getWilayahHargaPasarByName(Pageable pageable, String search);

    @Query("""
            SELECT whp
            FROM WilayahHargaPasar whp
            WHERE whp.deleteDate IS NULL
            AND whp.status = :search
            """)
    List<WilayahHargaPasar> getWilayahHargaPasarByStatus(Pageable pageable, Boolean search);

    //---------------------------------------------------------------------

    @Query("""
            SELECT DISTINCT whp.id
            FROM WilayahHargaPasar whp
            WHERE whp.deleteDate IS NULL
            """)
    List<String> getWilayahHargaPasarItemsById();

    @Query("""
            SELECT DISTINCT whp.namaWilayah
            FROM WilayahHargaPasar whp
            WHERE whp.deleteDate IS NULL
            """)
    List<String> getWilayahHargaPasarItemsByName();

    @Query(value = "SELECT DISTINCT CASE WHEN whp.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM WilayahHargaPasar whp", nativeQuery = true)
    List<String> getWilayahHargaPasarItemsByStatus();
}
