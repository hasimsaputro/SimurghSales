package com.sales.service.dataLeads;

import com.sales.dto.OptionDTO;
import com.sales.dto.dataLeads.*;
import com.sales.entity.Kategori;
import com.sales.entity.Merk;
import com.sales.entity.Tipe;
import com.sales.entity.POT;

import java.util.List;

public interface DataLeadsService {
    DataLeadsFormDTO getDataLeadsById(String dataLeadsId);
    DataLeadsDetailDTO getDataLeadsByIdDetail(String dataLeadsId);
    List<DataLeadsIndexDTO> getAll(String filter, String search, int page);
    int getTotal(String filter,String search);
    List<OptionDTO> getfilterAsItem();
    void deleteDataLeads(String dataLeadsId);
    void updateInsertDataLeads(DataLeadsFormDTO dataLeadsFormDTO);

    List<OptionDTO> getOptionSumberDataAplikasi();
    List<OptionDTO> getOptionReferensi();
    List<OptionDTO> getOptionKeteranganAplikasi();
    String getEstimasiNilaiFunding(EstimasiNilaiFundingDTO dto);
    List<OptionDTO> getSearchItems(String filter);

    List<OptionDTO> getKelurahanItems();

    List<OptionDTO> getPotItems();

    POTDataDTO getPotData(Integer idPOT);
}
