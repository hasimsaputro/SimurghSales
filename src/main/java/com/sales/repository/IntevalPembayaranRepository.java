package com.sales.repository;

import com.sales.entity.IntervalPembayaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntevalPembayaranRepository extends JpaRepository<IntervalPembayaran, Integer> {
    @Query("""
            SELECT ip
            FROM IntervalPembayaran ip
            """)
    List<IntervalPembayaran> getAllInterval();

}
