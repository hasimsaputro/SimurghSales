package com.sales.repository;

import com.sales.entity.MitraAgen;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MitraAgenRepository extends JpaRepository<MitraAgen, String> {
    @Query("""
            SELECT COUNT(1)
            FROM MitraAgen mit
            WHERE (mit.id = :id OR :id IS NULL)
            AND (mit.idTipeMaster = :tipe OR :tipe IS NULL)   
            AND (mit.namaMitraAgen = :name OR :name IS NULL)
            AND (mit.idKelurahanDomisili = :kelurahan OR :kelurahan IS NULL)
            AND (mit.idCabang = :cabang OR :cabang IS NULL)
            AND (mit.status = :status OR :status IS NULL)
            """)
    int getTotalPages(String id, Integer tipe, String name, Integer kelurahan,
                      Integer cabang, Boolean status);

    @Query("""
            SELECT mit
            FROM MitraAgen mit
            WHERE (mit.id = :id OR :id IS NULL)
            AND (mit.idTipeMaster = :tipe OR :tipe IS NULL)   
            AND (mit.namaMitraAgen = :name OR :name IS NULL)
            AND (mit.idKelurahanDomisili = :kelurahan OR :kelurahan IS NULL)
            AND (mit.idCabang = :cabang OR :cabang IS NULL)
            AND (mit.status = :status OR :status IS NULL)
            """)
    List<MitraAgen> getAllMitraAgen(Pageable pageable, String id, Integer tipe, String name, Integer kelurahan,
                                    Integer cabang, Boolean status);
//
//    @Query("""
//            SELECT mit
//            FROM MitraAgen mit
//            WHERE mit.Id = :id
//            """)
//    MitraAgen getMitraAgenById(String id);
//
//    @Query("""
//            SELECT mit
//            FROM MitraAgen mit
//            WHERE mit.IdTipeMaster = :tipe
//            """)
//    List<MitraAgen> getMitraAgenByTipe(Integer tipe);
//
//    @Query("""
//            SELECT mit
//            FROM MitraAgen mit
//            WHERE mit.NamaMitraAgen = :name
//            """)
//    List<MitraAgen> getMitraAgenByName(String name);
//
//    @Query("""
//            SELECT mit
//            FROM MitraAgen mit
//            WHERE mit.IdKelurahan = :kelurahan
//            """)
//    List<MitraAgen> getMitraAgenByKelurahan(Integer kelurahan);
//
//    @Query("""
//            SELECT mit
//            FROM MitraAgen mit
//            WHERE mit.IdCabang = :cabang
//            """)
//    List<MitraAgen> getMitraAgenByCabang(Integer cabang);
//
//    @Query("""
//            SELECT mit
//            FROM MitraAgen mit
//            WHERE mit.Status = :status
//            """)
//    List<MitraAgen> getMitraAgenByStatus(Boolean status);

}
