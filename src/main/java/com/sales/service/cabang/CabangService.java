package com.sales.service.cabang;

import com.sales.dto.OptionDTO;
import com.sales.dto.cabang.*;
import com.sales.dto.mitraAgen.*;
import com.sales.dto.pot.CabangPotGridDTO;
import com.sales.dto.produk.ProdukIndexDTO;
import com.sales.dto.produk.ProdukIndexOptionDTO;
import com.sales.entity.Produk;

import java.util.List;
import java.util.Set;

public interface CabangService {
    int getTotalPages(String filter, String search);
    int getTotalPagesAktif(String filter, String search);
    List<CabangIndexDTO> getAll(int page, String filter, String search);
    CabangFormDTO getCabangById(Integer id);
    CabangDetailDTO getDetailCabangById(Integer id);
    void save(CabangFormDTO cabangFormDTO);
    void delete(int id);
    List<CabangIndexOptionDTO> getFilterAsItem();
    List<CabangIndexOptionDTO> getSearchItems(String filter);
    List<CabangProdukDTO> getProdukByCabang(Integer id);
    List<KelurahanOptionDTO> getKelurahanOption();
    List<ProdukIndexOptionDTO> getSearchProdukItems(String filter);
    List<OptionDTO> getfilterAsItem();
    List<CabangIndexDTO> getAllAktif(int page, String filter, String search);
    CabangPotGridDTO getAllCabang(int page, String filter, String search);

    List<CabangIndexDTO> getAllCabangs();

    List<OptionDTO> getSearchCabangItems(String filter);
}
