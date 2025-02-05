package com.sales.repository;
import com.sales.entity.DataLeads;
import com.sales.entity.POT;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PotRepository extends JpaRepository<POT, Integer> {
    @Query("""
            SELECT pot
            FROM POT pot
            """)
    List<POT> getItemsPot();

    @Query("""
            SELECT pot
            FROM POT pot
            WHERE pot.id = :idPOT
            """)
    POT getPotById(Integer idPOT);

    @Query("""
            SELECT pot
            FROM POT pot
            """)
    List<POT> getAll(Pageable pagination);

    @Query("""
            SELECT pot
            FROM POT pot
            WHERE pot.id = :search
            """)
    List<POT> getByIdPot(String search, Pageable pagination);

    @Query("""
            SELECT pot
            FROM POT pot
            WHERE pot.namaPOT = :search
            """)
    List<POT> getByName(String search, Pageable pagination);

    @Query("""
            SELECT pot
            FROM POT pot
            JOIN pot.produk pro
            WHERE pro.namaProduk = :search
            """)
    List<POT> getByProduk(String search, Pageable pagination);

    @Query("""
            SELECT pot
            FROM POT pot
            WHERE CAST(pot.tanggalMulai AS String) = :search
            """)
    List<POT> getByTanggalAwal(String search, Pageable pagination);

    @Query("""
            SELECT pot
            FROM POT pot
            WHERE CAST(pot.tanggalAkhir AS String) = :search
            """)
    List<POT> getByTanggalAkhir(String search, Pageable pagination);

    @Query("""
            SELECT pot
            FROM POT pot
            WHERE CAST(pot.pokokHutangAwal AS String) = :search
            """)
    List<POT> getByPokokAwal(String search, Pageable pagination);

    @Query("""
            SELECT pot
            FROM POT pot
            WHERE CAST(pot.pokokHutangAkhir AS String) = :search
            """)
    List<POT> getByPokokAkhir(String search, Pageable pagination);

    @Query("""
            SELECT pot
            FROM POT pot
            WHERE CAST(pot.tenor AS String) = :search
            """)
    List<POT> getByTenor(String search, Pageable pagination);

    @Query("""
            SELECT pot
            FROM POT pot
            WHERE CAST(pot.effectRate AS String) = :search
            """)
    List<POT> getByEffectRate(String search, Pageable pagination);

    @Query("""
            SELECT pot.id
            FROM POT pot
            """)
    List<String> getItemsId();

    @Query("""
            SELECT pot.namaPOT
            FROM POT pot
            """)
    List<String> getItemsNamaPot();

    @Query("""
            SELECT pro.namaProduk
            FROM POT pot
            JOIN pot.produk pro
            """)
    List<String> getItemsNamaProduk();

    @Query("""
            SELECT CAST(pot.tanggalMulai AS String)
            FROM POT pot
            """)
    List<String> getItemsTanggalAwal();

    @Query("""
            SELECT CAST(pot.tanggalAkhir AS String)
            FROM POT pot
            """)
    List<String> getItemsTanggalAkhir();

    @Query("""
            SELECT CAST(pot.pokokHutangAwal AS String)
            FROM POT pot
            """)
    List<String> getItemsPokokAwal();

    @Query("""
            SELECT CAST(pot.pokokHutangAkhir AS String)
            FROM POT pot
            """)
    List<String> getItemsPokokAkhir();

    @Query("""
            SELECT CAST(pot.tenor AS String)
            FROM POT pot
            """)
    List<String> getItemsTenor();

    @Query("""
            SELECT CAST(pot.effectRate AS String)
            FROM POT pot
            """)
    List<String> getItemsEffectRate();

    @Query("""
            SELECT COUNT(pot.id)
            FROM POT pot
            """)
    int countAll();

    @Query("""
            SELECT COUNT(pot.id)
            FROM POT pot
            WHERE pot.id = :search
            """)
    int countById(String search);

    @Query("""
            SELECT COUNT(pot.id)
            FROM POT pot
            WHERE pot.namaPOT = :search
            """)
    int countByNamaPot(String search);

    @Query("""
            SELECT COUNT(pot.id)
            FROM POT pot
            JOIN pot.produk pro
            WHERE pro.namaProduk = :search
            """)
    int countByNamaProduk(String search);

    @Query("""
            SELECT COUNT(pot.id)
            FROM POT pot
             WHERE CAST(pot.tanggalMulai AS String) = :search
            """)
    int countByTanggalAwal(String search);

    @Query("""
            SELECT COUNT(pot.id)
            FROM POT pot
             WHERE CAST(pot.tanggalAkhir AS String) = :search
            """)
    int countByTanggalAkhir(String search);

    @Query("""
            SELECT COUNT(pot.id)
            FROM POT pot
             WHERE CAST(pot.pokokHutangAwal AS String) = :search
            """)
    int countByPokokAwal(String search);

    @Query("""
            SELECT COUNT(pot.id)
            FROM POT pot
             WHERE CAST(pot.pokokHutangAkhir AS String) = :search
            """)
    int countByPokokAkhir(String search);

    @Query("""
            SELECT COUNT(pot.id)
            FROM POT pot
             WHERE CAST(pot.tenor AS String) = :search
            """)
    int countByTenor(String search);

    @Query("""
            SELECT COUNT(pot.id)
            FROM POT pot
             WHERE CAST(pot.effectRate AS String) = :search
            """)
    int countByEffectRate(String search);
}
