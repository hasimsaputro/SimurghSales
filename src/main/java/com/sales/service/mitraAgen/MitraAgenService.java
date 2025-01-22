package com.sales.service.mitraAgen;


import com.sales.dto.mitraAgen.MitraAgenDetailDTO;
import com.sales.dto.mitraAgen.MitraAgenFormDTO;
import com.sales.dto.mitraAgen.MitraAgenIndexDTO;
import com.sales.dto.mitraAgen.MitraAgenIndexOptionDTO;

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
}
