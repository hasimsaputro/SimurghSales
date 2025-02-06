package com.sales.repository;

import com.sales.entity.DataLeads;
import com.sales.entity.KeteranganAplikasi;
import com.sales.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("""
            SELECT us
            FROM User us
            WHERE us.idCabang = :cabangId AND us.idJabatan = "SER" AND us.statusSurveyor = false
            """)
    List<User> getUserByCabangAndSurveyor(Integer cabangId);

    @Query("""
            SELECT us
            FROM User us
            WHERE us.namaKaryawan = :name
            """)
    User getUserByName(String name);

    @Query("""
        SELECT t
        FROM User t LEFT JOIN  t.jabatan j
        WHERE
            (:id IS NULL OR t.nik LIKE %:nik%)
            AND (:nama IS NULL OR t.namaKaryawan LIKE %:nama%)
            AND (:jabatan IS NULL OR j.namaJabatan LIKE %:jabatan%)
            AND (:email IS NULL OR t.email LIKE %:email%)
            AND (:status IS NULL OR t.status = :status)
        """)
    List<User> getAllBySearch(Pageable pagination, String  nik, String nama,String email,String jabatan , Boolean status);

    @Query("""
        SELECT COUNT(*)
        FROM User t LEFT JOIN  t.jabatan j
        WHERE
            (:id IS NULL OR t.nik LIKE %:nik%)
            AND (:nama IS NULL OR t.namaKaryawan LIKE %:nama%)
            AND (:jabatan IS NULL OR j.namaJabatan LIKE %:jabatan%)
            AND (:email IS NULL OR t.email LIKE %:email%)
            AND (:status IS NULL OR t.status = :status)
        """)
    Integer countAllBySearch(String nik, String nama,String email,String jabatan, Boolean status);


    @Query("""
        SELECT t
        FROM User t
        WHERE t.nik = :nik
        """)
    User getUserByNik(String  nik);
}
