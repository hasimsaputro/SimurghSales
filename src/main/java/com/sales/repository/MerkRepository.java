package com.sales.repository;

import com.sales.entity.Merk;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerkRepository extends JpaRepository<Merk, String> {

    @Query("""
            SELECT mer
            FROM Merk mer
            """)
    List<Merk> getAll(Pageable pagination);

    @Query("""
            SELECT mer
            FROM Merk mer
            WHERE mer.id = :search
            """)
    List<Merk> getByIdMerk(String search, Pageable pagination);

    @Query("""
            SELECT DISTINCT mer
            FROM Merk mer
            JOIN mer.kategoriMerk km
            WHERE km.namaKategori = :search
            """)
    List<Merk> getByKategoriName(String search, Pageable pagination);

    @Query("""
            SELECT mer
            FROM Merk mer
            WHERE mer.namaMerk = :search
            """)
    List<Merk> getByName(String search, Pageable pagination);

    @Query("""
            SELECT mer
            FROM Merk mer
            JOIN mer.negara nm
            WHERE nm.namaNegara = :search
            """)
    List<Merk> getByNegara(String search, Pageable pagination);

    @Query("""
            SELECT DISTINCT mer
            FROM Merk mer
            WHERE mer.status = :search
            """)
    List<Merk> getByStatus(Boolean search, Pageable pagination);

    @Query("""
            SELECT COUNT(mer.id)
            FROM Merk mer
            """)
    int countAll();

    @Query("""
            SELECT COUNT(mer.id)
            FROM Merk mer
            WHERE mer.id = :search
            """)
    int countById(String search);

    @Query("""
            SELECT DISTINCT COUNT(mer.id)
            FROM Merk mer
            JOIN mer.kategoriMerk km
            WHERE km.namaKategori = :search
            """)
    int countByNamaKategori(String search);

    @Query("""
            SELECT COUNT(mer.id)
            FROM Merk mer
            WHERE mer.namaMerk = :search
            """)
    int countByNameMerk(String search);

    @Query("""
            SELECT COUNT(mer.id)
            FROM Merk mer
            JOIN mer.negara nm
            WHERE nm.namaNegara = :search
            """)
    int countByNamaNegara(String search);

    @Query("""
            SELECT DISTINCT COUNT(kat.id)
            FROM Kategori kat
            WHERE kat.status = :search
            """)
    int countByStatus(boolean search);

    @Query("""
            SELECT mer
            FROM Merk mer
            WHERE mer.id = :merkId
            """)
    Merk getMerkById(String merkId);

    @Query("""
            SELECT mer
            FROM Merk mer
            WHERE mer.idKategori = :idKategori
            """)
    List<Merk> getMerkByKategoriId(Integer idKategori);

    @Query("""
            SELECT mer.id
            FROM Merk mer
            """)
    List<String> getItemsById();

    @Query("""
            SELECT DISTINCT km.namaKategori
            FROM Merk mer
            JOIN mer.kategoriMerk km
            """)
    List<String> getItemsByNamaKategori();


    @Query("""
            SELECT mer.namaMerk
            FROM Merk mer
            """)
    List<String> getItemsByNamaMerk();

    @Query("""
            SELECT nm.namaNegara
            FROM Merk mer
            JOIN mer.negara nm
            """)
    List<String> getItemsByNegara();

    @Query(value = "SELECT DISTINCT CASE WHEN mer.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM Merk mer", nativeQuery = true)
    List<String> getItemsByStatus();

    @Query("""
            SELECT mer
            FROM Merk mer
            """)
    List<Merk> getAllMerk();
}
