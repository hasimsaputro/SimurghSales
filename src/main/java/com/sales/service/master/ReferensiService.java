package com.sales.service.master;

import com.sales.dto.OptionDTO;
import com.sales.dto.master.DebiturReferensiOptionDTO;
import com.sales.dto.master.ReferensiFormDTO;
import com.sales.dto.master.ReferensiIndexDTO;
import com.sales.dto.master.TipeAplikasiDTO;

import java.util.List;

public interface ReferensiService {

    List<ReferensiIndexDTO> getAll(String id , String name, Boolean status, Integer page, String filter, String search);
    Integer totalPage(String id ,String name,Boolean status);
    ReferensiFormDTO getReferensiById(String id);
    List<OptionDTO> filter();
    List<OptionDTO> getSearchFilter(String filter);
    List<DebiturReferensiOptionDTO> getAllDebitur();


    public void updateInsert(ReferensiFormDTO referensiFormDTO);
    public void delete(String id);

}

