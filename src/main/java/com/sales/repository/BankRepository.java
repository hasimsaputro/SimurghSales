package com.sales.repository;

import com.sales.entity.Bank;
import com.sales.entity.Produk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {
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
    Produk getBankByName(String namaBank);
}
