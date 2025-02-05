package com.sales.service.referensi;

import com.sales.dto.OptionDTO;
import com.sales.dto.debitur.DebiturReferensiOptionDTO;
import com.sales.dto.referensi.ReferensiFormDTO;
import com.sales.dto.referensi.ReferensiIndexDTO;
import com.sales.entity.Debitur;
import com.sales.entity.Referensi;
import com.sales.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ReferensiServiceImplement implements ReferensiService {
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

    public ReferensiServiceImplement(ReferensiRepository referensiRepository, CabangRepository cabangRepository, KelurahanRepository kelurahanRepository, DebiturRepository debiturRepository, TipeAplikasiRepository tipeAplikasiRepository, ProdukRepository produkRepository, KeteranganAplikasiRepository keteranganAplikasiRepository, ModelRepository modelRepositoryl, TipeRepository tipeRepository, MerkRepository merkRepository, KategoriRepository kategoriRepository, MitraAgenRepository mitraAgenRepository, DataLeadsRepository repository, UserRepository userRepository) {
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
    public List<ReferensiIndexDTO> getAll(String id , String name, Boolean status, Integer page, String filter, String search) {
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
                case  "name":
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

        List<Referensi> referensiList = referensiRepository.getAllBySearch(pagination, id, name, status);
        var gridTipeApl = new LinkedList<ReferensiIndexDTO>();
        for (var tipeApl :referensiList) {
            ReferensiIndexDTO dto = new ReferensiIndexDTO();
            dto.setId(tipeApl.getId());
            dto.setName(tipeApl.getNamaDepan().concat(" ").concat(tipeApl.getNamaTengah()).concat(" ").concat(tipeApl.getNamaAkhir()));
            dto.setStatus(tipeApl.getStatus());
            gridTipeApl.add(dto);
        }
        return gridTipeApl;
    }

    @Override
    public Integer totalPage(String id, String name, Boolean status) {
        if (name != null){
            if(name.trim().isBlank()){
                name = null;
            }
        }

        Integer totalPage = (int) Math.ceil(((double)referensiRepository.countAllBySearch( id, name, status)) / rowInPage);

        return  totalPage;
    }

    @Override
    public ReferensiFormDTO getReferensiById(String id) {
        ReferensiFormDTO dto = new ReferensiFormDTO();
        Referensi referensi = referensiRepository.getReferensiById(id);

        if(referensi != null){
            dto.setStatus(referensi.getStatus());
            dto.setNamaAkhir(referensi.getNamaAkhir());
            dto.setNamaTengah(referensi.getNamaTengah());
            dto.setNamaDepan(referensi.getNamaDepan());
            dto.setId(referensi.getId());
        }

        return dto;
    }

    @Override
    public List<OptionDTO> filter() {
        OptionDTO dto1 = new OptionDTO();
        OptionDTO dto2 = new OptionDTO();
        OptionDTO dto3 = new OptionDTO();
        List<OptionDTO> dtoList = new LinkedList<>();
        dto1.setText("Id");
        dto1.setValue("id");
        dto2.setText("Nama Referensi");
        dto2.setValue("name");
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
        List<Referensi> referensiList = referensiRepository.findAll();
        if (filter.isBlank()){
        }else {
            switch (filter){
                case "id" :

                    for (var referensi : referensiList){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(referensi.getId());
                        dto.setText(referensi.getId());
                        searchItem.add(dto);
                    }
                    break;
                case  "name":

                    for (var referensi : referensiList){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(referensi.getNamaDepan().concat(" ").concat(referensi.getNamaTengah()).concat(" ").concat(referensi.getNamaAkhir()));
                        dto.setText(referensi.getNamaDepan().concat(" ").concat(referensi.getNamaTengah()).concat(" ").concat(referensi.getNamaAkhir()));
                        searchItem.add(dto);
                    }
                    break;
                case "status" :

                    for (var referensi : referensiList){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(referensi.getStatus().toString());
                        dto.setText(referensi.getStatus().toString());
                        searchItem.add(dto);
                    }
                    break;
            }
        }
        return searchItem;
    }

    @Override
    public List<DebiturReferensiOptionDTO> getAllDebitur() {
        List<Debitur> debiturList = debiturRepository.getDebiturReferensi();
        List<DebiturReferensiOptionDTO> optionDTOList = new LinkedList<>();

        for (var deb : debiturList){
            DebiturReferensiOptionDTO dto = new DebiturReferensiOptionDTO();
            dto.setText(deb.getNamaDepan().concat(" ").concat(deb.getNamaTengah().concat(" ").concat(deb.getNamaAkhir())));
            dto.setValue(deb.getId());
            dto.setNamaDepan(deb.getNamaDepan());
            dto.setNamaTengah(deb.getNamaTengah());
            dto.setNamaAkhir(deb.getNamaAkhir());
            optionDTOList.add(dto);
        }
        return optionDTOList;
    }

    @Override
    public void updateInsert(ReferensiFormDTO referensiFormDTO) {
        Referensi referensi = new Referensi();
        referensi.setNamaDepan(referensiFormDTO.getNamaDepan());
        referensi.setNamaTengah(referensiFormDTO.getNamaTengah());
        referensi.setNamaAkhir(referensiFormDTO.getNamaAkhir());
        referensi.setId(referensiFormDTO.getId());
        referensi.setStatus(referensiFormDTO.getStatus());
        referensiRepository.save(referensi);
    }

    @Override
    public void delete(String id) {
        Referensi referensi = referensiRepository.getReferensiById(id);
        referensiRepository.delete(referensi);
    }


}
