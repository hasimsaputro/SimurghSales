package com.sales.service.master;

import com.sales.dto.OptionDTO;
import com.sales.dto.master.KeteranganAplikasiDTO;
import com.sales.dto.master.KeteranganAplikasiFormDTO;
import com.sales.dto.master.TipeAplikasiDTO;

import java.util.List;

public interface KeteranganAplikasiService {

    List<KeteranganAplikasiDTO> getAll(Integer id , String name, Boolean status, Integer page, String filter, String search);
    Integer totalPage(Integer id ,String name,Boolean status);
    KeteranganAplikasiDTO getKeteranganAplikasiById(Integer id);
    List<OptionDTO> filter();
    List<OptionDTO> getSearchFilter(String filter);


    public void updateInsert(KeteranganAplikasiFormDTO keteranganAplikasiFormDTO);
    public void delete(Integer id);

}

