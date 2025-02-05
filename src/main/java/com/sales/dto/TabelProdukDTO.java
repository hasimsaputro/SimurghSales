package com.sales.dto;

import com.sales.dto.produk.ProdukIndexDTO;
import lombok.Data;

import java.util.List;

@Data
public class TabelProdukDTO {
    private List<ProdukIndexDTO> produkIndexDTOS;
    private Integer currentPage;
    private Integer totalPages;

}
