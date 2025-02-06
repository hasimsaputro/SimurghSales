package com.sales.service.jabatan;

import com.sales.dto.OptionDTO;
import com.sales.dto.jabatan.JabatanDTO;
import com.sales.dto.jabatan.JabatanFormDTO;

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

