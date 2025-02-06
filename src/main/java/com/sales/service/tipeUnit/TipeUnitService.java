package com.sales.service.tipeUnit;

import com.sales.dto.OptionDTO;
import com.sales.dto.tipeAplikasi.TipeAplikasiDTO;
import com.sales.dto.tipeUnit.TipeUnitDTO;
import com.sales.dto.tipeUnit.TipeUnitFormDTO;

import java.util.List;

public interface TipeUnitService {

    List<TipeUnitDTO> getAll(String id , String name, Boolean status, Integer page, String filter, String search);
    Integer totalPage(String id ,String name,Boolean status, String filter, String search);
    TipeUnitFormDTO getUnitById(String id);
    List<OptionDTO> filter();
    List<OptionDTO> getSearchFilter(String filter);


    public void updateInsert(TipeUnitFormDTO tipeAplikasiDTO);
    public void delete(String id);

}

