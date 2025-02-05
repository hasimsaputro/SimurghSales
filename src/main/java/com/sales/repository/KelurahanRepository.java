package com.sales.repository;

import com.sales.entity.Debitur;
import com.sales.entity.Kelurahan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KelurahanRepository extends JpaRepository<Kelurahan, Integer> {
    @Query("""
            SELECT kel
            FROM Kelurahan kel
            WHERE kel.deleteDate IS NULL
            AND kel.status = true
            """)
    List<Kelurahan> getAllKelurahanOption();

    @Query("""
            SELECT kel
            FROM Kelurahan kel
            WHERE kel.deleteDate IS NULL
            AND kel.namaKelurahan = :namaKelurahan
            """)
    Kelurahan getKelurahanFormByName(String namaKelurahan);

    @Query("""
            SELECT kel.namaKelurahan
            FROM Kelurahan kel
            WHERE kel.deleteDate IS NULL
            """)
    List<String> getKelurahanItems();

    //-----------------------------------------------------------------------

    @Query("""
            SELECT COUNT(1)
            FROM Kelurahan kel
            WHERE kel.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
            SELECT COUNT(1)
            FROM Kelurahan kel
            JOIN kel.provinsi prov
            WHERE kel.deleteDate IS NULL
            AND prov.namaProvinsi = :search
            """)
    int getTotalPagesByProvinsi(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Kelurahan kel
            JOIN kel.kabupaten kab
            WHERE kel.deleteDate IS NULL
            AND kab.namaKabupatenKota = :search
            """)
    int getTotalPagesByKabupaten(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Kelurahan kel
            JOIN kel.kecamatan kec
            WHERE kel.deleteDate IS NULL
            AND kec.namaKecamatan = :search
            """)
    int getTotalPagesByKecamatan(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Kelurahan kel
            WHERE kel.deleteDate IS NULL
            AND kel.id = :search
            """)
    int getTotalPagesById(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Kelurahan kel
            WHERE kel.deleteDate IS NULL
            AND kel.namaKelurahan LIKE %:search%
            """)
    int getTotalPagesByName(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Kelurahan kel
            WHERE kel.deleteDate IS NULL
            AND kel.status = :search
            """)
    int getTotalpagesByStatus(Boolean search);

    //-----------------------------------------------------------------------

    @Query("""
            SELECT kel
            FROM Kelurahan kel
            WHERE kel.deleteDate IS NULL
            """)
    List<Kelurahan> getAllKelurahan(Pageable pageable);

    @Query("""
            SELECT kel
            FROM Kelurahan kel
            JOIN kel.provinsi prov
            WHERE kel.deleteDate IS NULL
            AND prov.namaProvinsi = :search
            """)
    List<Kelurahan> getKelurahanByProvinsi(Pageable pageable, String search);

    @Query("""
            SELECT kel
            FROM Kelurahan kel
            JOIN kel.kabupaten kab
            WHERE kel.deleteDate IS NULL
            AND kab.namaKabupatenKota = :search
            """)
    List<Kelurahan> getKelurahanByKabupaten(Pageable pageable, String search);

    @Query("""
            SELECT kel
            FROM Kelurahan kel
            JOIN kel.kecamatan kec
            WHERE kel.deleteDate IS NULL
            AND kec.namaKecamatan = :search
            """)
    List<Kelurahan> getKelurahanByKecamatan(Pageable pageable, String search);

    @Query("""
            SELECT kel
            FROM Kelurahan kel
            WHERE kel.deleteDate IS NULL
            AND kel.id = :search
            """)
    List<Kelurahan> getKelurahanById(Pageable pageable, String search);

    @Query("""
            SELECT kel
            FROM Kelurahan kel
            WHERE kel.deleteDate IS NULL
            AND kel.namaKelurahan LIKE %:search%
            """)
    List<Kelurahan> getKelurahanByName(Pageable pageable, String search);

    @Query("""
            SELECT kel
            FROM Kelurahan kel
            WHERE kel.deleteDate IS NULL
            AND kel.status = :search
            """)
    List<Kelurahan> getKelurahanByStatus(Pageable pageable, Boolean search);

    //---------------------------------------------------------------------

    @Query("""
            SELECT DISTINCT kel.id
            FROM Kelurahan kel
            WHERE kel.deleteDate IS NULL
            """)
    List<String> getKelurahanItemsById();

    @Query("""
            SELECT DISTINCT kel.namaKelurahan
            FROM Kelurahan kel
            WHERE kel.deleteDate IS NULL
            """)
    List<String> getKelurahanItemsByName();

    @Query(value = "SELECT DISTINCT CASE WHEN kel.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM Kelurahan kel", nativeQuery = true)
    List<String> getKelurahanItemsByStatus();


    @Query("""
            SELECT kel
            FROM Kelurahan kel
            WHERE kel.namaKelurahan = :namaKelurahan
            """)
    Kelurahan getKelurahanByName(String namaKelurahan);

    @Query("""
            SELECT kl.namaKelurahan
            FROM Kelurahan kl
            """)
    List<String> getItemsKelurahan();

    @Query("""
            SELECT kel
            FROM Kelurahan kel
            WHERE kel.namaKelurahan = :namaKel
            """)
    Kelurahan getKelurahanByNama(String namaKel);
}
