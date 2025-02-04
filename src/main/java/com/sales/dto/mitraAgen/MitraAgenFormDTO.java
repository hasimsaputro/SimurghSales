package com.sales.dto.mitraAgen;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
public class MitraAgenFormDTO {
    private String id;
    private Integer idTipeMaster;
    private String namaTipeMaster;
    private Integer idProduk;
    private String namaProduk;
    private Integer idCabang;
    private String namaCabang;
    private Integer idIdentitas;
    private String namaIdentitas;

    @NotBlank(message = "Nomor identitas harus diisi")
    @Length(max=20, message = "Nomor identitas tidak boleh melebihi 20 karakter")
    private String nomorIdentitas;

    @NotBlank(message = "Nama harus diisi")
    @Length(max = 100, message = "Nama tidak boleh melebihi 100 karakter")
    private String nama;

    @NotBlank(message = "Jenis kelamin harus diisi")
    private String jenisKelamin;

    @Length(max = 20, message = "NPWP tidak boleh melebihi 20 karakter")
    private String npwp;

    @NotBlank(message = "Alamat identitas harus diisi")
    @Length(max = 200, message = "Alamat identitas tidak boleh melebihi 200 karakter")
    private String alamatIdentitas;

//    @NotBlank(message = "Kelurahan identitas harus diisi")
//    private Integer idKelurahanIdentitas;

    private String namaKelurahanIdentitas;
    private String kodeposIdentitas;
    private String kecamatanIdentitas;
    private String kotaIdentitas;
    private String provinsiIdentitas;

    @NotBlank(message = "Alamat domisili harus diisi")
    @Length(max = 200, message = "Alamat domisili tidak boleh melebihi 200 karakter")
    private String alamatDomisili;

//    @NotBlank(message = "Kelurahan domisili harus diisi")
//    private Integer idKelurahanDomisili;

    private String namaKelurahanDomisili;
    private String kodeposDomisili;
    private String kecamatanDomisili;
    private String kotaDomisili;
    private String provinsiDomisili;

    @NotBlank(message = "Tempat lahir harus diisi")
    @Length(max = 50, message = "Tempat lahir tidak boleh melebihi 50 karakter")
    private String tempatLahir;

    //    @NotBlank(message = "Tanggal lahir harus diisi")
    private LocalDate tanggalLahir;

    @Length(max = 20, message = "Nomor telepon tidak boleh melebihi 20 angka")
    private String nomorTelepon;

    @NotBlank(message = "Nomor Handphone harus diisi")
    @Length(max = 20, message = "Nomor handphone tidak boleh melebihi 20 angka")
    private String nomorHandPhone;

//    @NotBlank(message = "Bank tidak boleh kosong")
//    private Integer idBank;

    private String namaBank;

    @NotBlank(message = "Nomor rekening harus diisi")
    @Length(max = 20, message = "Nomor rekening tidak boleh melebihi 20 angka")
    private String nomorRekening;

    @NotBlank(message = "Nama rekening harus diisi")
    @Length(max = 100, message = "Nama rekening tidak boleh melebihi 100 karakter")
    private String namaRekening;

    //    @NotBlank(message = "Status harus diisi")
    private Boolean status;
}
