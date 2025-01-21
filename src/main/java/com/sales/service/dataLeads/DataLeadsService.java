package com.sales.service.dataLeads;

import com.sales.dto.OptionDTO;
import com.sales.dto.dataLeads.DataLeadsFormDTO;
import com.sales.dto.dataLeads.DataLeadsIndexDTO;

import java.util.List;

public interface DataLeadsService {
    List<DataLeadsIndexDTO> getAll(String fullName, String search, int page);
    DataLeadsFormDTO getDataLeadsById(String dataLeadsId);
    List<DataLeadsIndexDTO> getAll(String filter, String search, int page);
    int getTotal(String filter,String search);
    List<OptionDTO> getfilterAsItem();

    void updateInsertDataLeads(DataLeadsFormDTO dataLeadsFormDTO);
}
