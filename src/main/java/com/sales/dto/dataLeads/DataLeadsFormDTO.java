package com.sales.dto.dataLeads;

import com.sales.entity.Kategori;
import com.sales.entity.Kelurahan;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class DataLeadsFormDTO {


    private String id;

    @NotBlank(message = "Nomor identitas harus diisi")
    private String idProduk;

    @NotNull(message = "Pilih Debitur")
    private Boolean tipeDebitur;

    @NotBlank(message = "Tipe Aplikasi Tidak Boleh Kosong")
    private String tipeAplikasi;

    @NotBlank(message = "Nama Debitur tidak boleh kosong")
    @Length(max=20, message = "Nomor identitas tidak boleh melebihi 50 karakter")
    private String namaDepanDebitur;

    private String namaTengahDebitur;

    private String namaBelakangDebitur;

    @NotNull(message = "Silahkan Pilih identitas")
    private Integer idIdentitas;

    @NotBlank(message = "Nomor Identitas tidak boleh kosong")
    private String  nomorIdentitas;

    @NotBlank(message = "Silahkan Pilih Jenis Kelamin")
    private String jenisKelamin;

//    @NotBlank(message = "Alamat Tidak Boleh Kosong")
//    @Length(max=100, message = "Alamat tidak boleh lebih dari 100 karakter")
//    private String idDebiturReferensi;

    @NotBlank(message = "Nama Debitur tidak boleh kosong")
    private String kelurahan;

    private String  kodePos;

    private String kecamatan;

    private String  kotaKabupaten;

    private String  provinsi;

    @NotBlank(message = "Alamat tidak boleh Kosong")
    private String  alamatDomisili;

    @NotBlank(message = "Alamat tidak boleh Kosong")
    private String  alamatIdentitas;

    @NotBlank(message = "Kelurahan Tidak Boleh Kosong")
    private String  kelurahanDomisili;

    private String kodePosDomisili;

    private String kecamatanDomisili;

    private String kotaKabupatenDomisili;

    private String provinsiDomisili;

    private String namaCabangTujuan;

    @NotBlank(message = "Nomor Handphone Tidak Boleh Kosong")
    @Length(max=20, message = "Nomor Handphone tidak boleh lebih dari 20 karakter")
    private String nomorHandphone1;

    @Length(max=20, message = "Nomor Handphone tidak boleh lebih dari 20 karakter")
    private String nomorHandphone2;

    @Length(max=20, message = "Nomor telepon tidak boleh lebih dari 20 karakter")
    private String nomorTelepon;

    private String sumberDataAplikasi;

    private String referensi;

    private String jenisUsaha;

    @NotBlank(message = "Keterangan aplikasi Tidak Boleh Kosong")
    private String keteranganAplikasi;

    private String surveyor;

    private Boolean status;

    @NotBlank(message = "POT Tidak Boleh Kosong")
    private String pot;

    private String tenor;

    private String estimasiNilaiFunding;

    @NotBlank(message = "Kategori Tidak Boleh Kosong")
    private String kategori;

    private String merek;

    private String tipe;

    private String model;

    private String tahun;

    private String tahunPajakSTNK;


    private String nomorBPKB;

    private String nomorPolisi;
}
