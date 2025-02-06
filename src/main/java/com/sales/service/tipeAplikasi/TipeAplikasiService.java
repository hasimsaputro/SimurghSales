package com.sales.service.tipeAplikasi;

import com.sales.dto.OptionDTO;
import com.sales.dto.tipeAplikasi.TipeAplikasiDTO;

import java.util.List;

public interface TipeAplikasiService {

    List<TipeAplikasiDTO> getAll(Integer id ,String name,Boolean status, Integer page, String filter, String search);
    Integer totalPage(Integer id ,String name,Boolean status, String filter, String search);
    TipeAplikasiDTO getTipeAplikasiById(Integer id);
    List<OptionDTO> filter();
    List<OptionDTO> getSearchFilter(String filter);


    public void updateInsert(TipeAplikasiDTO tipeAplikasiDTO);
    public void delete(Integer id);

}

