package com.sales.repository;

import com.sales.entity.Provinsi;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinsiRepository extends JpaRepository<Provinsi, Integer> {
    @Query("""
            SELECT prov
            FROM Provinsi prov
            WHERE prov.deleteDate IS NULL
            AND prov.status = true
            """)
    List<Provinsi> getAllProvinsiOption();

    @Query("""
            SELECT prov
            FROM Provinsi prov
            WHERE prov.deleteDate IS NULL
            AND prov.namaProvinsi = :namaProvinsi
            """)
    Provinsi getProvinsiFormByName(String namaProvinsi);

    @Query("""
            SELECT prov.namaProvinsi
            FROM Provinsi prov
            WHERE prov.deleteDate IS NULL
            """)
    List<String> getProvinsiItems();

    //----------------------------------------------------------

    @Query("""
            SELECT COUNT(1)
            FROM Provinsi prov
            WHERE prov.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
            SELECT COUNT(1)
            FROM Provinsi prov
            WHERE prov.deleteDate IS NULL
            AND prov.id = :search
            """)
    int getTotalPagesById(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Provinsi prov
            WHERE prov.deleteDate IS NULL
            AND prov.namaProvinsi LIKE %:search%
            """)
    int getTotalPagesByName(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Provinsi prov
            WHERE prov.deleteDate IS NULL
            AND prov.status = :search
            """)
    int getTotalpagesByStatus(Boolean search);

    //----------------------------------------------------------

    @Query("""
            SELECT prov
            FROM Provinsi prov
            WHERE prov.deleteDate IS NULL
            """)
    List<Provinsi> getAllProvinsi(Pageable pageable);

    @Query("""
            SELECT prov
            FROM Provinsi prov
            WHERE prov.deleteDate IS NULL
            AND prov.id = :search
            """)
    List<Provinsi> getProvinsiById(Pageable pageable, String search);

    @Query("""
            SELECT prov
            FROM Provinsi prov
            WHERE prov.deleteDate IS NULL
            AND prov.namaProvinsi LIKE %:search%
            """)
    List<Provinsi> getProvinsiByName(Pageable pageable, String search);

    @Query("""
            SELECT prov
            FROM Provinsi prov
            WHERE prov.deleteDate IS NULL
            AND prov.status = :search
            """)
    List<Provinsi> getProvinsiByStatus(Pageable pageable, Boolean search);

    //---------------------------------------------------------------------

    @Query("""
            SELECT DISTINCT prov.id
            FROM Provinsi prov
            WHERE prov.deleteDate IS NULL
            """)
    List<String> getProvinsiItemsById();

    @Query("""
            SELECT DISTINCT prov.namaProvinsi
            FROM Provinsi prov
            WHERE prov.deleteDate IS NULL
            """)
    List<String> getProvinsiItemsByName();

    @Query(value = "SELECT DISTINCT CASE WHEN prov.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM Provinsi prov", nativeQuery = true)
    List<String> getProvinsiItemsByStatus();
}
