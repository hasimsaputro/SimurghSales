package com.sales.repository;

import com.sales.entity.DataLeads;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataLeadsRepository extends JpaRepository<DataLeads, String> {
    @Query("""
            SELECT dl
            FROM DataLeads dl
            WHERE dl.id = :idDataLeads
            """)
    DataLeads getDataLeadsById(String idDataLeads);


    @Query("""
            SELECT dl
            FROM DataLeads dl
            WHERE dl.id = :search
            """)
    List<DataLeads> getByIdDataLeads(String search, Pageable pagination);

    @Query("""
            SELECT dl
            FROM DataLeads dl
            WHERE dl.nomorAplikasi = :search
            """)
    List<DataLeads> getByNomorAplikasi(String search, Pageable pagination);

    @Query("""
            SELECT dl
            FROM DataLeads dl
            JOIN dl.debiturDataLeads db
            WHERE CONCAT(db.namaDepan,' ',db.namaTengah,' ',db.namaAkhir) LIKE %:search%
            """)
    List<DataLeads> getByDebitur(String search, Pageable pagination);

    @Query("""
            SELECT dl
            FROM DataLeads dl
            JOIN dl.tipeAplikasiDataLeads ta
            WHERE ta.namaTipeAplikasi = :search
            """)
    List<DataLeads> getByTipeAplikasi(String search, Pageable pagination);

    @Query("""
            SELECT dl
            FROM DataLeads dl
            JOIN dl.keteranganAplikasi ka
            WHERE ka.namaKeteranganAplikasi LIKE %:search%
            """)
    List<DataLeads> getByKeterangan(String search, Pageable pagination);

    @Query("""
            SELECT dl
            FROM DataLeads dl
            WHERE dl.status = :search
            """)
    List<DataLeads> getByStatus(String search, Pageable pagination);

    @Query("""
            SELECT dl
            FROM DataLeads dl
            """)
    List<DataLeads> getAll(Pageable pagination);
}
