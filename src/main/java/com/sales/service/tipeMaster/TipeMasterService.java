package com.sales.service.tipeMaster;

import com.sales.dto.tipeMaster.TipeMasterDetailDTO;
import com.sales.dto.tipeMaster.TipeMasterFormDTO;
import com.sales.dto.tipeMaster.TipeMasterIndexDTO;
import com.sales.dto.tipeMaster.TipeMasterIndexOptionDTO;

import java.util.List;

public interface TipeMasterService {
    int getTotalPages(String filter, String search);
    List<TipeMasterIndexDTO> getAll(int page, String filter, String search);
    TipeMasterFormDTO getTipeMasterById(Integer id);
    TipeMasterDetailDTO getDetailTipeMasterById(Integer id);
    void save(TipeMasterFormDTO tipeMasterFormDTO);
    void delete(int id);
    List<TipeMasterIndexOptionDTO> getFilterAsItem();
    List<TipeMasterIndexOptionDTO> getSearchItems(String filter);
}
