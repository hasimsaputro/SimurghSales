package com.sales.service.wilayahHargaPasar;

import com.sales.dto.OptionDTO;
import com.sales.dto.wilayahHargaPasar.CabangWilayahDTO;
import com.sales.dto.wilayahHargaPasar.WilayahHargaPasarDetailDTO;
import com.sales.dto.wilayahHargaPasar.WilayahHargaPasarFormDTO;

import java.util.List;

public interface WilayahHargaPasarService {
    WilayahHargaPasarFormDTO getWilayahById(String wilayahId);

    List<CabangWilayahDTO> getCabangByWilayahId(String wilayahId);

    WilayahHargaPasarDetailDTO getWilayahDetailById(String wilayahId);

    void deleteWilayah(String wilayahId);

    List<OptionDTO> getfilterAsItem();

    void saveWilayah(WilayahHargaPasarFormDTO wilayahHargaPasarFormDTO);

    List<OptionDTO> getKategoriItems();

    List<OptionDTO> getFilterCabang();
}
