package com.sales.repository;

import com.sales.entity.Cabang;
import com.sales.entity.HargaPasar;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HargaPasarRepository extends JpaRepository<HargaPasar, Integer> {
    @Query("""
            SELECT COUNT(1)
            FROM HargaPasar hp
            WHERE hp.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
            SELECT COUNT(1)
            FROM HargaPasar hp
            JOIN hp.wilayahHargaPasar w
            WHERE hp.deleteDate IS NULL
            AND w.namaWilayah = :search
            """)
    int getTotalPagesByWilayah(String search);

    @Query("""
            SELECT COUNT(1)
            FROM HargaPasar hp
            JOIN hp.merkHargaPasar mhp
            WHERE hp.deleteDate IS NULL
            AND mhp.namaMerk = :search
            """)
    int getTotalPagesByMerk(String search);

    @Query("""
            SELECT COUNT(1)
            FROM HargaPasar hp
            JOIN hp.tipeHargaPasar thp
            WHERE hp.deleteDate IS NULL
            AND thp.namaTipe = :search
            """)
    int getTotalPagesByTipe(String search);

    @Query("""
            SELECT COUNT(1)
            FROM HargaPasar hp
            JOIN hp.modelHargaPasar mod
            WHERE hp.deleteDate IS NULL
            AND mod.namaModel = :search
            """)
    int getTotalPagesByModel(String search);

    @Query("""
            SELECT COUNT(1)
            FROM HargaPasar hp
            JOIN hp.jenisHargaPasar jen
            WHERE hp.deleteDate IS NULL
            AND jen.namaJenis = :search
            """)
    int getTotalPagesByJenis(String search);

    @Query("""
            SELECT COUNT(1)
            FROM HargaPasar hp
            WHERE hp.deleteDate IS NULL
            AND hp.tipeUnit = :search
            """)
    int getTotalPagesByTipeUnit(String search);

    @Query("""
            SELECT COUNT(1)
            FROM HargaPasar hp
            WHERE hp.deleteDate IS NULL
            AND hp.tahun = :search
            """)
    int getTotalPagesByTahun(String search);

    @Query("""
            SELECT COUNT(1)
            FROM HargaPasar hp
            WHERE hp.deleteDate IS NULL
            AND hp.harga = :search
            """)
    int getTotalPagesByHarga(String search);

    @Query("""
            SELECT COUNT(1)
            FROM HargaPasar hp
            WHERE hp.deleteDate IS NULL
            AND hp.tanggalBerlaku = :search
            """)
    int getTotalPagesByTahunBerlaku(String search);

    @Query("""
            SELECT hp
            FROM HargaPasar hp
            WHERE hp.deleteDate IS NULL
            """)
    List<HargaPasar> getAllHargaPasar(Pageable pageable);

    @Query("""
            SELECT hp
            FROM HargaPasar hp
            JOIN hp.wilayahHargaPasar whp
            WHERE hp.deleteDate IS NULL
            AND whp.namaWilayah = :search
            """)
    List<HargaPasar> getHargaPasarByWilayah(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT hp
            FROM HargaPasar hp
            JOIN hp.merkHargaPasar mhp
            WHERE hp.deleteDate IS NULL
            AND mhp.namaMerk = :search
            """)
    List<HargaPasar> getHargaPasarByMerk(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT hp
            FROM HargaPasar hp
            JOIN hp.tipeHargaPasar thp
            WHERE hp.deleteDate IS NULL
            AND thp.namaTipe = :search
            """)
    List<HargaPasar> getHargaPasarByTipe(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT hp
            FROM HargaPasar hp
            JOIN hp.modelHargaPasar mod
            WHERE hp.deleteDate IS NULL
            AND mod.namaModel = :search
            """)
    List<HargaPasar> getHargaPasarByModel(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT hp
            FROM HargaPasar hp
            JOIN hp.jenisHargaPasar jen
            WHERE hp.deleteDate IS NULL
            AND jen.namaJenis = :search
            """)
    List<HargaPasar> getHargaPasarByJenis(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT hp
            FROM HargaPasar hp
            WHERE hp.deleteDate IS NULL
            AND hp.tipeUnit = :search
            """)
    List<HargaPasar> getHargaPasarByTipeUnit(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT hp
            FROM HargaPasar hp
            WHERE hp.deleteDate IS NULL
            AND hp.tahun = :search
            """)
    List<HargaPasar> getHargaPasarByTahun(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT hp
            FROM HargaPasar hp
            WHERE hp.deleteDate IS NULL
            AND hp.harga = :search
            """)
    List<HargaPasar> getHargaPasarByHarga(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT hp
            FROM HargaPasar hp
            WHERE hp.deleteDate IS NULL
            AND hp.tanggalBerlaku = :search
            """)
    List<HargaPasar> getHargaPasarByTahunBerlaku(Pageable pageable, @Param("search") String search);

}
