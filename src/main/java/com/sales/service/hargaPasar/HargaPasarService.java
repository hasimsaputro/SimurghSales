package com.sales.service.hargaPasar;

import com.sales.dto.OptionDTO;
import com.sales.dto.cabang.*;
import com.sales.dto.hargaPasar.HargaPasarDetailDTO;
import com.sales.dto.hargaPasar.HargaPasarFormDTO;
import com.sales.dto.hargaPasar.HargaPasarIndexDTO;
import com.sales.dto.hargaPasar.HargaPasarIndexOptionDTO;
import com.sales.dto.mitraAgen.KelurahanOptionDTO;

import java.util.List;

public interface HargaPasarService {
    int getTotalPages(String filter, String search);
    List<HargaPasarIndexDTO> getAll(int page, String filter, String search);
    HargaPasarFormDTO getHargaPasarById(Integer id);
    HargaPasarDetailDTO getDetailHargaPasarById(Integer id);
    List<HargaPasarIndexOptionDTO> getFilterAsItem();
    List<HargaPasarIndexOptionDTO> getSearchItems(String filter);

    void saveHargaPasar(HargaPasarFormDTO dto);

    void deleteHargaPasar(Integer id);

    List<OptionDTO> getKategoriItems();

    List<OptionDTO> getWilayahItems();

    List<OptionDTO> getMerkItems();

    List<OptionDTO> getTipeItems();

    List<OptionDTO> getModelItems();

    OptionDTO getTipeJenis(String idTipe);

    List<OptionDTO> getTipeUnitItems();
}
