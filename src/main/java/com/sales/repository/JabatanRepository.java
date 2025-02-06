package com.sales.repository;

import com.sales.entity.Jabatan;
import com.sales.entity.KeteranganAplikasi;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JabatanRepository extends JpaRepository<Jabatan, String> {

    @Query("""
        SELECT t
        FROM Jabatan t
        WHERE
            (:id IS NULL OR t.id LIKE %:id%)
            AND (:nama IS NULL OR t.namaJabatan LIKE %:nama%)
            AND (:status IS NULL OR t.status = :status)
        """)
    List<Jabatan> getAllBySearch(Pageable pagination, String id, String nama, Boolean status);

    @Query("""
        SELECT COUNT(*)
        FROM Jabatan t
        WHERE
            (:id IS NULL OR t.id LIKE %:id%)
            AND (:nama IS NULL OR t.namaJabatan LIKE %:nama%)
            AND (:status IS NULL OR t.status = :status)
        """)
    Integer countAllBySearch(String id, String nama, Boolean status);


    @Query("""
        SELECT t
        FROM Jabatan t
        WHERE t.id = :id
        """)
    Jabatan getKeteranganAplikasiById(String id);
}
