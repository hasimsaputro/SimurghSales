package com.sales.service.wilayahHargaPasar;

import com.sales.dto.OptionDTO;
import com.sales.dto.wilayahHargaPasar.CabangWilayahDTO;
import com.sales.dto.wilayahHargaPasar.WilayahHargaPasarDetailDTO;
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

    WilayahHargaPasarFormDTO getWilayahById(String wilayahId);

    List<CabangWilayahDTO> getCabangByWilayahId(String wilayahId);

    WilayahHargaPasarDetailDTO getWilayahDetailById(String wilayahId);

    void deleteWilayah(String wilayahId);

    List<OptionDTO> getfilterAsItem();

    void saveWilayah(WilayahHargaPasarFormDTO wilayahHargaPasarFormDTO);

    List<OptionDTO> getKategoriItems();

    List<OptionDTO> getFilterCabang();
}
