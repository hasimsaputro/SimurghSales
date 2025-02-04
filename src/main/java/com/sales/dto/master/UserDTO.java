package com.sales.dto.master;

import lombok.Data;
import org.springframework.data.domain.PageRequest;

@Data
public class UserDTO {
    private String NIK;

    private String namaKaryawan;

    private String email;

    private String namaJabatan;

    private Boolean status;
}
