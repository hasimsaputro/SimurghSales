package com.sales.service.merk;

import com.sales.dto.OptionDTO;
import com.sales.dto.merk.MerkDetailDTO;
import com.sales.dto.merk.MerkFormDTO;
import com.sales.dto.merk.MerkIndexDTO;
import com.sales.entity.Merk;
import com.sales.repository.KategoriRepository;
import com.sales.repository.MerkRepository;
import com.sales.repository.NegaraRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class MerkServiceImplementation implements  MerkService{
    private final MerkRepository repository;
    private final KategoriRepository kategoriRepository;
    private final NegaraRepository negaraRepository;
    private final Integer rowInPage = 10;

    public MerkServiceImplementation(MerkRepository repository,KategoriRepository kategoriRepository,
                                     NegaraRepository negaraRepository) {
        this.repository = repository;
        this.kategoriRepository = kategoriRepository;
        this.negaraRepository = negaraRepository;
    }

    @Override
    public List<MerkIndexDTO> getAll(String filter, String search, int page) {
        Pageable pagination = PageRequest.of(page-1,rowInPage, Sort.by("id"));
        List<Merk> merkList = new LinkedList<>();
        if(filter.equals("null") || filter.isBlank()){
            merkList = repository.getAll(pagination);
        }else {
            switch (filter){
                case "id":
                    merkList = repository.getByIdMerk(search, pagination);
                    break;
                case "namaKategori":
                    merkList = repository.getByKategoriName(search,pagination);
                    break;
                case "namaMerk":
                    merkList = repository.getByName(search,pagination);
                    break;
                case "negaraManufaktur":
                    merkList = repository.getByNegara(search,pagination);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? "true" : "false";
                    merkList = repository.getByStatus(Boolean.parseBoolean(search),pagination);
                    break;

            }
        }
        var gridMerk = new LinkedList<MerkIndexDTO>();
        for (var merk :merkList) {
            if(merk.getDeleteDate() == null){
                String id = String.valueOf(merk.getId());
                String namaKategori = merk.getKategoriMerk().getNamaKategori();
                String namaMerk = merk.getNamaMerk();
                String namaNegara = merk.getNegara().getNamaNegara();
                String status = merk.getStatus() ? "Aktif":"Tidak Akrif";
                MerkIndexDTO merkIndexDTO = new MerkIndexDTO();
                merkIndexDTO.setId(id);
                merkIndexDTO.setNamaMerk(namaMerk);
                merkIndexDTO.setNamaKategori(namaKategori);
                merkIndexDTO.setNegaraManufaktur(namaNegara);
                merkIndexDTO.setStatus(status);
                gridMerk.add(merkIndexDTO);
            }
        }
        return gridMerk;
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
                case "negaraManufaktur":
                    totalRows = repository.countByNamaNegara(search);
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
                new OptionDTO("Kode Merk", "id"),
                new OptionDTO("Kategori", "namaKategori"),
                new OptionDTO("Nama Merk", "namaMerk"),
                new OptionDTO("Nama Kategori", "negaraManufaktur"),
                new OptionDTO("Status", "status")
        );
    }

    @Override
    public MerkFormDTO getMerkById(String merkId) {
        Merk merk = repository.getMerkById(merkId);
        MerkFormDTO dto = new MerkFormDTO();

        if(merk != null){
            dto.setId(merk.getId());
            dto.setNamaKategori(merk.getKategoriMerk().getNamaKategori());
            dto.setIdKategori(merk.getIdKategori());
            dto.setNamaMerk(merk.getNamaMerk());
            dto.setIdNegara(merk.getIdNegara());
            dto.setNamaNegara(merk.getNegara().getNamaNegara());
            dto.setStatus(merk.getStatus());
        }
        return dto;
    }

    @Override
    public void saveMerk(MerkFormDTO dto) {
        Merk merk = new Merk();
        merk.setId(dto.getId());
        merk.setIdKategori(dto.getIdKategori());
        merk.setNamaMerk(dto.getNamaMerk());
        merk.setIdNegara(dto.getIdNegara());
        merk.setStatus(dto.getStatus());
        repository.save(merk);
    }

    @Override
    public MerkDetailDTO getMerkByIdDetail(String merkId) {
        Merk merk = repository.getMerkById(merkId);
        MerkDetailDTO dto = new MerkDetailDTO();
        if(merk != null){
            dto.setId(String.valueOf(merk.getId()));
            dto.setNamaKategori(merk.getKategoriMerk().getNamaKategori());
            dto.setNamaMerk(merk.getNamaMerk());
            dto.setNegaraManufaktur(merk.getNegara().getNamaNegara());
            dto.setStatus(merk.getStatus()? "Aktif":"Tidak Akrif");
        }
        return dto;
    }

    @Override
    public void deleteMerk(String merkId) {
        var merk = repository.findById(merkId).orElseThrow();
        merk.setDeleteDate(LocalDate.now());
        repository.save(merk);
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
                case "negaraManufaktur":
                    searchItems = repository.getItemsByNegara();
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
    public List<OptionDTO> getNegaraItems() {
        var negaraItem = negaraRepository.getAllNegara();
        List<OptionDTO> negaraList = new LinkedList<>();
        for (var negara : negaraItem){
            OptionDTO item = new OptionDTO(negara.getNamaNegara(),String.valueOf(negara.getId()));
            negaraList.add(item);
        }
        return negaraList;
    }
}
