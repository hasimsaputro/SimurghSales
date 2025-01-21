package com.sales.service.dataLeads;

import com.sales.dto.dataLeads.DataLeadsIndexDTO;

import java.util.List;

public interface DataLeadsService {
    List<DataLeadsIndexDTO> getAll(String filter, String search, int page);
    int getTotal(String filter,String search);
    List<OptionDTO> getfilterAsItem();
    List<DataLeadsIndexDTO> getAll(String fullName, String search, int page);

    void updateInsertDataLeads(DataLeadsFormDTO dataLeadsFormDTO);
}
