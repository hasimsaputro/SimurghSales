package com.sales.repository;

import com.sales.entity.Identitas;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdentitasRepository extends JpaRepository<Identitas, Integer> {
    @Query("""
            SELECT ide
            FROM Identitas ide
            WHERE ide.deleteDate IS NULL
            AND ide.status = true
            """)
    List<Identitas> getAllIdentitas();

    @Query("""
            SELECT COUNT(1)
            FROM Identitas ide
            WHERE ide.deleteDate IS NULL
            """)
    int getTotalPages();

    @Query("""
        SELECT COUNT(1)
        FROM Identitas ide
        WHERE ide.id = %:search%
        AND ide.deleteDate IS NULL
        """)
    int getTotalPagesById(String search);

    @Query("""
        SELECT COUNT(1)
        FROM Identitas ide
        WHERE ide.deleteDate IS NULL
        AND ide.namaIdentitas LIKE %:search%
        """)
    int getTotalpagesByName(String search);

    @Query("""
        SELECT COUNT(1)
        FROM Identitas ide
        WHERE ide.deleteDate IS NULL
        AND ide.status = :search
        """)
    int getTotalpagesByStatus(Boolean search);

    @Query("""
        SELECT ide
        FROM Identitas ide
        WHERE ide.deleteDate IS NULL
        """)
    List<Identitas> getAllIdentitas(Pageable pageable);

    @Query("""
        SELECT ide
        FROM Identitas ide
        WHERE ide.deleteDate IS NULL
        AND ide.id = :search
        """)
    List<Identitas> getIdentitasById(Pageable pageable, @Param("search") String search);

    @Query("""
        SELECT ide
        FROM Identitas ide
        WHERE ide.deleteDate IS NULL
        AND ide.namaIdentitas = :search
        """)
    List<Identitas> getIdentitasByName(Pageable pageable, @Param("search") String search);

    @Query("""
        SELECT ide
        FROM Identitas ide
        WHERE ide.deleteDate IS NULL
        AND ide.status = :search
        """)
    List<Identitas> getIdentitasByStatus(Pageable pageable, @Param("search") Boolean search);

    @Query("""
        SELECT ide.id
        FROM Identitas ide
        WHERE ide.deleteDate IS NULL
        """)
    List<String> getIdentitasItemsById();

    @Query("""
        SELECT ide.namaIdentitas
        FROM Identitas ide
        WHERE ide.deleteDate IS NULL
        """)
    List<String> getIdentitasItemsByName();

    @Query(value = "SELECT CASE WHEN ide.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM Identitas ide", nativeQuery = true)
    List<String> getIdentitasItemsByStatus();


    @Query("""
            SELECT ide
            FROM Identitas ide
            WHERE ide.id = :id
            """)
    Identitas getIdentitasById(Integer id);

    @Query("""
            SELECT ide
            FROM Identitas ide
            WHERE ide.namaIdentitas = :namaIdentitas
            """)
    Identitas getIdentitasByName(String namaIdentitas);
}
