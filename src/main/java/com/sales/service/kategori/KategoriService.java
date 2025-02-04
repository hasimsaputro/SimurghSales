package com.sales.service.kategori;

import com.sales.dto.OptionDTO;
import com.sales.dto.kategori.KategoriDetailDTO;
import com.sales.dto.kategori.KategoriFormDTO;
import com.sales.dto.kategori.KategoriIndexDTO;

import java.util.List;

public interface KategoriService {
    List<KategoriIndexDTO> getAll(String filter, String search, int page);

    int getTotal(String filter, String search);

    List<OptionDTO> getfilterAsItem();

    KategoriFormDTO getKategoriById(Integer kategoriId);

    void saveKategori(KategoriFormDTO dto);

    KategoriDetailDTO getKategoriByIdDetail(Integer kategoriId);

    void deleteKategori(Integer kategoriId);

    List<OptionDTO> getSearchItems(String filter);
}
