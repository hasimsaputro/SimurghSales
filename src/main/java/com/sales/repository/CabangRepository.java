package com.sales.repository;

import com.sales.entity.Cabang;
import com.sales.entity.MitraAgen;
import com.sales.entity.Produk;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabangRepository extends JpaRepository<Cabang, Integer> {
    @Query("""
            SELECT COUNT(1)
            FROM Cabang cab
            WHERE cab.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
            SELECT COUNT(1)
            FROM Cabang cab
            WHERE cab.id = %:search%
            """)
    int getTotalPagesById(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Cabang cab
            WHERE cab.deleteDate IS NULL
            AND cab.namaCabang LIKE %:search%
            """)
    int getTotalpagesByName(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Cabang cab
            JOIN cab.tipeStruktur ts
            WHERE cab.deleteDate IS NULL
            AND ts.namaStruktur = :search
            """)
    int getTotalpagesByTipeStruktur(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Cabang cab
            WHERE cab.deleteDate IS NULL
            AND cab.alamat = :search
            """)
    int getTotalpagesByAlamat(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Cabang cab
            WHERE cab.deleteDate IS NULL
            AND cab.status = :search
            """)
    int getTotalpagesByStatus(Boolean search);

    @Query("""
            SELECT cab
            FROM Cabang cab
            WHERE cab.deleteDate IS NULL
            """)
    List<Cabang> getAllCabang(Pageable pageable);

    @Query("""
            SELECT cab
            FROM Cabang cab
            WHERE cab.deleteDate IS NULL
            AND cab.id = :search
            """)
    List<Cabang> getCabangById(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT cab
            FROM Cabang cab
            WHERE cab.deleteDate IS NULL
            AND cab.namaCabang = :search
            """)
    List<Cabang> getCabangByName(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT cab
            FROM Cabang cab
            JOIN cab.tipeStruktur ts
            WHERE cab.deleteDate IS NULL
            AND ts.namaStruktur = :search
            """)
    List<Cabang> getCabangByTipeStruktur(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT cab
            FROM Cabang cab
            WHERE cab.deleteDate IS NULL
            AND cab.alamat LIKE CONCAT('%', :search, '%')
            """)
    List<Cabang> getCabangByAlamat(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT cab
            FROM Cabang cab
            WHERE cab.deleteDate IS NULL
            AND cab.status = :search
            """)
    List<Cabang> getCabangByStatus(Pageable pageable, @Param("search") Boolean search);

    @Query("""
            SELECT cab
            FROM Cabang cab
            WHERE cab.namaCabang = :namaCabang
            """)
    Cabang getCabangByName(String namaCabang);

    @Query("""
            SELECT cab.id
            FROM Cabang cab
            WHERE cab.deleteDate IS NULL
            """)
    List<String> getCabangItemsById();

    @Query("""
            SELECT cab.namaCabang
            FROM Cabang cab
            WHERE cab.deleteDate IS NULL
            """)
    List<String> getCabangItemsByName();

    @Query("""
            SELECT ts.namaStruktur
            FROM Cabang cab
            JOIN cab.tipeStruktur ts
            WHERE cab.deleteDate IS NULL
            """)
    List<String> getCabangItemsByTipeStruktur();

    @Query("""
            SELECT cab.alamat
            FROM Cabang cab
            WHERE cab.deleteDate IS NULL
            """)
    List<String> getCabangItemsByAlamat();

    @Query(value = "SELECT CASE WHEN cab.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM Cabang cab", nativeQuery = true)
    List<String> getCabangItemsByStatus();

}
