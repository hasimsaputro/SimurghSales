package com.sales.repository;

import com.sales.entity.Kategori;
import com.sales.entity.Model;
import com.sales.entity.Tipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, String> {
    @Query("""
            SELECT mod
            FROM Model mod
            WHERE mod.idKategori = :kategoriId
            """)
    List<Model> getAll(Integer kategoriId);

    @Query("""
            SELECT mod
            FROM Model mod
            WHERE mod.idKategori = :kategoriId AND mod.idMerk = :merkId AND mod.idTipe = :tipeId
            """)
    List<Model> getAllByMerkTipe(Integer kategoriId, String merkId, String tipeId);

    @Query("""
            SELECT mod
            FROM Model mod
            WHERE mod.idKategori = :kategoriId AND mod.idMerk = :merkId
            """)
    List<Model> getAllByMerk(Integer kategoriId, String merkId);

    @Query("""
            SELECT mod
            FROM Model mod
            """)
    List<Model> getAllModel(Pageable pagination);

    @Query("""
            SELECT mod
            FROM Model mod
            WHERE mod.id = :search
            """)
    List<Model> getByIdModel(String search, Pageable pagination);

    @Query("""
            SELECT DISTINCT mod
            FROM Model mod
            JOIN mod.kategoriModel km
            WHERE km.namaKategori = :search
            """)
    List<Model> getByKategoriName(String search, Pageable pagination);

    @Query("""
            SELECT mod
            FROM Model mod
            JOIN mod.merkModel mm
            WHERE mm.namaMerk = :search
            """)
    List<Model> getByMerkName(String search, Pageable pagination);

    @Query("""
            SELECT mod
            FROM Model mod
            JOIN mod.tipeModel tm
            WHERE tm.namaTipe = :search
            """)
    List<Model> getByTipeName(String search, Pageable pagination);

    @Query("""
            SELECT mod
            FROM Model mod
            JOIN mod.tipeModel tm
            JOIN tm.jenisTipe jt
            WHERE jt.namaJenis = :search
            """)
    List<Model> getByJenisName(String search, Pageable pagination);

    @Query("""
            SELECT mod
            FROM Model mod
            WHERE mod.namaModel = :search
            """)
    List<Model> getByModelName(String search, Pageable pagination);

    @Query("""
            SELECT DISTINCT mod
            FROM Model mod
            WHERE mod.status = :search
            """)
    List<Model> getByStatus(Boolean search, Pageable pagination);

    @Query("""
            SELECT COUNT(mod.id)
            FROM Model mod
            """)
    int countAll();

    @Query("""
            SELECT COUNT(mod.id)
            FROM Model mod
            WHERE mod.id = :search
            """)
    int countById(String search);

    @Query("""
            SELECT DISTINCT COUNT(mod.id)
            FROM Model mod
            JOIN mod.kategoriModel km
            WHERE km.namaKategori = :search
            """)
    int countByNamaKategori(String search);

    @Query("""
            SELECT COUNT(mod.id)
            FROM Model mod
            JOIN mod.merkModel mt
            WHERE mt.namaMerk = :search
            """)
    int countByNameMerk(String search);

    @Query("""
            SELECT COUNT(mod.id)
            FROM Model mod
            JOIN mod.tipeModel tm
            WHERE tm.namaTipe = :search
            """)
    int countByNamaTipe(String search);

    @Query("""
            SELECT COUNT(mod.id)
            FROM Model mod
            JOIN mod.tipeModel tm
            JOIN tm.jenisTipe jt
            WHERE jt.namaJenis = :search
            """)
    int countByNamaJenis(String search);

    @Query("""
            SELECT DISTINCT COUNT(mod.id)
            FROM Model mod
            WHERE mod.status = :search
            """)
    int countByStatus(String search);

    @Query("""
            SELECT COUNT(mod.id)
            FROM Model mod
            WHERE mod.namaModel = :search
            """)
    int countByNamaModel(String search);

    @Query("""
            SELECT mod.id
            FROM Model mod
            """)
    List<String> getItemsById();

    @Query("""
            SELECT DISTINCT km.namaKategori
            FROM Model mod
            JOIN mod.kategoriModel km
            """)
    List<String> getItemsByNamaKategori();

    @Query("""
            SELECT mm.namaMerk
            FROM Model mod
            JOIN mod.merkModel mm
            """)
    List<String> getItemsByNamaMerk();

    @Query("""
            SELECT tm.namaTipe
            FROM Model mod
            JOIN mod.tipeModel tm
            """)
    List<String> getItemsByNameTipe();

    @Query("""
            SELECT jt.namaJenis
            FROM Model mod
            JOIN mod.tipeModel tm
            JOIN tm.jenisTipe jt
            """)
    List<String> getItemsByNamaJenis();

    @Query("""
            SELECT mod.namaModel
            FROM Model mod
            """)
    List<String> getItemsByName();

    @Query(value = "SELECT DISTINCT CASE WHEN mod.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM Model mod", nativeQuery = true)
    List<String> getItemsByStatus();

    @Query("""
            SELECT mod
            FROM Model mod
            WHERE mod.id = :modelId
            """)
    Model getModelById(String modelId);
}
