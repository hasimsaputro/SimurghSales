package com.sales.dto.wilayahHargaPasar;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class WilayahHargaPasarFormDTO {
    private String id;
    @NotBlank(message = "Nama Wilayah harus diisi")
    private String namaWilayah;
    @NotNull
    private Integer idKategori;
    private String namaKategori;
    @NotNull
    private Boolean status;


    @JsonProperty("cabangList")
    private List<Integer> cabangList;
}
