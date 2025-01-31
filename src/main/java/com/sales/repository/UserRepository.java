package com.sales.repository;

import com.sales.entity.DataLeads;
import com.sales.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("""
            SELECT us
            FROM User us
            WHERE us.idCabang = :cabangId AND us.idJabatan = "JBT02" AND us.statusSurveyor = false
            """)
    List<User> getUserByCabangAndSurveyor(Integer cabangId);

    @Query("""
            SELECT us
            FROM User us
            WHERE us.namaKaryawan = :name
            """)
    User getUserByName(String name);
}
