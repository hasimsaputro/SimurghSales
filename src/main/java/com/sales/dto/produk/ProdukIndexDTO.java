package com.sales.dto.produk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProdukIndexDTO {
    private Integer kodeProduk;
    private String namaProduk;
    private String status;
}
