package com.sales.service.referensi;

import com.sales.dto.OptionDTO;
import com.sales.dto.debitur.DebiturReferensiOptionDTO;
import com.sales.dto.referensi.ReferensiFormDTO;
import com.sales.dto.referensi.ReferensiIndexDTO;

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

