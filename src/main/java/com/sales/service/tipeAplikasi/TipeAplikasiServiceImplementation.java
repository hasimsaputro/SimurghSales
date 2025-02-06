package com.sales.service.tipeAplikasi;

import com.sales.dto.OptionDTO;
import com.sales.dto.tipeAplikasi.TipeAplikasiDTO;
import com.sales.entity.*;
import com.sales.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class TipeAplikasiServiceImplementation implements TipeAplikasiService {
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
    private final Integer rowInPage = 1;

    public TipeAplikasiServiceImplementation(ReferensiRepository referensiRepository, CabangRepository cabangRepository, KelurahanRepository kelurahanRepository, DebiturRepository debiturRepository, TipeAplikasiRepository tipeAplikasiRepository, ProdukRepository produkRepository, KeteranganAplikasiRepository keteranganAplikasiRepository, ModelRepository modelRepositoryl, TipeRepository tipeRepository, MerkRepository merkRepository, KategoriRepository kategoriRepository, MitraAgenRepository mitraAgenRepository, DataLeadsRepository repository, UserRepository userRepository) {
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
    public List<TipeAplikasiDTO> getAll(Integer id ,String name,Boolean status, Integer page,String filter, String search) {
        if (filter.isBlank()){
        }else {
            switch (filter){
                case "kodeTipeAplikasi" :
                    try{
                        id = Integer.parseInt(search);
                    }catch (Exception exception){
                        id = null;
                    }

                    break;
                case  "namaTipeAplikasi":
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

        List<TipeAplikasi> tipeAplikasiList = tipeAplikasiRepository.getAllBySearch(pagination, id, name, status);
        var gridTipeApl = new LinkedList<TipeAplikasiDTO>();
        for (var tipeApl :tipeAplikasiList) {
            TipeAplikasiDTO dto = new TipeAplikasiDTO();
            dto.setKodeTipeAplikasi(tipeApl.getId());
            dto.setNamaTipeAplikasi(tipeApl.getNamaTipeAplikasi());
            dto.setStatus(tipeApl.getStatus());
            gridTipeApl.add(dto);
        }
        return gridTipeApl;
    }

    @Override
    public Integer totalPage(Integer id, String name, Boolean status) {
        if (name != null){
            if(name.trim().isBlank()){
                name = null;
            }
        }

        Integer totalPage = (int) Math.ceil(((double)tipeAplikasiRepository.countAllBySearch( id, name, status)) / rowInPage);

        return  totalPage;
    }

    @Override
    public TipeAplikasiDTO getTipeAplikasiById(Integer id) {
        TipeAplikasiDTO dto = new TipeAplikasiDTO();
        TipeAplikasi tipeAplikasi = tipeAplikasiRepository.getTipeAplikasiById(id);

        if(tipeAplikasi != null){
            dto.setStatus(tipeAplikasi.getStatus());
            dto.setKodeTipeAplikasi(tipeAplikasi.getId());
            dto.setNamaTipeAplikasi(tipeAplikasi.getNamaTipeAplikasi());
        }

        return dto;
    }

    @Override
    public List<OptionDTO> filter() {
        OptionDTO dto1 = new OptionDTO();
        OptionDTO dto2 = new OptionDTO();
        OptionDTO dto3 = new OptionDTO();
        List<OptionDTO> dtoList = new LinkedList<>();
        dto1.setText("Kode Tipe Aplikasi");
        dto1.setValue("kodeTipeAplikasi");
        dto2.setText("Nama Tipe Aplikasi");
        dto2.setValue("namaTipeAplikasi");
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
        List<TipeAplikasi> tipeAplikasiList = tipeAplikasiRepository.findAll();
        if (filter.isBlank()){
        }else {
            switch (filter){
                case "kodeTipeAplikasi" :

                    for (var tipeAplikasi : tipeAplikasiList){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(tipeAplikasi.getId().toString());
                        dto.setText(tipeAplikasi.getId().toString());
                        searchItem.add(dto);
                    }
                    break;
                case  "namaTipeAplikasi":

                    for (var tipeAplikasi : tipeAplikasiList){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(tipeAplikasi.getNamaTipeAplikasi());
                        dto.setText(tipeAplikasi.getNamaTipeAplikasi());
                        searchItem.add(dto);
                    }
                    break;
                case "status" :

                    for (var tipeAplikasi : tipeAplikasiList){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(tipeAplikasi.getStatus().toString());
                        dto.setText(tipeAplikasi.getStatus().toString());
                        searchItem.add(dto);
                    }
                    break;
            }
        }
        return searchItem;
    }

    @Override
    public void updateInsert(TipeAplikasiDTO tipeAplikasiDTO) {
        TipeAplikasi tipeAplikasi = new TipeAplikasi();
        tipeAplikasi.setNamaTipeAplikasi(tipeAplikasiDTO.getNamaTipeAplikasi());
        tipeAplikasi.setId(tipeAplikasiDTO.getKodeTipeAplikasi());
        tipeAplikasi.setStatus(tipeAplikasiDTO.getStatus());
        tipeAplikasiRepository.save(tipeAplikasi);
    }

    @Override
    public void delete(Integer id) {
        TipeAplikasi tipeAplikasi = tipeAplikasiRepository.getTipeAplikasiById(id);
        tipeAplikasi.setDeleteDate(LocalDate.now());
        tipeAplikasiRepository.save(tipeAplikasi);
    }


}
