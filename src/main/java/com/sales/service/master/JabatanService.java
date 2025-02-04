package com.sales.service.master;

import com.sales.dto.OptionDTO;
import com.sales.dto.master.JabatanDTO;
import com.sales.dto.master.JabatanFormDTO;
import com.sales.dto.master.KeteranganAplikasiDTO;
import com.sales.dto.master.KeteranganAplikasiFormDTO;

import java.util.List;

public interface JabatanService {

    List<JabatanDTO> getAll(String id , String name, Boolean status, Integer page, String filter, String search);
    Integer totalPage(String id ,String name,Boolean status);
    JabatanDTO getJabatanById(String id);
    List<OptionDTO> filter();
    List<OptionDTO> getSearchFilter(String filter);


    public void updateInsert(JabatanFormDTO jabatanFormDTO);
    public void delete(String id);

}

