package com.sales.service.mitraAgen;


import com.sales.dto.mitraAgen.*;

import java.util.List;

public interface MitraAgenService {
    int getTotalPages(String filter, String search);
    List<MitraAgenIndexDTO> getAll(int page, String filter, String search);
    MitraAgenFormDTO getMitraAgenById(String id);
    MitraAgenDetailDTO getDetailMitraAgenById(String id);
    void save(MitraAgenFormDTO mitraAgenFormDTO);
    void delete(String id);
    String generateId(int tipe);
    List<MitraAgenIndexOptionDTO> getFilterAsItem();
    List<MitraAgenIndexOptionDTO> getSearchItems(String filter);
    List<TipeMasterOptionDTO> getTipeMasterOption();
    List<ProdukOptionDTO> getProdukOption(int page, int size);
    List<KelurahanOptionDTO> getKelurahanOption();
    List<IdentitasOptionDTO> getIdentitasOption();
    List<BankOptionDTO> getBankOption();
}
