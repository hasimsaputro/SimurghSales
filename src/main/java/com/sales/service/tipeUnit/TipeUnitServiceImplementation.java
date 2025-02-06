package com.sales.service.tipeUnit;

import com.sales.dto.OptionDTO;
import com.sales.dto.tipeAplikasi.TipeAplikasiDTO;
import com.sales.dto.tipeAplikasi.TipeAplikasiFormDTO;
import com.sales.dto.tipeUnit.TipeUnitDTO;
import com.sales.dto.tipeUnit.TipeUnitFormDTO;
import com.sales.entity.TipeAplikasi;
import com.sales.entity.TipeUnit;
import com.sales.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class TipeUnitServiceImplementation implements TipeUnitService {
    private final TipeUnitRepository tipeUnitRepository;
    private final ReferensiRepository referensiRepository;
    private final CabangRepository cabangRepository;
    private final KelurahanRepository kelurahanRepository;
    private final DebiturRepository debiturRepository;
    private final TipeAplikasiRepository tipeAplikasiRepository;
    private final ProdukRepository produkRepository;
    private final KeteranganAplikasiRepository keteranganAplikasiRepository;
    private final ModelRepository modelRepositoryl;
    private final TipeRepository tipeRepository;
    private final MerkRepository merkRepository;
    private final KategoriRepository kategoriRepository;
    private final MitraAgenRepository mitraAgenRepository;
    private final DataLeadsRepository repository;
    private final UserRepository userRepository;
    private final Integer rowInPage = 5;

    public TipeUnitServiceImplementation(TipeUnitRepository tipeUnitRepository, ReferensiRepository referensiRepository, CabangRepository cabangRepository, KelurahanRepository kelurahanRepository, DebiturRepository debiturRepository, TipeAplikasiRepository tipeAplikasiRepository, ProdukRepository produkRepository, KeteranganAplikasiRepository keteranganAplikasiRepository, ModelRepository modelRepositoryl, TipeRepository tipeRepository, MerkRepository merkRepository, KategoriRepository kategoriRepository, MitraAgenRepository mitraAgenRepository, DataLeadsRepository repository, UserRepository userRepository) {
        this.tipeUnitRepository = tipeUnitRepository;
        this.referensiRepository = referensiRepository;
        this.cabangRepository = cabangRepository;
        this.kelurahanRepository = kelurahanRepository;
        this.debiturRepository = debiturRepository;
        this.tipeAplikasiRepository = tipeAplikasiRepository;
        this.produkRepository = produkRepository;
        this.keteranganAplikasiRepository = keteranganAplikasiRepository;
        this.modelRepositoryl = modelRepositoryl;
        this.tipeRepository = tipeRepository;
        this.merkRepository = merkRepository;
        this.kategoriRepository = kategoriRepository;
        this.mitraAgenRepository = mitraAgenRepository;
        this.repository = repository;
        this.userRepository = userRepository;
    }


    @Override
    public List<TipeUnitDTO> getAll(String id , String name, Boolean status, Integer page, String filter, String search) {
        if (filter.isBlank()){
        }else {
            switch (filter){
                case "id" :
                    try{
                        id = search;
                    }catch (Exception exception){
                        id = null;
                    }

                    break;
                case  "namaUnit":
                    name = search;
                    break;
                case "status" :
                    try{
                        status = Boolean.getBoolean(search);
                    }catch (Exception exception){
                        status = null;
                    }

                    break;
            }
        }


        Pageable pagination = PageRequest.of(page-1,rowInPage, Sort.by("id"));
        if(name != null){
            if(name.trim().isBlank()){
                name = null;
            }
        }

        List<TipeUnit> tipeAplikasiList = tipeUnitRepository.getAllBySearch(pagination, id, name, status);
        var gridTipeUnit = new LinkedList<TipeUnitDTO>();
        for (var tipeUnit :tipeAplikasiList) {
            TipeUnitDTO dto = new TipeUnitDTO();
            dto.setNamaUnit(tipeUnit.getNamaUnit());
            dto.setId(tipeUnit.getId());
            dto.setStatus(tipeUnit.getStatus());
            gridTipeUnit.add(dto);
        }
        return gridTipeUnit;
    }

    @Override
    public Integer totalPage(String id, String name, Boolean status,String filter, String search) {
        if (filter.isBlank()){
        }else {
            switch (filter){
                case "id" :
                    try{
                        id = search;
                    }catch (Exception exception){
                        id = null;
                    }

                    break;
                case  "namaUnit":
                    name = search;
                    break;
                case "status" :
                    try{
                        status = Boolean.getBoolean(search);
                    }catch (Exception exception){
                        status = null;
                    }

                    break;
            }
        }
        String hasil = tipeUnitRepository.countAllBySearch( id, name, status).toString();

        Integer totalPage = (int) Math.ceil(((double)tipeUnitRepository.countAllBySearch( id, name, status)) / rowInPage);

        return  totalPage;
    }

    @Override
    public TipeUnitFormDTO getUnitById(String id) {
        TipeUnitFormDTO dto = new TipeUnitFormDTO();
        TipeUnit  tipeUnit = tipeUnitRepository.getTipeUnitById(id);

        if(tipeUnit != null){
            dto.setStatus(tipeUnit.getStatus());
            dto.setId(tipeUnit.getId());
            dto.setNamaUnit(tipeUnit.getNamaUnit());
        }

        return dto;
    }

    @Override
    public List<OptionDTO> filter() {
        OptionDTO dto1 = new OptionDTO();
        OptionDTO dto2 = new OptionDTO();
        OptionDTO dto3 = new OptionDTO();
        List<OptionDTO> dtoList = new LinkedList<>();
        dto1.setText("ID");
        dto1.setValue("id");
        dto2.setText("Nama Unit");
        dto2.setValue("namaUnit");
        dto3.setText("Status");
        dto3.setValue("status");
        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);
        return dtoList;
    }

    @Override
    public List<OptionDTO> getSearchFilter(String filter) {
        List<OptionDTO> searchItem = new LinkedList<>();
        List<TipeUnit> tipeUnits = tipeUnitRepository.findAll();
        if (filter.isBlank()){
        }else {
            switch (filter){
                case "id" :

                    for (var tipeUnit : tipeUnits){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(tipeUnit.getId());
                        dto.setText(tipeUnit.getId());
                        searchItem.add(dto);
                    }
                    break;
                case  "namaUnit":

                    for (var tipeUnit : tipeUnits){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(tipeUnit.getNamaUnit());
                        dto.setText(tipeUnit.getNamaUnit());
                        searchItem.add(dto);
                    }
                    break;
                case "status" :

                    for (var tipeUnit : tipeUnits){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(tipeUnit.getStatus().toString());
                        dto.setText(tipeUnit.getStatus().toString());
                        searchItem.add(dto);
                    }
                    break;
            }
        }
        return searchItem;
    }

    @Override
    public void updateInsert(TipeUnitFormDTO tipeUnitFormDTO) {
        TipeUnit tipeUnit = new TipeUnit();
        if(tipeUnitFormDTO.getId().isBlank()){
            if (tipeUnitRepository.findAll().size() == 0) {
                String newId = "TU001";
                tipeUnit.setId(newId);
            } else {
                List<TipeUnit> tipeUnits = tipeUnitRepository.findAll();
                TipeUnit lastTipeUnit = tipeUnits.get(tipeUnits.size() - 1);
                String lastId = lastTipeUnit.getId();
                int lastNumber = Integer.parseInt(lastId.substring(2));
                String newId = String.format("TU%03d", lastNumber + 1);
                tipeUnit.setId(newId);
            }

        }else {
            tipeUnit.setId(tipeUnitFormDTO.getId());
        }
        tipeUnit.setNamaUnit(tipeUnitFormDTO.getNamaUnit());
        tipeUnit.setStatus(tipeUnitFormDTO.getStatus());
        tipeUnitRepository.save(tipeUnit);
    }

    @Override
    public void delete(String id) {
        TipeUnit tipeUnit = tipeUnitRepository.getTipeUnitById(id);
        tipeUnit.setDeleteDate(LocalDate.now());
        tipeUnitRepository.save(tipeUnit);
    }


}
