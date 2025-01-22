package com.sales.dto.dataLeads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class DataLeadsDetailDTO {
    private String id;

    private Integer idProduk;

    private String tipeDebitur;

    private String tipeAplikasi;

    private String namaDepanDebitur;

    private String namaTengahDebitur;

    private String namaBelakangDebitur;

    private String idIdentitas;

    private String  nomorIdentitas;

    private String jenisKelamin;

    private String idDebiturReferensi;

    private String kelurahan;

    private String  kodePos;

    private String kecamatan;

    private String  kotaKabupaten;

    private String  provinsi;

    private String  alamatDomisili;

    private String  alamatIdentitas;

    private String  kelurahanDomisili;

    private String kodePosDomisili;

    private String kecamatanDomisili;

    private String kotaKabupatenDomisili;

    private String provinsiDomisili;

    private String namaCabangTujuan;

    private String nomorHandphone1;

    private String nomorHandphone2;

    private String nomorTelepon;

    private String sumberDataAplikasi;

    private String referensi;

    private String jenisUsaha;

    private String keteranganAplikasi;

    private String surveyor;

    private Boolean status;

    private String pot;

    private String tenor;

    private String estimasiNilaiFunding;

    private String kategori;

    private String merek;

    private String tipe;

    private String model;

    private String tahun;

    private String tahunPajakSTNK;

    private String nomorBPKB;

    private String nomorPolisi;
}
