package com.sales.dto.tipe;

import com.sales.dto.OptionDTO;
import lombok.Data;

@Data
public class TipeJenisDTO {
    private String id;
    private String namaTipe;
    private OptionDTO jenis;
}
