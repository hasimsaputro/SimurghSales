package com.sales.dto.dataLeads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class POTDataDTO {
    private Integer id;
    private String namaPOT;
    private String namaKategori;
    private List<String> namaMerk;
    private List<String> namaTipe;
    private List<String> namaModel;
}
