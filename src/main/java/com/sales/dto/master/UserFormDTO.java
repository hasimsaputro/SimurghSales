package com.sales.dto.master;

import lombok.Data;

@Data
public class UserFormDTO {
    private String NIK;

    private String namaKaryawan;

    private String email;

    private String namaJabatan;

    private String idJabatan;

    private String namaCabang;

    private String emailEksternal;

    private String nomorHp;

    private Boolean status;
}
