package com.sales.repository;

import com.sales.entity.KeteranganAplikasi;
import com.sales.entity.Produk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdukRepository extends JpaRepository<Produk, Integer> {
    @Query("""
            SELECT p
            FROM Produk p
            WHERE p.namaProduk = :namaProduk
            """)
    Produk getProdukByName(String namaProduk);

    @Query("""
            SELECT p
            FROM Produk p
            """)
    List<Produk> getAll();
}
