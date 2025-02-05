package com.sales.service.kabupaten;

import com.sales.dto.FilterIndexOptionDTO;
import com.sales.dto.FilterOptionDTO;
import com.sales.dto.kabupaten.KabupatenDetailDTO;
import com.sales.dto.kabupaten.KabupatenFormDTO;
import com.sales.dto.kabupaten.KabupatenIndexDTO;

import java.util.List;

public interface KabupatenService {
    int getTotalPages(String filter, String search);
    List<KabupatenIndexDTO> getAllKabupaten(int page, String filter, String search);
    KabupatenDetailDTO getKabupatenDetailById(Integer id);
    KabupatenFormDTO getKabupatenFormById(Integer id);
    void save(KabupatenFormDTO kabupatenFormDTO);
    void delete(Integer id);

    List<FilterIndexOptionDTO> getFilterAsItem();
    List<FilterIndexOptionDTO> getSearchItems(String filter);
    List<FilterOptionDTO> getProvinsiOption();
}
