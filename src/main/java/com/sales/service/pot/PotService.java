package com.sales.service.pot;

import com.sales.dto.OptionDTO;
import com.sales.dto.pot.PotFormDTO;
import com.sales.dto.pot.PotIndexDTO;

import java.util.List;

public interface PotService {
    PotFormDTO getPotById(Integer potId);
    List<PotIndexDTO> getAll(String filter, String search, int page);
    int getTotal(String filter,String search);
    List<OptionDTO> getfilterAsItem();

    void savePOT(PotFormDTO dto);

    void deletePot(Integer potId);

    Object getPotByIdDetail(String potId);
}
