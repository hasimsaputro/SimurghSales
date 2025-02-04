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
            SELECT DISTINCT dl
            FROM DataLeads dl
            WHERE dl.status = :search
            """)
    List<DataLeads> getByStatus(Boolean search, Pageable pagination);

    @Query("""
            SELECT dl
            FROM DataLeads dl
            """)
    List<DataLeads> getAll(Pageable pagination);

    @Query("""
            SELECT COUNT(dl.id)
            FROM DataLeads dl
            """)
    int countAll();

    @Query("""
            SELECT COUNT(dl.id)
            FROM DataLeads dl
            WHERE dl.id = :search
            """)
    int countById(String search);

    @Query("""
            SELECT COUNT(dl.id)
            FROM DataLeads dl
            WHERE dl.nomorAplikasi = :search
            """)
    int countByNomorAplikasi(String search);

    @Query("""
            SELECT COUNT(dl.id)
            FROM DataLeads dl
            JOIN dl.debiturDataLeads db
            WHERE CONCAT(db.namaDepan,' ',db.namaTengah,' ',db.namaAkhir) LIKE %:search%
            """)
    int countByDebitur(String search);

    @Query("""
            SELECT COUNT(dl.id)
            FROM DataLeads dl
            JOIN dl.tipeAplikasiDataLeads ta
            WHERE ta.namaTipeAplikasi = :search
            """)
    int countByTipeAplikasi(String search);


    @Query("""
            SELECT COUNT(dl.id)
            FROM DataLeads dl
            JOIN dl.keteranganAplikasi ka
            WHERE ka.namaKeteranganAplikasi LIKE %:search%
            """)
    int countByKeterangan(String search);

    @Query("""
            SELECT DISTINCT COUNT(dl.id)
            FROM DataLeads dl
            WHERE dl.status = :search
            """)
    int countByStatus(boolean search);

    @Query("""
            SELECT dl.id
            FROM DataLeads dl
            """)
    List<String> getItemsById();

    @Query("""
            SELECT dl.nomorAplikasi
            FROM DataLeads dl
            """)
    List<String> getItemsByNomorAplikasi();

    @Query("""
            SELECT CONCAT(db.namaDepan,' ',db.namaTengah,' ',db.namaAkhir)
            FROM DataLeads dl
            JOIN dl.debiturDataLeads db
            """)
    List<String> getItemsByDebitur();

    @Query("""
            SELECT ta.namaTipeAplikasi
            FROM DataLeads dl
            JOIN dl.tipeAplikasiDataLeads ta
            """)
    List<String> getItemsByTipeAplikasi();

    @Query("""
            SELECT ka.namaKeteranganAplikasi
            FROM DataLeads dl
            JOIN dl.keteranganAplikasi ka
            """)
    List<String> getItemsByKeterangan();

    @Query(value = "SELECT DISTINCT CASE WHEN dl.status = 1 THEN 'Aktif' ELSE 'Tidak Aktif' END FROM DataLeads dl", nativeQuery = true)
    List<String> getItemsByStatus();

    @Query("""
            SELECT dl
            FROM DataLeads dl
            """)
    List<DataLeads> getAllOnly();
}
