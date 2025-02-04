package com.sales.repository;

import com.sales.entity.Bank;
import com.sales.entity.Produk;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

    @Query("""
            SELECT COUNT(1)
            FROM Bank ba
            WHERE ba.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
            SELECT COUNT(1)
            FROM Bank ba
            WHERE ba.id = %:search%
            """)
    int getTotalPagesById(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Bank ba
            WHERE ba.deleteDate IS NULL
            AND ba.namaBank LIKE %:search%
            """)
    int getTotalpagesByName(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Bank ba
            WHERE ba.deleteDate IS NULL
            AND ba.status = :search
            """)
    int getTotalpagesByStatus(Boolean search);

    @Query("""
            SELECT ba
            FROM Bank ba
            WHERE ba.deleteDate IS NULL
            """)
    List<Bank> getAllBank(Pageable pageable);

    @Query("""
            SELECT ba
            FROM Bank ba
            WHERE ba.deleteDate IS NULL
            AND ba.id = :search
            """)
    List<Bank> getBankById(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT ba
            FROM Bank ba
            WHERE ba.deleteDate IS NULL
            AND ba.namaBank LIKE %:search%
            """)
    List<Bank> getBankByName(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT ba
            FROM Bank ba
            WHERE ba.deleteDate IS NULL
            AND ba.status = :search
            """)
    List<Bank> getBankByStatus(Pageable pageable, @Param("search") Boolean search);

    @Query("""
            SELECT ba.id
            FROM Bank ba
            WHERE ba.deleteDate IS NULL
            """)
    List<String> getBankItemsById();

    @Query("""
            SELECT ba.namaBank
            FROM Bank ba
            WHERE ba.deleteDate IS NULL
            """)
    List<String> getBankItemsByName();

    @Query(value = "SELECT CASE WHEN ba.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM Bank ba", nativeQuery = true)
    List<String> getBankItemsByStatus();

    @Query("""
            SELECT ba
            FROM Bank ba
            """)
    List<Bank> getAllBank();

    @Query("""
            SELECT ba
            FROM Bank ba
            WHERE ba.id = :id
            """)
    Produk getBankById(Integer id);

    @Query("""
            SELECT ba
            FROM Bank ba
            WHERE ba.namaBank = :namaBank
            """)
    Bank getBankByName(String namaBank);
}
