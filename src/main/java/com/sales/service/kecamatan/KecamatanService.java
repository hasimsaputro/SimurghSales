package com.sales.service.kecamatan;

import com.sales.dto.FilterIndexOptionDTO;
import com.sales.dto.FilterOptionDTO;
import com.sales.dto.kecamatan.KecamatanDetailDTO;
import com.sales.dto.kecamatan.KecamatanFormDTO;
import com.sales.dto.kecamatan.KecamatanIndexDTO;

import java.util.List;

public interface KecamatanService {
    int getTotalPages(String filter, String search);
    List<KecamatanIndexDTO> getAllKecamatan(int page, String filter, String search);
    KecamatanDetailDTO getKecamatanDetailById(Integer id);
    KecamatanFormDTO getKecamatanFormById(Integer id);
    void save(KecamatanFormDTO kecamatanFormDTO);
    void delete(Integer id);

    List<FilterIndexOptionDTO> getFilterAsItem();
    List<FilterIndexOptionDTO> getSearchItems(String filter);
    List<FilterOptionDTO> getProvinsiOption();
    List<FilterOptionDTO> getKabupatenOption(Integer id);
}
