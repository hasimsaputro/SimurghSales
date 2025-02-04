package com.sales.service.tipe;

import com.sales.dto.OptionDTO;
import com.sales.dto.merk.MerkDetailDTO;
import com.sales.dto.merk.MerkFormDTO;
import com.sales.dto.merk.MerkIndexDTO;
import com.sales.dto.tipe.TipeDetailDTO;
import com.sales.dto.tipe.TipeFormDTO;
import com.sales.dto.tipe.TipeIndexDTO;

import java.util.List;

public interface TipeService {
    List<TipeIndexDTO> getAll(String filter, String search, int page);

    int getTotal(String filter, String search);

    List<OptionDTO> getfilterAsItem();

    TipeFormDTO getTipeById(String tipeId);

    void saveTipe(TipeFormDTO dto);

    TipeDetailDTO getTipeByIdDetail(String tipeId);

    void deleteTipe(String tipeId);

    List<OptionDTO> getSearchItems(String filter);

    List<OptionDTO> getKategoriItems();

    List<OptionDTO> getMerkItems();

    List<OptionDTO> getJenisItems();
}
