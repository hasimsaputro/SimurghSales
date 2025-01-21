package com.sales.dto.dataLeads;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DataLeadsDetailDTO {
    private String nomorDataLeads;
    private String produk;
    private String tipeDebitur;
    private String tipeAplikasi;
    private String nomorDebitur;
    private String namaDepanDebitur;
    private String namaTengahDebitur;
    private String namaAkhirDebitur;
    private String tipeIdentitas;
    private String nomorIdentitas;
    private String jenisKelamin;
    private String alamatIdentitas;
    private String kelurahan;
    private String kodePos;
    private String kecamatan;
    private String kabupaten;
    private String provinsi;
    private String alamatDomisili;
    private String kelurahanDomisili;
    private String kodePosDomisili;
    private String kecamatanDomisili;
    private String kabupatenDomisili;
    private String provinsiDomisili;
    private String cabangTujuan;
    private String nomorHp1;
    private String nomorHp2;
    private String nomorTelepon;
    private String mitraAgen; //Sumber Data Aplikasi
    private String debiturReferens; //Referensi
    private String jenisUsaha;
    private String keteranganAplikasi;
    private String surveyor;
    private String status;
    private String pot;
    private String tenor;
    private String estimasiFunding;
    private String kategori;
    private String merek;
    private String tipe;
    private String model;
    private String tahun;
    private String tahunPajakSTNK;
    private String nomorBPKB;
    private String nomorPolisi;
}
