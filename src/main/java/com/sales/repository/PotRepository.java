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
            WHERE CAST(pot.tanggalMulai AS string) = :search
            """)
    List<POT> getByTanggalAwal(String search, Pageable pagination);

    @Query("""
            SELECT pot
            FROM POT pot
            WHERE CAST(pot.tanggalAkhir AS string) = :search
            """)
    List<POT> getByTanggalAkhir(String search, Pageable pagination);
}
