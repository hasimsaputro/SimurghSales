package com.sales.repository;

import com.sales.entity.Produk;
import com.sales.entity.TipeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdukRepository extends JpaRepository<Produk, Integer> {
    @Query("""
            SELECT pro
            FROM Produk pro
            """)
    List<Produk> getAllProduk();

    @Query("""
            SELECT pro
            FROM Produk pro
            WHERE pro.id = :id
            """)
    Produk getProdukById(Integer id);

    @Query("""
            SELECT pro
            FROM Produk pro
            WHERE pro.namaProduk = :namaProduk
            """)
    Produk getProdukByName(String namaProduk);
}
