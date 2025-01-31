package com.sales.service.master;

import com.sales.dto.OptionDTO;
import com.sales.dto.OptionKelurahanDTO;
import com.sales.dto.dataLeads.DataLeadsDetailDTO;
import com.sales.dto.dataLeads.DataLeadsFormDTO;
import com.sales.dto.dataLeads.DataLeadsIndexDTO;
import com.sales.dto.dataLeads.KeteranganAplikasiSurveyorDTO;
import com.sales.dto.master.TipeAplikasiDTO;
import com.sales.entity.TipeAplikasi;

import java.util.List;

public interface TipeAplikasiService {

    List<TipeAplikasiDTO> getAll(Integer id ,String name,Boolean status, Integer page, String filter, String search);
    Integer totalPage(Integer id ,String name,Boolean status);
    TipeAplikasiDTO getTipeAplikasiById(Integer id);
    List<OptionDTO> filter();
    List<OptionDTO> getSearchFilter(String filter);


    public void updateInsert(TipeAplikasiDTO tipeAplikasiDTO);
    public void delete(TipeAplikasiDTO tipeAplikasiDTO);

}

