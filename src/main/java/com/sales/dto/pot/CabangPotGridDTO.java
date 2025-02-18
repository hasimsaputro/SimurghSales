package com.sales.dto.pot;

import com.sales.dto.cabang.CabangIndexDTO;
import lombok.Data;

import java.util.List;

@Data
public class CabangPotGridDTO {
    private List<CabangIndexDTO> cabangIndexDTOS;
    private Integer currentPage;
    private Integer totalPages;
}
