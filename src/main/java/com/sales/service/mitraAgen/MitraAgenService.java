package com.sales.service.mitraAgen;


import com.sales.dto.mitraAgen.MitraAgenDetailDTO;
import com.sales.dto.mitraAgen.MitraAgenFormDTO;
import com.sales.dto.mitraAgen.MitraAgenIndexDTO;

import java.util.List;

public interface MitraAgenService {
    int getTotalPages(String id, Integer tipe, String name, Integer kelurahan, Integer cabang, Boolean status);
    List<MitraAgenIndexDTO> getAll(int page, String id, Integer tipe, String name, Integer kelurahan, Integer cabang, Boolean status);
    MitraAgenFormDTO getMitraAgenById(String id);
    MitraAgenDetailDTO getDetailMitraAgenById(String id);
    void save(MitraAgenFormDTO mitraAgenFormDTO);
    void delete(String id);
}
