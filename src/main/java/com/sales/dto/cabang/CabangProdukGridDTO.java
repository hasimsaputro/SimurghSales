package com.sales.dto.cabang;

import com.sales.dto.produk.ProdukIndexDTO;
import lombok.Data;

import java.util.List;

@Data
public class CabangProdukGridDTO {
    private List<ProdukIndexDTO> produkIndexDTOS;
    private Integer currentPage;
    private Integer totalPages;
}
