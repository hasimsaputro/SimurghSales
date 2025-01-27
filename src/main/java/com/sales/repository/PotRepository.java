package com.sales.repository;
import com.sales.entity.POT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PotRepository extends JpaRepository<POT, Integer> {
    @Query("""
            SELECT pot
            FROM POT pot
            """)
    List<POT> getItemsPot();

    @Query("""
            SELECT pot
            FROM POT pot
            WHERE pot.id = :idPOT
            """)
    POT getPotById(Integer idPOT);
}
