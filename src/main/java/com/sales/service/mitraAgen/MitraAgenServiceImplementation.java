package com.sales.service.mitraAgen;

import com.sales.dto.mitraAgen.MitraAgenFormDTO;
import com.sales.dto.mitraAgen.MitraAgenIndexDTO;
import com.sales.entity.MitraAgen;
import com.sales.repository.MitraAgenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MitraAgenServiceImplementation implements MitraAgenService{
    private final MitraAgenRepository repository;
    private final int rowInPage = 5;

    @Autowired
    public MitraAgenServiceImplementation(MitraAgenRepository repository) {
        this.repository = repository;
    }

    @Override
    public int getTotalPages(String id, Integer tipe, String name, Integer kelurahan, Integer cabang, Boolean status) {
        double total = repository.getTotalPages(id, tipe, name, kelurahan, cabang, status);
        return (int) Math.ceil(total/rowInPage);
    }

    @Override
    public List<MitraAgenIndexDTO> getAll(int page, String id, Integer tipe, String name, Integer kelurahan, Integer cabang, Boolean status) {
        int totalPages = getTotalPages(id, tipe, name, kelurahan, cabang, status);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0){
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));
        List<MitraAgen> mitraAgenList = repository.getAllMitraAgen(pageable, id, tipe, name, kelurahan, cabang, status);
        List<MitraAgenIndexDTO> mitraAgenIndexDTOS = new LinkedList<>();
        for (MitraAgen mitraAgen : mitraAgenList){
            MitraAgenIndexDTO mitraAgenIndexDTO = new MitraAgenIndexDTO();
            mitraAgenIndexDTO.setTipe(mitraAgen.getTipeMasterMitraAgen().getNamaTipeMaster());
            mitraAgenIndexDTO.setKode(mitraAgen.getId());
            mitraAgenIndexDTO.setNama(mitraAgen.getNamaMitraAgen());
            mitraAgenIndexDTO.setKelurahan(mitraAgen.getKelurahanDomisiliMitraAgen().getNamaKelurahan());
            mitraAgenIndexDTO.setCabang(mitraAgen.getCabangMitraAgen().getNamaCabang());
            String statusMitraAgen = mitraAgen.getStatus() == false ? "Tidak Aktif" : "Aktif";
            mitraAgenIndexDTO.setStatus(statusMitraAgen);
            mitraAgenIndexDTOS.add(mitraAgenIndexDTO);
        }
        return mitraAgenIndexDTOS;
    }

    @Override
    public MitraAgenFormDTO getMitraAgenById(String id) {
        return null;
    }

    @Override
    public void save(MitraAgenFormDTO mitraAgenFormDTO) {

    }

    @Override
    public void delete(String id) {

    }
}
