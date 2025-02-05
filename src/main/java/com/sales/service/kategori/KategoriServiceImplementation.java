package com.sales.service.kategori;

import com.sales.dto.OptionDTO;
import com.sales.dto.kategori.KategoriDetailDTO;
import com.sales.dto.kategori.KategoriFormDTO;
import com.sales.dto.kategori.KategoriIndexDTO;
import com.sales.entity.Kategori;
import com.sales.repository.KategoriRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class KategoriServiceImplementation implements KategoriService {
    private final KategoriRepository repository;
    private final Integer rowInPage = 10;

    public KategoriServiceImplementation(KategoriRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<KategoriIndexDTO> getAll(String filter, String search, int page) {
        Pageable pagination = PageRequest.of(page-1,rowInPage, Sort.by("id"));
        List<Kategori> kategoriList = new LinkedList<>();
        if(filter.equals("null") || filter.isBlank()){
            kategoriList = repository.getAll(pagination);
        }else {
            switch (filter){
                case "id":
                    kategoriList = repository.getByIdKategori(search, pagination);
                    break;
                case "namaKategori":
                    kategoriList = repository.getByName(search,pagination);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? "true" : "false";
                    kategoriList = repository.getByStatus(Boolean.parseBoolean(search),pagination);
                    break;

            }
        }
        var gridKategori = new LinkedList<KategoriIndexDTO>();
        for (var kategori :kategoriList) {
            if(kategori.getDeleteDate() == null){
                String id = String.valueOf(kategori.getId());
                String namaKategori = kategori.getNamaKategori();
                String status = kategori.getStatus() ? "Aktif":"Tidak Akrif";
                KategoriIndexDTO kategoriIndexDTO = new KategoriIndexDTO();
                kategoriIndexDTO.setId(id);
                kategoriIndexDTO.setNamaKategori(namaKategori);
                kategoriIndexDTO.setStatus(status);
                gridKategori.add(kategoriIndexDTO);
            }
        }
        return gridKategori;
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
                new OptionDTO("Kode Kategori", "id"),
                new OptionDTO("Nama Kategori", "namaKategori"),
                new OptionDTO("Status", "status")
        );
    }

    @Override
    public KategoriFormDTO getKategoriById(Integer kategoriId) {
        Kategori kategori = repository.getKategoriById(kategoriId);
        KategoriFormDTO dto = new KategoriFormDTO();

        if(kategori != null){
            dto.setId(kategori.getId());
            dto.setNamaKategori(kategori.getNamaKategori());
            dto.setStatus(kategori.getStatus());
        }

        return dto;
    }

    @Override
    public void saveKategori(KategoriFormDTO dto) {
        Kategori kategori = new Kategori();
        if(dto.getId() != null){
            kategori.setId(dto.getId());
        }
        kategori.setNamaKategori(dto.getNamaKategori());
        kategori.setStatus(dto.getStatus());
        repository.save(kategori);
    }

    @Override
    public KategoriDetailDTO getKategoriByIdDetail(Integer kategoriId) {
        Kategori kategori = repository.getKategoriById(kategoriId);
        KategoriDetailDTO dto = new KategoriDetailDTO();
        if(kategori != null){
            dto.setId(String.valueOf(kategori.getId()));
            dto.setNamaKategori(kategori.getNamaKategori());
            dto.setStatus(kategori.getStatus()? "Aktif":"Tidak Akrif");
        }
        return dto;
    }

    @Override
    public void deleteKategori(Integer kategoriId) {
        var kategori = repository.findById(kategoriId).orElseThrow();
        kategori.setDeleteDate(LocalDate.now());
        repository.save(kategori);
    }

    @Override
    public List<OptionDTO> getSearchItems(String filter) {
        List<String > searchItems = new LinkedList<>();
        if(!filter.isBlank()){
            switch (filter){
                case "id":
                    searchItems = repository.getItemsId();
                    break;
                case "namaKategori":
                    searchItems = repository.getItemsNamaKategori();
                    break;
                case "status":
                    searchItems = repository.getItemsStatus();
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
}
