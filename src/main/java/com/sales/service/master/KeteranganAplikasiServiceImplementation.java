package com.sales.service.master;

import com.sales.dto.OptionDTO;
import com.sales.dto.master.KeteranganAplikasiDTO;
import com.sales.dto.master.KeteranganAplikasiFormDTO;
import com.sales.dto.master.TipeAplikasiDTO;
import com.sales.entity.KeteranganAplikasi;
import com.sales.entity.TipeAplikasi;
import com.sales.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class KeteranganAplikasiServiceImplementation implements KeteranganAplikasiService {
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

    public KeteranganAplikasiServiceImplementation(ReferensiRepository referensiRepository, CabangRepository cabangRepository, KelurahanRepository kelurahanRepository, DebiturRepository debiturRepository, TipeAplikasiRepository tipeAplikasiRepository, ProdukRepository produkRepository, KeteranganAplikasiRepository keteranganAplikasiRepository, ModelRepository modelRepositoryl, TipeRepository tipeRepository, MerkRepository merkRepository, KategoriRepository kategoriRepository, MitraAgenRepository mitraAgenRepository, DataLeadsRepository repository, UserRepository userRepository) {
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
    public List<KeteranganAplikasiDTO> getAll(Integer id , String name, Boolean status, Integer page, String filter, String search) {
        if (filter.isBlank()){
        }else {
            switch (filter){
                case "id" :
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

        List<KeteranganAplikasi> keteranganAplikasis = keteranganAplikasiRepository.getAllBySearch(pagination, id, name, status);
        var gridTipeApl = new LinkedList<KeteranganAplikasiDTO>();
        for (var ket :keteranganAplikasis) {
            KeteranganAplikasiDTO dto = new KeteranganAplikasiDTO();
            dto.setId(ket.getId());
            dto.setName(ket.getNamaKeteranganAplikasi());
            dto.setStatus(ket.getStatus());
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

        Integer totalPage = (int) Math.ceil(((double)keteranganAplikasiRepository.countAllBySearch( id, name, status)) / rowInPage);

        return  totalPage;
    }

    @Override
    public KeteranganAplikasiDTO getKeteranganAplikasiById(Integer id) {
        KeteranganAplikasiDTO dto = new KeteranganAplikasiDTO();
        KeteranganAplikasi keteranganAplikasi = keteranganAplikasiRepository.getKeteranganAplikasiById(id);

        if(keteranganAplikasi != null){
            dto.setStatus(keteranganAplikasi.getStatus());
            dto.setName(keteranganAplikasi.getNamaKeteranganAplikasi());
            dto.setId(keteranganAplikasi.getId());
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
        dto2.setText("Nama Keterangan Aplikasi");
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
        List<KeteranganAplikasi> keteranganAplikasis = keteranganAplikasiRepository.findAll();
        if (filter.isBlank()){
        }else {
            switch (filter){
                case "id" :

                    for (var tipeAplikasi : keteranganAplikasis){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(tipeAplikasi.getId().toString());
                        dto.setText(tipeAplikasi.getId().toString());
                        searchItem.add(dto);
                    }
                    break;
                case  "namaTipeAplikasi":

                    for (var tipeAplikasi : keteranganAplikasis){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(tipeAplikasi.getNamaKeteranganAplikasi());
                        dto.setText(tipeAplikasi.getNamaKeteranganAplikasi());
                        searchItem.add(dto);
                    }
                    break;
                case "status" :

                    for (var tipeAplikasi : keteranganAplikasis){
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
    public void updateInsert(KeteranganAplikasiFormDTO keteranganAplikasiFormDTO) {
        KeteranganAplikasi keteranganAplikasi = new KeteranganAplikasi();
        keteranganAplikasi.setNamaKeteranganAplikasi(keteranganAplikasiFormDTO.getName());
        keteranganAplikasi.setId(keteranganAplikasiFormDTO.getId());
        keteranganAplikasi.setStatus(keteranganAplikasiFormDTO.getStatus());
        keteranganAplikasiRepository.save(keteranganAplikasi);
    }

    @Override
    public void delete(Integer id) {
        KeteranganAplikasi keteranganAplikasi = keteranganAplikasiRepository.getKeteranganAplikasiById(id);

        keteranganAplikasiRepository.delete(keteranganAplikasi);
    }


}
