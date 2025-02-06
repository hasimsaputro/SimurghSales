package com.sales.service.keteranganAplikasi;

import com.sales.dto.OptionDTO;
import com.sales.dto.keteranganAplikasi.KeteranganAplikasiDTO;
import com.sales.dto.keteranganAplikasi.KeteranganAplikasiFormDTO;

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

