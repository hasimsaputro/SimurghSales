package com.sales.repository;

import com.sales.entity.TipeUnit;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TipeUnitRepository extends JpaRepository<TipeUnit, String> {

    @Query("""
        SELECT t
        FROM TipeUnit t
        WHERE
            (:id IS NULL OR CAST(t.id AS string) LIKE %:id%)
            AND (:nama IS NULL OR t.namaUnit LIKE %:nama%)
            AND (:status IS NULL OR t.status = :status)
            AND t.deleteDate IS NULL
        """)
    List<TipeUnit> getAllBySearch(Pageable pagination, String id, String nama, Boolean status);

    @Query("""
        SELECT COUNT(t)
        FROM TipeUnit t
        WHERE
            (:id IS NULL OR CAST(t.id AS string) LIKE %:id%)
            AND (:nama IS NULL OR t.namaUnit LIKE %:nama%)
            AND (:status IS NULL OR t.status = :status)
            AND t.deleteDate IS NULL
        """)
    Integer countAllBySearch(String id, String nama, Boolean status);


    @Query("""
        SELECT t
        FROM TipeUnit t
        WHERE t.id = :id
        """)
   TipeUnit getTipeUnitById(String id);
}
