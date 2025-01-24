package com.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OptionKelurahanDTO {
    private String kelurahan;
    private String kecamatan;
    private String kotaKabupaten;
    private String provinsi;
    private String kodePos;
    private Integer value;
}
