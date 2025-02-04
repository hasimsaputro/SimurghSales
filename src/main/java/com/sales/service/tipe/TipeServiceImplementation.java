package com.sales.service.tipe;

import com.sales.dto.OptionDTO;
import com.sales.dto.tipe.TipeDetailDTO;
import com.sales.dto.tipe.TipeFormDTO;
import com.sales.dto.tipe.TipeIndexDTO;
import com.sales.entity.Tipe;
import com.sales.repository.JenisRepository;
import com.sales.repository.KategoriRepository;
import com.sales.repository.MerkRepository;
import com.sales.repository.TipeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class TipeServiceImplementation implements TipeService{
    private final TipeRepository repository;
    private final KategoriRepository kategoriRepository;
    private final MerkRepository merkRepository;
    private final JenisRepository jenisRepository;
    private final Integer rowInPage = 10;

    public TipeServiceImplementation(TipeRepository repository, KategoriRepository kategoriRepository, JenisRepository jenisRepository, MerkRepository merkRepository) {
        this.repository = repository;
        this.kategoriRepository = kategoriRepository;
        this.merkRepository = merkRepository;
        this.jenisRepository = jenisRepository;
    }

    @Override
    public List<TipeIndexDTO> getAll(String filter, String search, int page) {
        Pageable pagination = PageRequest.of(page-1,rowInPage, Sort.by("id"));
        List<Tipe> tipeList = new LinkedList<>();
        if(filter.equals("null") || filter.isBlank()){
            tipeList = repository.getAllTipe(pagination);
        }else {
            switch (filter){
                case "id":
                    tipeList = repository.getByIdTipe(search, pagination);
                    break;
                case "namaKategori":
                    tipeList = repository.getByKategoriName(search,pagination);
                    break;
                case "namaMerk":
                    tipeList = repository.getByMerkName(search,pagination);
                    break;
                case "namaTipe":
                    tipeList = repository.getByName(search,pagination);
                    break;
                case "namaJenis":
                    tipeList = repository.getByJenisName(search,pagination);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? "true" : "false";
                    tipeList = repository.getByStatus(Boolean.parseBoolean(search),pagination);
                    break;

            }
        }
        var gridTipe = new LinkedList<TipeIndexDTO>();
        for (var tipe :tipeList) {
            if(tipe.getDeleteDate() == null){
                String id = String.valueOf(tipe.getId());
                String namaKategori = tipe.getKategoriTipe().getNamaKategori();
                String namaMerk = tipe.getMerkTipe().getNamaMerk();
                String namaTipe = tipe.getNamaTipe();
                String namaJenis = tipe.getJenisTipe().getNamaJenis();
                String status = tipe.getStatus() ? "Aktif":"Tidak Akrif";
                TipeIndexDTO tipeIndexDTO = new TipeIndexDTO();
                tipeIndexDTO.setId(id);
                tipeIndexDTO.setNamaMerk(namaMerk);
                tipeIndexDTO.setNamaKategori(namaKategori);
                tipeIndexDTO.setNamaTipe(namaTipe);
                tipeIndexDTO.setNamaJenis(namaJenis);
                tipeIndexDTO.setStatus(status);
                gridTipe.add(tipeIndexDTO);
            }
        }
        return gridTipe;
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
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? "true" : "false";
                    totalRows = repository.countByStatus(Boolean.parseBoolean(search));
                    break;
            }
        }
        return (int)(Math.ceil(totalRows/rowInPage));
    }

    @Override
    public List<OptionDTO> getfilterAsItem() {
        return List.of(
                new OptionDTO("Kode Tipe", "id"),
                new OptionDTO("Kategori", "namaKategori"),
                new OptionDTO("Nama Merk", "namaMerk"),
                new OptionDTO("Nama Tipe", "namaTipe"),
                new OptionDTO("Nama Jenis", "namaJenis"),
                new OptionDTO("Status", "status")
        );
    }

    @Override
    public TipeFormDTO getTipeById(String tipeId) {
        Tipe tipe = repository.getTipeById(tipeId);
        TipeFormDTO dto = new TipeFormDTO();
        if(tipe != null){
            dto.setId(tipe.getId());
            dto.setIdKategori(tipe.getIdKategori());
            dto.setNamaKategori(tipe.getKategoriTipe().getNamaKategori());
            dto.setIdMerk(tipe.getIdMerk());
            dto.setNamaMerk(tipe.getMerkTipe().getNamaMerk());
            dto.setNamaTipe(tipe.getNamaTipe());
            dto.setIdJenis(tipe.getIdJenis());
            dto.setNamaJenis(tipe.getJenisTipe().getNamaJenis());
            dto.setStatus(tipe.getStatus());
        }
        return dto;
    }

    @Override
    public void saveTipe(TipeFormDTO dto) {
        Tipe tipe = new Tipe();
        tipe.setId(dto.getId());
        tipe.setIdKategori(dto.getIdKategori());
        tipe.setIdMerk(dto.getIdMerk());
        tipe.setNamaTipe(dto.getNamaTipe());
        tipe.setIdJenis(dto.getIdJenis());
        tipe.setStatus(dto.getStatus());
        repository.save(tipe);
    }

    @Override
    public TipeDetailDTO getTipeByIdDetail(String tipeId) {
        Tipe tipe = repository.findById(tipeId).orElseThrow();
        TipeDetailDTO dto = new TipeDetailDTO();
        dto.setId(String.valueOf(tipe.getId()));
        dto.setNamaKategori(tipe.getKategoriTipe().getNamaKategori());
        dto.setNamaMerk(tipe.getMerkTipe().getNamaMerk());
        dto.setNamaTipe(tipe.getNamaTipe());
        dto.setNamaJenis(tipe.getJenisTipe().getNamaJenis());
        dto.setStatus(tipe.getStatus()? "Aktif":"Tidak Akrif");
        return dto;
    }

    @Override
    public void deleteTipe(String tipeId) {
        var tipe = repository.findById(tipeId).orElseThrow();
        tipe.setDeleteDate(LocalDate.now());
        repository.save(tipe);
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
                    searchItems = repository.getItemsByName();
                    break;
                case "namaJenis":
                    searchItems = repository.getItemsByNamaJenis();
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
    public List<OptionDTO> getJenisItems() {
        var jenisItem = jenisRepository.getAllJenis();
        List<OptionDTO> jenisList = new LinkedList<>();
        for (var jenis : jenisItem){
            OptionDTO item = new OptionDTO(jenis.getNamaJenis(),String.valueOf(jenis.getId()));
            jenisList.add(item);
        }
        return jenisList;
    }
}
