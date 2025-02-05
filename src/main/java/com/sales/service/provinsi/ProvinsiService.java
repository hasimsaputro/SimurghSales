package com.sales.service.provinsi;

import com.sales.dto.FilterIndexOptionDTO;
import com.sales.dto.provinsi.ProvinsiDetailDTO;
import com.sales.dto.provinsi.ProvinsiFormDTO;
import com.sales.dto.provinsi.ProvinsiIndexDTO;

import java.util.List;

public interface ProvinsiService {
    int getTotalPages(String filter, String search);
    List<ProvinsiIndexDTO> getAllProvinsi(int page, String filter, String search);
    ProvinsiDetailDTO getProvinsiDetailById(Integer id);
    ProvinsiFormDTO getProvinsiFormById(Integer id);
    void save(ProvinsiFormDTO provinsiFormDTO);
    void delete(Integer id);

    List<FilterIndexOptionDTO> getFilterAsItem();
    List<FilterIndexOptionDTO> getSearchItems(String filter);
}
