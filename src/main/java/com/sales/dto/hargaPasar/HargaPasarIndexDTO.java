package com.sales.dto.hargaPasar;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class HargaPasarIndexDTO {
    private Integer idHargaPasar;
    private String wilayah;
    private String kategori;
    private String merk;
    private String tipe;
    private String model;
    private String jenis;
    private String tipeUnit;
    private String tahun;
    private BigDecimal harga;
    private LocalDate tanggalMulaiBerlaku;
}
