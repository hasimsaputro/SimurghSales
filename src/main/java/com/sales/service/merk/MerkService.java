package com.sales.service.merk;

import com.sales.dto.OptionDTO;
import com.sales.dto.kategori.KategoriDetailDTO;
import com.sales.dto.kategori.KategoriFormDTO;
import com.sales.dto.kategori.KategoriIndexDTO;
import com.sales.dto.merk.MerkDetailDTO;
import com.sales.dto.merk.MerkFormDTO;
import com.sales.dto.merk.MerkIndexDTO;

import java.util.List;

public interface MerkService {
    List<MerkIndexDTO> getAll(String filter, String search, int page);

    int getTotal(String filter, String search);

    List<OptionDTO> getfilterAsItem();

    MerkFormDTO getMerkById(String merkId);

    void saveMerk(MerkFormDTO dto);

    MerkDetailDTO getMerkByIdDetail(String merkId);

    void deleteMerk(String merkId);

    List<OptionDTO> getSearchItems(String filter);

    List<OptionDTO> getKategoriItems();

    List<OptionDTO> getNegaraItems();
}
