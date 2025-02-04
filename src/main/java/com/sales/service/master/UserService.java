package com.sales.service.master;

import com.sales.dto.OptionDTO;
import com.sales.dto.master.KeteranganAplikasiDTO;
import com.sales.dto.master.KeteranganAplikasiFormDTO;
import com.sales.dto.master.UserDTO;
import com.sales.dto.master.UserFormDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAll(String nik , String name,String email, String jabatan, Boolean status, Integer page, String filter, String search);
    Integer totalPage(String nik ,String name,String email,String jabatan,Boolean status);
    UserFormDTO getUserByNik(String nik);
    List<OptionDTO> filter();
    List<OptionDTO> getSearchFilter(String filter);


    public void updateInsert(UserFormDTO userFormDTO);
    public void delete(String nik);

}

