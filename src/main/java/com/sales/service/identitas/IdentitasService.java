package com.sales.service.identitas;

import com.sales.dto.identitas.IdentitasDetailDTO;
import com.sales.dto.identitas.IdentitasFormDTO;
import com.sales.dto.identitas.IdentitasIndexDTO;
import com.sales.dto.identitas.IdentitasIndexOptionDTO;

import java.util.List;

public interface IdentitasService {
    int getTotalPages(String filter, String search);
    List<IdentitasIndexDTO> getAll(int page, String filter, String search);
    IdentitasFormDTO getIdentitasById(Integer id);
    IdentitasDetailDTO getDetailIdentitasById(Integer id);
    void save(IdentitasFormDTO identitasFormDTO);
    void delete(int id);
    List<IdentitasIndexOptionDTO> getFilterAsItem();
    List<IdentitasIndexOptionDTO> getSearchItems(String filter);

}
