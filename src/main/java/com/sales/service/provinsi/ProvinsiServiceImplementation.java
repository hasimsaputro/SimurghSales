package com.sales.service.provinsi;

import com.sales.dto.FilterIndexOptionDTO;
import com.sales.dto.kabupaten.KabupatenDetailDTO;
import com.sales.dto.kabupaten.KabupatenFormDTO;
import com.sales.dto.kabupaten.KabupatenIndexDTO;
import com.sales.dto.provinsi.ProvinsiDetailDTO;
import com.sales.dto.provinsi.ProvinsiFormDTO;
import com.sales.dto.provinsi.ProvinsiIndexDTO;
import com.sales.entity.Kabupaten;
import com.sales.entity.Provinsi;
import com.sales.repository.ProvinsiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProvinsiServiceImplementation implements ProvinsiService{
    private final ProvinsiRepository repository;
    private final int rowInPage = 1;

    @Autowired
    public ProvinsiServiceImplementation(ProvinsiRepository repository) {
        this.repository = repository;
    }

    @Override
    public int getTotalPages(String filter, String search) {
        double page = 0;
        if (filter.isEmpty()){
            page = repository.getTotalPages();
        } else {
            switch (filter){
                case "kode":
                    page = repository.getTotalPagesById(search);
                    break;
                case "provinsi":
                    page = repository.getTotalPagesByName(search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    page = repository.getTotalpagesByStatus(Boolean.parseBoolean(search));
                    break;
            }
        }
        return (int) Math.ceil(page/rowInPage);
    }

    @Override
    public List<ProvinsiIndexDTO> getAllProvinsi(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0){
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<Provinsi> provinsiList = new LinkedList<>();
        if (filter.isEmpty()){
            provinsiList = repository.getAllProvinsi(pageable);
        } else {
            switch (filter){
                case "kode":
                    provinsiList = repository.getProvinsiById(pageable, search);
                    break;
                case "provinsi":
                    provinsiList = repository.getProvinsiByName(pageable, search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    provinsiList = repository.getProvinsiByStatus(pageable, Boolean.parseBoolean(search));
                    break;
            }
        }

        List<ProvinsiIndexDTO> provinsiIndexDTOS = new LinkedList<>();
        for (Provinsi provinsi : provinsiList){
            ProvinsiIndexDTO provinsiIndexDTO = new ProvinsiIndexDTO();
            provinsiIndexDTO.setKode(provinsi.getId());
            provinsiIndexDTO.setProvinsi(provinsi.getNamaProvinsi());
            String statusKelurahan = !provinsi.getStatus() ? "Tidak Aktif" : "Aktif";
            provinsiIndexDTO.setStatus(statusKelurahan);

            provinsiIndexDTOS.add(provinsiIndexDTO);
        }
        return provinsiIndexDTOS;
    }

    @Override
    public ProvinsiDetailDTO getProvinsiDetailById(Integer id) {
        ProvinsiDetailDTO provinsiDetailDTO = new ProvinsiDetailDTO();
        if (id!=null) {
            try {
                Provinsi provinsi = repository.findById(id).orElseThrow();
                provinsiDetailDTO.setKode(provinsi.getId());
                provinsiDetailDTO.setProvinsi(provinsi.getNamaProvinsi());
                String statusKelurahan = provinsi.getStatus() ? "Aktif" : "Tidak Aktif";
                provinsiDetailDTO.setStatus(statusKelurahan);
            } catch (Exception ignored){}
        }
        return provinsiDetailDTO;
    }

    @Override
    public ProvinsiFormDTO getProvinsiFormById(Integer id) {
        ProvinsiFormDTO provinsiFormDTO = new ProvinsiFormDTO();
        if (id!=null){
            try {
                Provinsi provinsi = repository.findById(id).orElseThrow();
                provinsiFormDTO.setKode(provinsi.getId());
                provinsiFormDTO.setProvinsi(provinsi.getNamaProvinsi());
                provinsiFormDTO.setStatus(provinsi.getStatus());
            } catch (Exception ignored){}
        }
        return provinsiFormDTO;
    }

    @Override
    public void save(ProvinsiFormDTO provinsiFormDTO) {
        Provinsi provinsi = new Provinsi();
        provinsi.setId(provinsiFormDTO.getKode());
        provinsi.setNamaProvinsi(provinsiFormDTO.getProvinsi());
        provinsi.setStatus(provinsiFormDTO.getStatus());

        repository.save(provinsi);
    }

    @Override
    public void delete(Integer id) {
        if (id!=null){
            try {
                Provinsi provinsi = repository.findById(id).orElseThrow();
                provinsi.setDeleteDate(LocalDate.now());
                repository.save(provinsi);
            } catch (Exception ignored){}
        }
    }

    @Override
    public List<FilterIndexOptionDTO> getFilterAsItem() {
        return List.of(
                new FilterIndexOptionDTO("Kode Provinsi", "kode"),
                new FilterIndexOptionDTO("Nama Provinsi", "provinsi"),
                new FilterIndexOptionDTO("Status", "status")
        );
    }

    @Override
    public List<FilterIndexOptionDTO> getSearchItems(String filter) {
        List<String> searchItems = new LinkedList<>();
        if (!filter.isEmpty()){
            switch (filter){
                case "kode":
                    searchItems = repository.getProvinsiItemsById();
                    break;
                case "provinsi":
                    searchItems = repository.getProvinsiItemsByName();
                    break;
                case "status":
                    searchItems = repository.getProvinsiItemsByStatus();
                    break;
            }
        }
        List<FilterIndexOptionDTO> filterIndexOptionDTOS = new LinkedList<>();
        for (String searchItem : searchItems){
            FilterIndexOptionDTO filterIndexOptionDTO = new FilterIndexOptionDTO(searchItem, searchItem);
            filterIndexOptionDTOS.add(filterIndexOptionDTO);
        }
        return filterIndexOptionDTOS;
    }
}
