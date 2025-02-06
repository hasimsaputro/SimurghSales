package com.sales.dto.wilayahHargaPasar;

import com.sales.dto.cabang.CabangIndexDTO;
import lombok.Data;

import java.util.List;

@Data
public class CabangWilayahGridDTO {
    private List<CabangIndexDTO> cabangIndexDTOS;
    private Integer currentPage;
    private Integer totalPages;
}
