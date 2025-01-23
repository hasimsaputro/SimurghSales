package com.sales.service.dataLeads;

import com.sales.dto.OptionDTO;
import com.sales.dto.dataLeads.DataLeadsFormDTO;
import com.sales.dto.dataLeads.DataLeadsIndexDTO;
import com.sales.dto.dataLeads.POTDataDTO;
import com.sales.entity.POT;

import java.util.List;

public interface DataLeadsService {
    DataLeadsFormDTO getDataLeadsById(String dataLeadsId);
    List<DataLeadsIndexDTO> getAll(String filter, String search, int page);
    int getTotal(String filter,String search);
    List<OptionDTO> getfilterAsItem();
    void deleteDataLeads(String dataLeadsId);
    void updateInsertDataLeads(DataLeadsFormDTO dataLeadsFormDTO);

    List<OptionDTO> getSearchItems(String filter);

    List<OptionDTO> getKelurahanItems();

    List<OptionDTO> getPotItems();

    POTDataDTO getPotData(Integer idPOT);
}
