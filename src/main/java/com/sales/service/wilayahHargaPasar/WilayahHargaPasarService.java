package com.sales.service.wilayahHargaPasar;

import com.sales.dto.FilterIndexOptionDTO;
import com.sales.dto.wilayahHargaPasar.WilayahHargaPasarFormDTO;
import com.sales.dto.wilayahHargaPasar.WilayahHargaPasarIndexDTO;

import java.util.List;

public interface WilayahHargaPasarService {
    int getTotalPages(String filter, String search);
    List<WilayahHargaPasarIndexDTO> getAllWilayahHargaPasar(int page, String filter, String search);
    void save(WilayahHargaPasarFormDTO wilayahHargaPasarFormDTO);
    void delete(String id);

    List<FilterIndexOptionDTO> getFilterAsItem();
    List<FilterIndexOptionDTO> getSearchItems(String filter);
}
