package com.sales.dto.dataLeads;

import com.sales.dto.OptionDTO;
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
    private String tenor;
    private String estimasiNilaiFunding;
    private OptionDTO kategori;
    private List<OptionDTO> merk;
    private List<OptionDTO> tipe;
    private List<OptionDTO> model;
}
