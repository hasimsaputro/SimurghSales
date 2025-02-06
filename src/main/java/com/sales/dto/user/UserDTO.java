package com.sales.dto.user;

import lombok.Data;

@Data
public class UserDTO {
    private String NIK;

    private String namaKaryawan;

    private String email;

    private String namaJabatan;

    private Boolean status;
}
