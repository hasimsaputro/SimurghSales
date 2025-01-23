package com.sales.repository;

import com.sales.entity.Identitas;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdentitasRepository extends JpaRepository<Identitas, Integer> {
    @Query("""
            SELECT ide
            FROM Identitas ide
            """)
    List<Identitas> getAllIdentitas();

    @Query("""
            SELECT ide
            FROM Identitas ide
            WHERE ide.id = :id
            """)
    Identitas getIdentitasById(Integer id);
}
