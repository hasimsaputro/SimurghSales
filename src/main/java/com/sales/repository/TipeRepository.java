package com.sales.repository;

import com.sales.entity.Kategori;
import com.sales.entity.Tipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipeRepository extends JpaRepository<Tipe, String > {
    @Query("""
            SELECT tip
            FROM Tipe tip
            """)
    List<Tipe> getAll();
}
