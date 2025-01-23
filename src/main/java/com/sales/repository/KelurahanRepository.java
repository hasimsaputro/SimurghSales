package com.sales.repository;

import com.sales.entity.Kelurahan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface KelurahanRepository extends JpaRepository<Kelurahan, Integer> {

    @Query("""
            SELECT kl.namaKelurahan
            FROM Kelurahan kl
            """)
    List<String> getItemsKelurahan();
}
