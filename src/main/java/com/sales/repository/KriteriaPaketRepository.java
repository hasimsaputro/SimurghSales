package com.sales.repository;

import com.sales.entity.KriteriaPaket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KriteriaPaketRepository extends JpaRepository<KriteriaPaket, String> {
    @Query("""
            SELECT kp
            FROM KriteriaPaket kp
            """)
    List<KriteriaPaket> getAllKriteria();

}
