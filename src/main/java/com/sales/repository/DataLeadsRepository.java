package com.sales.repository;

import com.sales.entity.DataLeads;
import com.sales.entity.MitraAgen;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DataLeadsRepository extends JpaRepository<DataLeads, String> {
    @Query("""
            SELECT dl
            FROM DataLeads dl
            WHERE dl.id = :idDataLeads
            """)
    DataLeads getDataLeadsById(String idDataLeads);
}
