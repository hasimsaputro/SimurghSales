package com.sales.repository;

import com.sales.entity.Cabang;
import com.sales.entity.Produk;
import com.sales.entity.TipeMaster;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdukRepository extends JpaRepository<Produk, Integer> {
    @Query("""
            SELECT COUNT(1)
            FROM Produk pro
            WHERE pro.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
            SELECT COUNT(1)
            FROM Produk pro
            WHERE pro.id = %:search%
            AND pro.deleteDate IS NULL
            """)
    int getTotalPagesById(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Produk pro
            WHERE pro.deleteDate IS NULL
            AND pro.namaProduk LIKE %:search%
            """)
    int getTotalpagesByName(String search);

    @Query("""
            SELECT COUNT(1)
            FROM Produk pro
            WHERE pro.deleteDate IS NULL
            AND pro.status = :search
            """)
    int getTotalpagesByStatus(Boolean search);

    @Query("""
            SELECT pro
            FROM Produk pro
            WHERE pro.deleteDate IS NULL
            """)
    List<Produk> getAllProduk(Pageable pageable);

    @Query("""
            SELECT pro
            FROM Produk pro
            WHERE pro.deleteDate IS NULL
            AND pro.id = :search
            """)
    List<Produk> getProdukById(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT pro
            FROM Produk pro
            WHERE pro.deleteDate IS NULL
            AND pro.namaProduk = :search
            """)
    List<Produk> getProdukByName(Pageable pageable, @Param("search") String search);

    @Query("""
            SELECT pro
            FROM Produk pro
            WHERE pro.deleteDate IS NULL
            AND pro.status = :search
            """)
    List<Produk> getProdukByStatus(Pageable pageable, @Param("search") Boolean search);

    @Query("""
            SELECT pro.id
            FROM Produk pro
            WHERE pro.deleteDate IS NULL
            """)
    List<String> getProdukItemsById();

    @Query("""
            SELECT pro.namaProduk
            FROM Produk pro
            WHERE pro.deleteDate IS NULL
            """)
    List<String> getProdukItemsByName();

    @Query(value = "SELECT CASE WHEN pro.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM Produk pro", nativeQuery = true)
    List<String> getProdukItemsByStatus();


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

    @Query("""
            SELECT p
            FROM Produk p
            """)
    List<Produk> getAll();
}
