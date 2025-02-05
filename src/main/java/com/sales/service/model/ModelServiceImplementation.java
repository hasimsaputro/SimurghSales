package com.sales.service.model;

import com.sales.dto.OptionDTO;
import com.sales.dto.dataLeads.POTDataDTO;
import com.sales.dto.model.ModelDetailDTO;
import com.sales.dto.model.ModelFormDTO;
import com.sales.dto.model.ModelIndexDTO;
import com.sales.dto.tipe.TipeDetailDTO;
import com.sales.dto.tipe.TipeFormDTO;
import com.sales.dto.tipe.TipeIndexDTO;
import com.sales.dto.tipe.TipeJenisDTO;
import com.sales.entity.Model;
import com.sales.entity.POT;
import com.sales.entity.Tipe;
import com.sales.repository.KategoriRepository;
import com.sales.repository.MerkRepository;
import com.sales.repository.ModelRepository;
import com.sales.repository.TipeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class ModelServiceImplementation implements ModelService{
    private final ModelRepository repository;
    private final KategoriRepository kategoriRepository;
    private final MerkRepository merkRepository;
    private final TipeRepository tipeRepository;
    private final Integer rowInPage = 10;

    public ModelServiceImplementation(ModelRepository repository, KategoriRepository kategoriRepository, MerkRepository merkRepository, TipeRepository tipeRepository) {
        this.repository = repository;
        this.kategoriRepository = kategoriRepository;
        this.merkRepository = merkRepository;
        this.tipeRepository = tipeRepository;
    }

    @Override
    public List<ModelIndexDTO> getAll(String filter, String search, int page) {
        Pageable pagination = PageRequest.of(page-1,rowInPage, Sort.by("id"));
        List<Model> modelList = new LinkedList<>();
        if(filter.equals("null") || filter.isBlank()){
            modelList = repository.getAllModel(pagination);
        }else {
            switch (filter){
                case "id":
                    modelList = repository.getByIdModel(search, pagination);
                    break;
                case "namaKategori":
                    modelList = repository.getByKategoriName(search,pagination);
                    break;
                case "namaMerk":
                    modelList = repository.getByMerkName(search,pagination);
                    break;
                case "namaTipe":
                    modelList = repository.getByTipeName(search,pagination);
                    break;
                case "namaJenis":
                    modelList = repository.getByJenisName(search,pagination);
                    break;
                case "namaModel":
                    modelList = repository.getByModelName(search,pagination);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? "true" : "false";
                    modelList = repository.getByStatus(Boolean.parseBoolean(search),pagination);
                    break;

            }
        }
        var gridModel = new LinkedList<ModelIndexDTO>();
        for (var model :modelList) {
            if(model.getDeleteDate() == null){
                String id = String.valueOf(model.getId());
                String namaKategori = model.getKategoriModel().getNamaKategori();
                String namaMerk = model.getMerkModel().getNamaMerk();
                String namaTipe = model.getTipeModel().getNamaTipe();
                String namaJenis = model.getTipeModel().getJenisTipe().getNamaJenis();
                String namaModel = model.getNamaModel();
                String status = model.getStatus() ? "Aktif":"Tidak Akrif";
                ModelIndexDTO modelIndexDTO = new ModelIndexDTO();
                modelIndexDTO.setId(id);
                modelIndexDTO.setNamaMerk(namaMerk);
                modelIndexDTO.setNamaKategori(namaKategori);
                modelIndexDTO.setNamaTipe(namaTipe);
                modelIndexDTO.setNamaJenis(namaJenis);
                modelIndexDTO.setNamaModel(namaModel);
                modelIndexDTO.setStatus(status);
                gridModel.add(modelIndexDTO);
            }
        }
        return gridModel;
    }

    @Override
    public int getTotal(String filter, String search) {
        double totalRows = 0;
        if(filter.equals("null") || filter.isBlank()){
            totalRows = repository.countAll();
        }else {
            switch (filter){
                case "id":
                    totalRows = repository.countById(search);
                    break;
                case "namaKategori":
                    totalRows = repository.countByNamaKategori(search);
                    break;
                case "namaMerk":
                    totalRows = repository.countByNameMerk(search);
                    break;
                case "namaTipe":
                    totalRows = repository.countByNamaTipe(search);
                    break;
                case "namaJenis":
                    totalRows = repository.countByNamaJenis(search);
                    break;
                case "namaModel":
                    totalRows = repository.countByNamaModel(search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? "true" : "false";
                    totalRows = repository.countByStatus(search);
                    break;
            }
        }
        return (int)(Math.ceil(totalRows/rowInPage));
    }

    @Override
    public List<OptionDTO> getfilterAsItem() {
        return List.of(
                new OptionDTO("Kode Model", "id"),
                new OptionDTO("Kategori", "namaKategori"),
                new OptionDTO("Nama Merk", "namaMerk"),
                new OptionDTO("Nama Tipe", "namaTipe"),
                new OptionDTO("Nama Jenis", "namaJenis"),
                new OptionDTO("Nama Model", "namaModel"),
                new OptionDTO("Status", "status")
        );
    }

    @Override
    public ModelFormDTO getModelById(String modelId) {
        Model model = repository.getModelById(modelId);
        ModelFormDTO dto = new ModelFormDTO();
        if(model != null) {
            dto.setId(model.getId());
            dto.setIdKategori(model.getIdKategori());
            dto.setNamaKategori(model.getKategoriModel().getNamaKategori());
            dto.setIdMerk(model.getIdMerk());
            dto.setNamaMerk(model.getMerkModel().getNamaMerk());
            dto.setIdTipe(model.getIdTipe());
            dto.setNamaTipe(model.getTipeModel().getNamaTipe());
            dto.setIdJenis(model.getTipeModel().getIdJenis());
            dto.setNamaJenis(model.getTipeModel().getJenisTipe().getNamaJenis());
            dto.setNamaModel(model.getNamaModel());
            dto.setStatus(model.getStatus());
        }
        return dto;
    }

    @Override
    public void saveModel(ModelFormDTO dto) {
        Model model = new Model();
        model.setId(dto.getId());
        model.setIdKategori(dto.getIdKategori());
        model.setIdMerk(dto.getIdMerk());
        model.setIdTipe(dto.getIdTipe());
        model.setNamaModel(dto.getNamaModel());
        model.setStatus(dto.getStatus());
        repository.save(model);
    }

    @Override
    public ModelDetailDTO getModelByIdDetail(String modelId) {
        Model model = repository.findById(modelId).orElseThrow();
        ModelDetailDTO dto = new ModelDetailDTO();
        dto.setId(String.valueOf(model.getId()));
        dto.setNamaKategori(model.getKategoriModel().getNamaKategori());
        String namaMerk = model.getMerkModel().getNamaMerk();
        String idMerk = model.getIdMerk();
        dto.setMerk(idMerk+" - "+namaMerk);
        String namaTipe = model.getTipeModel().getNamaTipe();
        String idTipe = model.getIdTipe();
        dto.setTipe(idTipe+" - "+namaTipe);
        String namaJenis = model.getTipeModel().getJenisTipe().getNamaJenis();
        String idJenis = model.getTipeModel().getIdJenis();
        dto.setJenis(idJenis+" - "+namaJenis);
        dto.setNamaModel(model.getNamaModel());
        dto.setStatus(model.getStatus()? "Aktif":"Tidak Akrif");
        return dto;
    }

    @Override
    public void deleteModel(String modelId) {
        var model = repository.findById(modelId).orElseThrow();
        model.setDeleteDate(LocalDate.now());
        repository.save(model);
    }

    @Override
    public List<OptionDTO> getSearchItems(String filter) {
        List<String > searchItems = new LinkedList<>();
        if(!filter.isBlank()){
            switch (filter){
                case "id":
                    searchItems = repository.getItemsById();
                    break;
                case "namaKategori":
                    searchItems = repository.getItemsByNamaKategori();
                    break;
                case "namaMerk":
                    searchItems = repository.getItemsByNamaMerk();
                    break;
                case "namaTipe":
                    searchItems = repository.getItemsByNameTipe();
                    break;
                case "namaJenis":
                    searchItems = repository.getItemsByNamaJenis();
                    break;
                case "namaModel":
                    searchItems = repository.getItemsByName();
                    break;
                case "status":
                    searchItems = repository.getItemsByStatus();
                    break;
            }
        }
        List<OptionDTO> searchsItems = new LinkedList<>();
        for (var searchItem : searchItems){
            OptionDTO item = new OptionDTO(searchItem, searchItem);
            searchsItems.add(item);
        }
        return searchsItems;
    }

    @Override
    public List<OptionDTO> getKategoriItems() {
        var kategoriItem = kategoriRepository.getAllKategori();
        List<OptionDTO> kategoriList = new LinkedList<>();
        for (var kategori : kategoriItem){
            OptionDTO item = new OptionDTO(kategori.getNamaKategori(),String.valueOf(kategori.getId()));
            kategoriList.add(item);
        }
        return kategoriList;
    }

    @Override
    public List<OptionDTO> getMerkItems() {
        var merkItem = merkRepository.getAllMerk();
        List<OptionDTO> merkList = new LinkedList<>();
        for (var merk : merkItem){
            OptionDTO item = new OptionDTO(merk.getNamaMerk(),String.valueOf(merk.getId()));
            merkList.add(item);
        }
        return merkList;
    }

    @Override
    public List<OptionDTO> getTipeItems() {
        var tipeItem = tipeRepository.getAllTipe();
        List<OptionDTO> tipeList = new LinkedList<>();
        for (var tipe : tipeItem){
            OptionDTO item = new OptionDTO(tipe.getNamaTipe(),String.valueOf(tipe.getId()));
            tipeList.add(item);
        }
        return tipeList;
    }

    @Override
    public TipeJenisDTO getTipeJenis(String idTipe) {
        var tipeData = tipeRepository.findById(idTipe).orElseThrow();
        TipeJenisDTO tipeJenisDTO = new TipeJenisDTO();
        tipeJenisDTO.setId(tipeData.getId());
        tipeJenisDTO.setNamaTipe(tipeData.getNamaTipe());
        OptionDTO jenis = new OptionDTO();
        jenis.setText(tipeData.getJenisTipe().getNamaJenis());
        jenis.setValue(tipeData.getIdJenis());
        tipeJenisDTO.setJenis(jenis);
        return tipeJenisDTO;
    }
}
