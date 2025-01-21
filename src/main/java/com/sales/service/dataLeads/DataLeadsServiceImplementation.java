package com.sales.service.dataLeads;

import com.sales.dto.OptionDTO;
import com.sales.dto.dataLeads.DataLeadsFormDTO;
import com.sales.dto.dataLeads.DataLeadsIndexDTO;
import com.sales.entity.DataLeads;
import com.sales.repository.DataLeadsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
@Service
public class DataLeadsServiceImplementation implements DataLeadsService{
    private final DataLeadsRepository repository;
    private final Integer rowInPage = 10;

    public DataLeadsServiceImplementation(DataLeadsRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<DataLeadsIndexDTO> getAll(String filter, String search, int page) {
        Pageable pagination = PageRequest.of(page-1,rowInPage, Sort.by("dataLeadsId"));
        List<DataLeads> dataLeads = new LinkedList<>();
        if(filter.equalsIgnoreCase("Nomor Data Leads")){
            dataLeads = repository.getByIdDataLeads(search, pagination);
        }else if (filter.equalsIgnoreCase("Nomor Aplikasi")){
            dataLeads = repository.getByNomorAplikasi(search, pagination);
        }else if (filter.equalsIgnoreCase("Nama Debitur")){
            dataLeads = repository.getByDebitur(search, pagination);
        }else if (filter.equalsIgnoreCase("Tipe Aplikasi")){
            dataLeads = repository.getByTipeAplikasi(search, pagination);
        }else if (filter.equalsIgnoreCase("Keterangan")){
            dataLeads = repository.getByKeterangan(search, pagination);
        }else if (filter.equalsIgnoreCase("Status")){
            dataLeads = repository.getByStatus(search, pagination);
        }
        var gridDataLeads = new LinkedList<DataLeadsIndexDTO>();
        for (var datalead :dataLeads) {
            String dataleadId = datalead.getId();
            String nomorAplikasi = datalead.getNomorAplikasi();
            String namaDebitur = datalead.getDebiturDataLeads().getNamaDepan()+" "+datalead.getDebiturDataLeads().getNamaTengah()+" "+datalead.getDebiturDataLeads().getNamaAkhir();
            String tipeAplikasi = datalead.getTipeAplikasiDataLeads().getNamaTipeAplikasi();
            String keterangan = datalead.getKeteranganAplikasiDataLeads().getNamaKeteranganAplikasi();
            String status = datalead.getStatus() ? "Aktif":"Tidak Akrif";
            DataLeadsIndexDTO dataLeadsIndexDTO = new DataLeadsIndexDTO();
            dataLeadsIndexDTO.setNomorDataLeads(dataleadId);
            dataLeadsIndexDTO.setNomorAplikasi(nomorAplikasi);
            dataLeadsIndexDTO.setNamaDebitur(namaDebitur);
            dataLeadsIndexDTO.setTipeAplikasi(tipeAplikasi);
            dataLeadsIndexDTO.setKeterangan(keterangan);
            dataLeadsIndexDTO.setStatus(status);
            gridDataLeads.add(dataLeadsIndexDTO);
        }
        return null;
    }

    @Override
    public int getTotal(String filter, String search) {
        return 0;
    }

    @Override
    public List<OptionDTO> getfilterAsItem() {
        return List.of();
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
