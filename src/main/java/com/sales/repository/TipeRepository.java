package com.sales.repository;

import com.sales.entity.Tipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipeRepository extends JpaRepository<Tipe, String > {
    @Query("""
            SELECT tip
            FROM Tipe tip
            WHERE tip.idKategori = :kategoriId
            """)
    List<Tipe> getAllTipeByKategoriId(Integer kategoriId);

    @Query("""
            SELECT tip
            FROM Tipe tip
            WHERE tip.idKategori = :kategoriId AND tip.idMerk = :merkId
            """)
    List<Tipe> getAllByMerk(Integer kategoriId, String merkId);

    @Query("""
            SELECT tip
            FROM Tipe tip
            """)
    List<Tipe> getAllTipe(Pageable pagination);

    @Query("""
            SELECT tip
            FROM Tipe tip
            WHERE tip.id = :search
            """)
    List<Tipe> getByIdTipe(String search, Pageable pagination);

    @Query("""
            SELECT DISTINCT tip
            FROM Tipe tip
            JOIN tip.kategoriTipe kt
            WHERE kt.namaKategori = :search
            """)
    List<Tipe> getByKategoriName(String search, Pageable pagination);

    @Query("""
            SELECT tip
            FROM Tipe tip
            JOIN tip.merkTipe mt
            WHERE mt.namaMerk = :search
            """)
    List<Tipe> getByMerkName(String search, Pageable pagination);

    @Query("""
            SELECT tip
            FROM Tipe tip
            WHERE tip.namaTipe = :search
            """)
    List<Tipe> getByName(String search, Pageable pagination);

    @Query("""
            SELECT tip
            FROM Tipe tip
            JOIN tip.jenisTipe jt
            WHERE jt.namaJenis = :search
            """)
    List<Tipe> getByJenisName(String search, Pageable pagination);

    @Query("""
            SELECT DISTINCT tip
            FROM Tipe tip
            WHERE tip.status = :search
            """)
    List<Tipe> getByStatus(Boolean search, Pageable pagination);

    @Query("""
            SELECT COUNT(tip.id)
            FROM Tipe tip
            """)
    int countAll();

    @Query("""
            SELECT COUNT(tip.id)
            FROM Tipe tip
            WHERE tip.id = :search
            """)
    int countById(String search);

    @Query("""
            SELECT DISTINCT COUNT(tip.id)
            FROM Tipe tip
            JOIN tip.kategoriTipe kt
            WHERE kt.namaKategori = :search
            """)
    int countByNamaKategori(String search);

    @Query("""
            SELECT COUNT(tip.id)
            FROM Tipe tip
            JOIN tip.merkTipe mt
            WHERE mt.namaMerk = :search
            """)
    int countByNameMerk(String search);

    @Query("""
            SELECT COUNT(tip.id)
            FROM Tipe tip
            WHERE tip.namaTipe = :search
            """)
    int countByNamaTipe(String search);

    @Query("""
            SELECT COUNT(tip.id)
            FROM Tipe tip
            JOIN tip.jenisTipe jt
            WHERE jt.namaJenis = :search
            """)
    int countByNamaJenis(String search);

    @Query("""
            SELECT DISTINCT COUNT(tip.id)
            FROM Tipe tip
            WHERE tip.status = :search
            """)
    int countByStatus(boolean search);

    @Query("""
            SELECT tip
            FROM Tipe tip
            WHERE tip.id = :tipeId
            """)
    Tipe getTipeById(String tipeId);

    @Query("""
            SELECT tip.id
            FROM Tipe tip
            """)
    List<String> getItemsById();

    @Query("""
            SELECT DISTINCT kt.namaKategori
            FROM Tipe tip
            JOIN tip.kategoriTipe kt
            """)
    List<String> getItemsByNamaKategori();

    @Query("""
            SELECT mt.namaMerk
            FROM Tipe tip
            JOIN tip.merkTipe mt
            """)
    List<String> getItemsByNamaMerk();

    @Query("""
            SELECT tip.namaTipe
            FROM Tipe tip
            """)
    List<String> getItemsByName();

    @Query("""
            SELECT jt.namaJenis
            FROM Tipe tip
            JOIN tip.jenisTipe jt
            """)
    List<String> getItemsByNamaJenis();

    @Query(value = "SELECT DISTINCT CASE WHEN tip.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM Tipe tip", nativeQuery = true)
    List<String> getItemsByStatus();

    @Query("""
            SELECT tip
            FROM Tipe tip
            """)
    List<Tipe> getAllTipe();
}
