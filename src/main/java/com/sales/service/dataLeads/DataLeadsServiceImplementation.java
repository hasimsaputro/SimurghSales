package com.sales.service.dataLeads;

import com.sales.dto.dataLeads.DataLeadsFormDTO;
import com.sales.dto.dataLeads.DataLeadsIndexDTO;
import com.sales.entity.DataLeads;
import com.sales.repository.DataLeadsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DataLeadsServiceImplementation implements DataLeadsService{
    private final DataLeadsRepository repository;

    public DataLeadsServiceImplementation(DataLeadsRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<DataLeadsIndexDTO> getAll(String fullName, String search, int page) {
        return null;
    }

    @Override
    public void updateInsertDataLeads(DataLeadsFormDTO dataLeadsFormDTO) {
        DataLeads dataLeads = repository.getDataLeadsById(dataLeadsFormDTO.getId());
        Boolean isexist = dataLeads.getId() == null ? false : true;

        if(isexist == false){
            dataLeads = new DataLeads();
        }


        repository.save(dataLeads);
    }


}
