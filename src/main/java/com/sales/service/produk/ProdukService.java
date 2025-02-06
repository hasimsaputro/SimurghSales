package com.sales.service.produk;

import com.sales.dto.cabang.*;
import com.sales.dto.produk.ProdukDetailDTO;
import com.sales.dto.produk.ProdukFormDTO;
import com.sales.dto.produk.ProdukIndexDTO;
import com.sales.dto.produk.ProdukIndexOptionDTO;

import java.util.List;

public interface ProdukService {
    int getTotalPages(String filter, String search);
    int getTotalPagesAktif(String filter, String search);
    List<ProdukIndexDTO> getAll(int page, String filter, String search);
    List<ProdukIndexDTO> getAllAktif(int page, String filter, String search);
    CabangProdukGridDTO getAllRest(int page, String filter, String search);
    ProdukFormDTO getProdukById(Integer id);
    ProdukDetailDTO getDetailProdukById(Integer id);
    void save(ProdukFormDTO produkFormDTO);
    void delete(int id);
    List<ProdukIndexOptionDTO> getFilterAsItem();
    List<ProdukIndexOptionDTO> getFilterAsItemNonStatus();
    List<ProdukIndexOptionDTO> getSearchItems(String filter);
    List<ProdukIndexDTO> getAllProduks();
}
