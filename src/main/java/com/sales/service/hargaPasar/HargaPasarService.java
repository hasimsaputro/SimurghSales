package com.sales.service.hargaPasar;

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
    void save(HargaPasarFormDTO hargaPasarFormDTO);
    void delete(int id);
    List<HargaPasarIndexOptionDTO> getFilterAsItem();
    List<HargaPasarIndexOptionDTO> getSearchItems(String filter);
}
