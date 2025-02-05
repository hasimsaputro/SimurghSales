package com.sales.service.kabupaten;

import com.sales.dto.FilterIndexOptionDTO;
import com.sales.dto.FilterOptionDTO;
import com.sales.dto.kabupaten.KabupatenDetailDTO;
import com.sales.dto.kabupaten.KabupatenFormDTO;
import com.sales.dto.kabupaten.KabupatenIndexDTO;
import com.sales.dto.kecamatan.KecamatanDetailDTO;
import com.sales.dto.kecamatan.KecamatanFormDTO;
import com.sales.dto.kecamatan.KecamatanIndexDTO;
import com.sales.entity.Kabupaten;
import com.sales.entity.Kecamatan;
import com.sales.entity.Provinsi;
import com.sales.repository.KabupatenRepository;
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
public class KabupatenServiceImplementation implements KabupatenService{
    private final KabupatenRepository repository;
    private final ProvinsiRepository provinsiRepository;
    private final int rowInPage = 1;

    @Autowired
    public KabupatenServiceImplementation(KabupatenRepository repository, ProvinsiRepository provinsiRepository) {
        this.repository = repository;
        this.provinsiRepository = provinsiRepository;
    }

    @Override
    public int getTotalPages(String filter, String search) {
        double page = 0;
        if (filter.isEmpty()){
            page = repository.getTotalPages();
        } else {
            switch (filter){
                case "provinsi":
                    page = repository.getTotalPagesByProvinsi(search);
                    break;
                case "kode":
                    page = repository.getTotalPagesById(search);
                    break;
                case "kabupaten":
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
    public List<KabupatenIndexDTO> getAllKabupaten(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0){
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<Kabupaten> kabupatenList = new LinkedList<>();
        if (filter.isEmpty()){
            kabupatenList = repository.getAllKabupaten(pageable);
        } else {
            switch (filter){
                case "provinsi":
                    kabupatenList = repository.getKabupatenByProvinsi(pageable, search);
                    break;
                case "kode":
                    kabupatenList = repository.getKabupatenById(pageable, search);
                    break;
                case "kabupaten":
                    kabupatenList = repository.getKabupatenByName(pageable, search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    kabupatenList = repository.getKabupatenByStatus(pageable, Boolean.parseBoolean(search));
                    break;
            }
        }

        List<KabupatenIndexDTO> kabupatenIndexDTOS = new LinkedList<>();
        for (Kabupaten kabupaten : kabupatenList){
            KabupatenIndexDTO kabupatenIndexDTO = new KabupatenIndexDTO();
            kabupatenIndexDTO.setProvinsi(kabupaten.getProvinsi().getNamaProvinsi());
            kabupatenIndexDTO.setKode(kabupaten.getId());
            kabupatenIndexDTO.setKabupaten(kabupaten.getNamaKabupatenKota());
            String statusKelurahan = !kabupaten.getStatus() ? "Tidak Aktif" : "Aktif";
            kabupatenIndexDTO.setStatus(statusKelurahan);

            kabupatenIndexDTOS.add(kabupatenIndexDTO);
        }
        return kabupatenIndexDTOS;
    }

    @Override
    public KabupatenDetailDTO getKabupatenDetailById(Integer id) {
        KabupatenDetailDTO kabupatenDetailDTO = new KabupatenDetailDTO();
        if (id!=null) {
            try {
                Kabupaten kabupaten = repository.findById(id).orElseThrow();
                String detail = String.format("%s - %s", kabupaten.getProvinsi().getId(), kabupaten.getProvinsi().getNamaProvinsi());
                kabupatenDetailDTO.setProvinsi(detail);
                kabupatenDetailDTO.setKode(kabupaten.getId());
                kabupatenDetailDTO.setKabupaten(kabupaten.getNamaKabupatenKota());
                String statusKelurahan = kabupaten.getStatus() ? "Aktif" : "Tidak Aktif";
                kabupatenDetailDTO.setStatus(statusKelurahan);
            } catch (Exception ignored){}
        }
        return kabupatenDetailDTO;
    }

    @Override
    public KabupatenFormDTO getKabupatenFormById(Integer id) {
        KabupatenFormDTO kabupatenFormDTO = new KabupatenFormDTO();
        if (id!=null){
            try {
                Kabupaten kabupaten = repository.findById(id).orElseThrow();
                String getIdNameLocation = String.format("%s - %s", kabupaten.getProvinsi().getId(), kabupaten.getProvinsi().getNamaProvinsi());
                kabupatenFormDTO.setProvinsi(getIdNameLocation);
                kabupatenFormDTO.setKode(kabupaten.getId());
                kabupatenFormDTO.setKabupaten(kabupaten.getNamaKabupatenKota());
                kabupatenFormDTO.setStatus(kabupaten.getStatus());
            } catch (Exception ignored){}
        }
        return kabupatenFormDTO;
    }

    @Override
    public void save(KabupatenFormDTO kabupatenFormDTO) {
        Kabupaten kabupaten = new Kabupaten();
        kabupaten.setId(kabupatenFormDTO.getKode());
        Integer getIdLocation = Integer.parseInt(kabupatenFormDTO.getProvinsi().substring(0, kabupatenFormDTO.getProvinsi().indexOf(" - ")));
        kabupaten.setIdProvinsi(provinsiRepository.getProvinsiFormById(getIdLocation).getId());
        kabupaten.setNamaKabupatenKota(kabupatenFormDTO.getKabupaten());
        kabupaten.setStatus(kabupatenFormDTO.getStatus());

        repository.save(kabupaten);
    }

    @Override
    public void delete(Integer id) {
        if (id!=null){
            try {
                Kabupaten kabupaten = repository.findById(id).orElseThrow();
                kabupaten.setDeleteDate(LocalDate.now());
                repository.save(kabupaten);
            } catch (Exception ignored){}
        }
    }

    @Override
    public List<FilterIndexOptionDTO> getFilterAsItem() {
        return List.of(
                new FilterIndexOptionDTO("Nama Provinsi", "provinsi"),
                new FilterIndexOptionDTO("Kode Kabupaten", "kode"),
                new FilterIndexOptionDTO("Nama Kabupaten", "kabupaten"),
                new FilterIndexOptionDTO("Status", "status")
        );
    }

    @Override
    public List<FilterIndexOptionDTO> getSearchItems(String filter) {
        List<String> searchItems = new LinkedList<>();
        if (!filter.isEmpty()){
            switch (filter){
                case "provinsi":
                    searchItems = provinsiRepository.getProvinsiItems();
                    break;
                case "kode":
                    searchItems = repository.getKabupatenItemsById();
                    break;
                case "kabupaten":
                    searchItems = repository.getKabupatenItemsByName();
                    break;
                case "status":
                    searchItems = repository.getKabupatenItemsByStatus();
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

    @Override
    public List<FilterOptionDTO> getProvinsiOption() {
        List<Provinsi> provinsiList = provinsiRepository.getAllProvinsiOption();
        List<FilterOptionDTO> filterOptionDTOS = new LinkedList<>();
        for (Provinsi provinsi : provinsiList){
            FilterOptionDTO filterOptionDTO = new FilterOptionDTO();
            filterOptionDTO.setName(provinsi.getNamaProvinsi());
            filterOptionDTO.setId(provinsi.getId());
            filterOptionDTOS.add(filterOptionDTO);
        }
        return filterOptionDTOS;
    }
}
