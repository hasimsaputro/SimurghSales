package com.sales.repository;

import com.sales.entity.Kategori;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KategoriRepository extends JpaRepository<Kategori, Integer> {
    @Query("""
            SELECT kat
            FROM Kategori kat
            """)
    List<Kategori> getAll(Pageable pagination);

    @Query("""
            SELECT kat
            FROM Kategori kat
            WHERE kat.id = :search
            """)
    List<Kategori> getByIdKategori(String search, Pageable pagination);

    @Query("""
            SELECT kat
            FROM Kategori kat
            WHERE kat.namaKategori = :search
            """)
    List<Kategori> getByName(String search, Pageable pagination);

    @Query("""
            SELECT kat
            FROM Kategori kat
            WHERE kat.status = :search
            """)
    List<Kategori> getByStatus(Boolean search, Pageable pagination);
    
    @Query("""
            SELECT COUNT(kat.id)
            FROM Kategori kat
            """)
    int countAll();
    
    @Query("""
            SELECT COUNT(kat.id)
            FROM Kategori kat
            WHERE kat.id = :search
            """)
    int countById(String search);

    @Query("""
            SELECT COUNT(kat.id)
            FROM Kategori kat
            WHERE kat.namaKategori = :search
            """)
    int countByNamaKategori(String search);

    @Query("""
            SELECT COUNT(kat.id)
            FROM Kategori kat
            WHERE kat.status = :search
            """)
    int countByStatus(boolean search);

    @Query("""
            SELECT kat
            FROM Kategori kat
            WHERE kat.id = :kategoriId
            """)
    Kategori getKategoriById(Integer kategoriId);

    @Query("""
            SELECT kat
            FROM Kategori kat
            """)
    List<Kategori> getAllKategori();

    @Query("""
            SELECT kat.id
            FROM Kategori kat
            """)
    List<String> getItemsId();

    @Query("""
            SELECT kat.namaKategori
            FROM Kategori kat
            """)
    List<String> getItemsNamaKategori();

    @Query(value = "SELECT DISTINCT CASE WHEN kat.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM Kategori kat", nativeQuery = true)
    List<String> getItemsStatus();
}
