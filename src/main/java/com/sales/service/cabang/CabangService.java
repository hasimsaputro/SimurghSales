package com.sales.service.cabang;

import com.sales.dto.OptionDTO;
import com.sales.dto.cabang.*;
import com.sales.dto.mitraAgen.*;
import com.sales.entity.Produk;

import java.util.List;
import java.util.Set;

public interface CabangService {
    int getTotalPages(String filter, String search);
    List<CabangIndexDTO> getAll(int page, String filter, String search);
    CabangFormDTO getCabangById(Integer id);
    CabangDetailDTO getDetailCabangById(Integer id);
    void save(CabangFormDTO cabangFormDTO);
    void delete(int id);
    List<CabangIndexOptionDTO> getFilterAsItem();
    List<CabangIndexOptionDTO> getSearchItems(String filter);
    List<CabangProdukDTO> getProdukByCabang(Integer id);

    List<OptionDTO> getfilterAsItem();
}
