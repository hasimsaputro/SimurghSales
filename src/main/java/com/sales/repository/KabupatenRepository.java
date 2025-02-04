package com.sales.repository;

import com.sales.entity.Kabupaten;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KabupatenRepository extends JpaRepository<Kabupaten, Integer> {
    @Query("""
            SELECT kab
            FROM Kabupaten kab
            WHERE kab.deleteDate IS NULL
            """)
    List<Kabupaten> getAllKabupatenOption();

    @Query("""
            SELECT kab
            FROM Kabupaten kab
            WHERE kab.deleteDate IS NULL
            AND kab.namaKabupatenKota = :namaKabupaten
            """)
    Kabupaten getKabupatenFormByName(String namaKabupaten);

    @Query("""
            SELECT kab.namaKabupatenKota
            FROM Kabupaten kab
            WHERE kab.deleteDate IS NULL
            """)
    List<String> getkabupatenItems();

    //-----------------------------------------------------------

    @Query("""
            SELECT COUNT(1)
            FROM Kabupaten kab
            WHERE kab.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
            SELECT COUNT(1)
            FROM Kabupaten kab
            JOIN kab.provinsi prov
            WHERE kab.deleteDate IS NULL
            AND prov.namaProvinsi = :search
            """)
    int getTotalPagesByProvinsi(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Kabupaten kab
            WHERE kab.deleteDate IS NULL
            AND kab.id = :search
            """)
    int getTotalPagesById(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Kabupaten kab
            WHERE kab.deleteDate IS NULL
            AND kab.namaKabupatenKota LIKE %:search%
            """)
    int getTotalPagesByName(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Kabupaten kab
            WHERE kab.deleteDate IS NULL
            AND kab.status = :search
            """)
    int getTotalpagesByStatus(Boolean search);

    //-----------------------------------------------------------

    @Query("""
            SELECT kab
            FROM Kabupaten kab
            WHERE kab.deleteDate IS NULL
            """)
    List<Kabupaten> getAllKabupaten(Pageable pageable);

    @Query("""
            SELECT kab
            FROM Kabupaten kab
            JOIN kab.provinsi prov
            WHERE kab.deleteDate IS NULL
            AND prov.namaProvinsi = :search
            """)
    List<Kabupaten> getKabupatenByProvinsi(Pageable pageable, String search);

    @Query("""
            SELECT kab
            FROM Kabupaten kab
            WHERE kab.deleteDate IS NULL
            AND kab.id = :search
            """)
    List<Kabupaten> getKabupatenById(Pageable pageable, String search);

    @Query("""
            SELECT kab
            FROM Kabupaten kab
            WHERE kab.deleteDate IS NULL
            AND kab.namaKabupatenKota LIKE %:search%
            """)
    List<Kabupaten> getKabupatenByName(Pageable pageable, String search);

    @Query("""
            SELECT kab
            FROM Kabupaten kab
            WHERE kab.deleteDate IS NULL
            AND kab.status = :search
            """)
    List<Kabupaten> getKabupatenByStatus(Pageable pageable, Boolean search);

    //-----------------------------------------------------------

    @Query("""
            SELECT DISTINCT kab.id
            FROM Kabupaten kab
            WHERE kab.deleteDate IS NULL
            """)
    List<String> getKabupatenItemsById();

    @Query("""
            SELECT DISTINCT kab.namaKabupatenKota
            FROM Kabupaten kab
            WHERE kab.deleteDate IS NULL
            """)
    List<String> getKabupatenItemsByName();

    @Query(value = "SELECT DISTINCT CASE WHEN kab.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM Kabupaten kab", nativeQuery = true)
    List<String> getKabupatenItemsByStatus();
}
