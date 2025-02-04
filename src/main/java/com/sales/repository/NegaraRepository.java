package com.sales.repository;

import com.sales.entity.Kategori;
import com.sales.entity.Negara;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NegaraRepository extends JpaRepository<Negara, Integer> {
    @Query("""
            SELECT neg
            FROM Negara neg
            """)
    List<Negara> getAllNegara();
}
