package com.sales.dto.mitraAgen;

import lombok.Data;
import org.hibernate.event.spi.PreInsertEvent;

@Data
public class MitraAgenDetailDTO {
    private String id;
    private String tipe;
    private String produk;
    private String cabang;
    private String nomorIdentitas;
    private String nama;
    private String jenisKelamin;
    private String npwp;
    private String alamatIdentitas;
    private String kelurahanIdentitas;
    private String kodePosIdentitas;
    private String kecamatanIdentitas;
    private String kotaIdentitas;
    private String provinsiIdentitas;
    private String kelurahanDomisili;
    private String kodePosDomisili;
    private String kecamatanDomisili;
    private String kotaDomisili;
    private String provinsiDomisili;
    private String tempatLahir;
    private String tanggalLahir;
    private String nomorTelepon;
    private String nomorHandphone;
    private String bank;
    private String nomorRekening;
    private String namaRekening;
    private String status;
}
