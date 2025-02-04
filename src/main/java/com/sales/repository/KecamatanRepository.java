package com.sales.repository;

import com.sales.entity.Kecamatan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KecamatanRepository extends JpaRepository<Kecamatan, Integer> {
    @Query("""
            SELECT kec
            FROM Kecamatan kec
            WHERE kec.deleteDate IS NULL
            AND kec.idKabupaten = :id
            """)
    List<Kecamatan> getAllKecamatanOption(Integer id);

    @Query("""
            SELECT kec
            FROM Kecamatan kec
            WHERE kec.deleteDate IS NULL
            AND kec.namaKecamatan = :namaKecamatan
            """)
    Kecamatan getKecamatanFormByName(String namaKecamatan);

    @Query("""
            SELECT kec.namaKecamatan
            FROM Kecamatan kec
            WHERE kec.deleteDate IS NULL
            """)
    List<String> getKecamatanItems();

    //-----------------------------------------------------------

    @Query("""
            SELECT COUNT(1)
            FROM Kecamatan kec
            WHERE kec.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
            SELECT COUNT(1)
            FROM Kecamatan kec
            JOIN kec.provinsi prov
            WHERE kec.deleteDate IS NULL
            AND prov.namaProvinsi = :search
            """)
    int getTotalPagesByProvinsi(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Kecamatan kec
            JOIN kec.kabupaten kab
            WHERE kec.deleteDate IS NULL
            AND kab.namaKabupatenKota = :search
            """)
    int getTotalPagesByKabupaten(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Kecamatan kec
            WHERE kec.deleteDate IS NULL
            AND kec.id = :search
            """)
    int getTotalPagesById(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Kecamatan kec
            WHERE kec.deleteDate IS NULL
            AND kec.namaKecamatan LIKE %:search%
            """)
    int getTotalPagesByName(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Kecamatan kec
            WHERE kec.deleteDate IS NULL
            AND kec.status = :search
            """)
    int getTotalpagesByStatus(Boolean search);

    //-----------------------------------------------------------

    @Query("""
            SELECT kec
            FROM Kecamatan kec
            WHERE kec.deleteDate IS NULL
            """)
    List<Kecamatan> getAllKecamatan(Pageable pageable);

    @Query("""
            SELECT kec
            FROM Kecamatan kec
            JOIN kec.provinsi prov
            WHERE kec.deleteDate IS NULL
            AND prov.namaProvinsi = :search
            """)
    List<Kecamatan> getKecamatanByProvinsi(Pageable pageable, String search);

    @Query("""
            SELECT kec
            FROM Kecamatan kec
            JOIN kec.kabupaten kab
            WHERE kec.deleteDate IS NULL
            AND kab.namaKabupatenKota = :search
            """)
    List<Kecamatan> getKecamatanByKabupaten(Pageable pageable, String search);

    @Query("""
            SELECT kec
            FROM Kecamatan kec
            WHERE kec.deleteDate IS NULL
            AND kec.id = :search
            """)
    List<Kecamatan> getKecamatanById(Pageable pageable, String search);

    @Query("""
            SELECT kec
            FROM Kecamatan kec
            WHERE kec.deleteDate IS NULL
            AND kec.namaKecamatan LIKE %:search%
            """)
    List<Kecamatan> getKecamatanByName(Pageable pageable, String search);

    @Query("""
            SELECT kec
            FROM Kecamatan kec
            WHERE kec.deleteDate IS NULL
            AND kec.status = :search
            """)
    List<Kecamatan> getKecamatanByStatus(Pageable pageable, Boolean search);

    //-----------------------------------------------------------

    @Query("""
            SELECT DISTINCT kec.id
            FROM Kecamatan kec
            WHERE kec.deleteDate IS NULL
            """)
    List<String> getKecamatanItemsById();

    @Query("""
            SELECT DISTINCT kec.namaKecamatan
            FROM Kecamatan kec
            WHERE kec.deleteDate IS NULL
            """)
    List<String> getKecamatanItemsByName();

    @Query(value = "SELECT DISTINCT CASE WHEN kec.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM Kecamatan kec", nativeQuery = true)
    List<String> getKecamatanItemsByStatus();
}
