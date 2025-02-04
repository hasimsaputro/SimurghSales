package com.sales.repository;

import com.sales.entity.Jenis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JenisRepository extends JpaRepository<Jenis, String > {
    @Query("""
            SELECT jen
            FROM Jenis jen
            """)
    List<Jenis> getAllJenis();
}
