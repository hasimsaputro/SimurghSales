package com.sales.service.kelurahan;

import com.sales.dto.FilterIndexOptionDTO;
import com.sales.dto.FilterOptionDTO;
import com.sales.dto.kelurahan.*;

import java.util.List;

public interface KelurahanService {
    int getTotalPages(String filter, String search);
    List<KelurahanIndexDTO> getAllKelurahan(int page, String filter, String search);
    KelurahanDetailDTO getKelurahanDetailById(Integer id);
    KelurahanFormDTO getKelurahanFormById(Integer id);
    void save(KelurahanFormDTO kelurahanFormDTO);
    void delete(Integer id);

    List<FilterIndexOptionDTO> getFilterAsItem();
    List<FilterIndexOptionDTO> getSearchItems(String filter);
    List<FilterOptionDTO> getProvinsiOption();
    List<FilterOptionDTO> getKabupatenOption(Integer id);
    List<FilterOptionDTO> getKecamatanOption(Integer id);
}
