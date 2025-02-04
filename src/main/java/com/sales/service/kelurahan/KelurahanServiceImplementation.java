package com.sales.service.kelurahan;

import com.sales.dto.FilterIndexOptionDTO;
import com.sales.dto.FilterOptionDTO;
import com.sales.dto.kelurahan.*;
import com.sales.entity.Kabupaten;
import com.sales.entity.Kecamatan;
import com.sales.entity.Kelurahan;
import com.sales.entity.Provinsi;
import com.sales.repository.KabupatenRepository;
import com.sales.repository.KecamatanRepository;
import com.sales.repository.KelurahanRepository;
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
public class KelurahanServiceImplementation implements KelurahanService{
    private final KelurahanRepository repository;
    private final ProvinsiRepository provinsiRepository;
    private final KabupatenRepository kabupatenRepository;
    private final KecamatanRepository kecamatanRepository;
    private final int rowInPage = 1;

    @Autowired
    public KelurahanServiceImplementation(KelurahanRepository repository, ProvinsiRepository provinsiRepository, KabupatenRepository kabupatenRepository, KecamatanRepository kecamatanRepository) {
        this.repository = repository;
        this.provinsiRepository = provinsiRepository;
        this.kabupatenRepository = kabupatenRepository;
        this.kecamatanRepository = kecamatanRepository;
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
                case "kabupaten":
                    page = repository.getTotalPagesByKabupaten(search);
                    break;
                case "kecamatan":
                    page = repository.getTotalPagesByKecamatan(search);
                    break;
                case "kode":
                    page = repository.getTotalPagesById(search);
                    break;
                case "kelurahan":
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
    public List<KelurahanIndexDTO> getAllKelurahan(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0){
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<Kelurahan> kelurahanList = new LinkedList<>();
        if (filter.isEmpty()){
            kelurahanList = repository.getAllKelurahan(pageable);
        } else {
            switch (filter){
                case "provinsi":
                    kelurahanList = repository.getKelurahanByProvinsi(pageable, search);
                    break;
                case "kabupaten":
                    kelurahanList = repository.getKelurahanByKabupaten(pageable, search);
                    break;
                case "kecamatan":
                    kelurahanList = repository.getKelurahanByKecamatan(pageable, search);
                    break;
                case "kode":
                    kelurahanList = repository.getKelurahanById(pageable, search);
                    break;
                case "kelurahan":
                    kelurahanList = repository.getKelurahanByName(pageable, search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    kelurahanList = repository.getKelurahanByStatus(pageable, Boolean.parseBoolean(search));
                    break;
            }
        }

        List<KelurahanIndexDTO> kelurahanIndexDTOS = new LinkedList<>();
        for (Kelurahan kelurahan : kelurahanList){
            KelurahanIndexDTO kelurahanIndexDTO = new KelurahanIndexDTO();
            kelurahanIndexDTO.setProvinsi(kelurahan.getProvinsi().getNamaProvinsi());
            kelurahanIndexDTO.setKabupaten(kelurahan.getKabupaten().getNamaKabupatenKota());
            kelurahanIndexDTO.setKecamatan(kelurahan.getKecamatan().getNamaKecamatan());
            kelurahanIndexDTO.setKode(kelurahan.getId());
            kelurahanIndexDTO.setKelurahan(kelurahan.getNamaKelurahan());
            String statusKelurahan = !kelurahan.getStatus() ? "Tidak Aktif" : "Aktif";
            kelurahanIndexDTO.setStatus(statusKelurahan);

            kelurahanIndexDTOS.add(kelurahanIndexDTO);
        }
        return kelurahanIndexDTOS;
    }

    @Override
    public KelurahanDetailDTO getKelurahanDetailById(Integer id) {
        KelurahanDetailDTO kelurahanDetailDTO = new KelurahanDetailDTO();
        if (id!=null) {
            try {
                Kelurahan kelurahan = repository.findById(id).orElseThrow();
                String detail = String.format("%s - %s", kelurahan.getProvinsi().getId(), kelurahan.getProvinsi().getNamaProvinsi());
                kelurahanDetailDTO.setProvinsi(detail);
                detail = String.format("%s - %s", kelurahan.getKabupaten().getId(), kelurahan.getKabupaten().getNamaKabupatenKota());
                kelurahanDetailDTO.setKabupaten(detail);
                detail = String.format("%s - %s", kelurahan.getKecamatan().getId(), kelurahan.getKecamatan().getNamaKecamatan());
                kelurahanDetailDTO.setKecamatan(detail);
                kelurahanDetailDTO.setKode(kelurahan.getId());
                kelurahanDetailDTO.setKelurahan(kelurahan.getNamaKelurahan());
                kelurahanDetailDTO.setKodePos(kelurahan.getKodePos());
                String statusKelurahan = kelurahan.getStatus() ? "Aktif" : "Tidak Aktif";
                kelurahanDetailDTO.setStatus(statusKelurahan);
            } catch (Exception ignored){}
        }
        return kelurahanDetailDTO;
    }

    @Override
    public KelurahanFormDTO getKelurahanFormById(Integer id) {
        KelurahanFormDTO kelurahanFormDTO = new KelurahanFormDTO();
        if (id!=null){
            try {
                Kelurahan kelurahan = repository.findById(id).orElseThrow();
                kelurahanFormDTO.setProvinsi(kelurahan.getProvinsi().getNamaProvinsi());
                kelurahanFormDTO.setKabupaten(kelurahan.getKabupaten().getNamaKabupatenKota());
                kelurahanFormDTO.setKecamatan(kelurahan.getKecamatan().getNamaKecamatan());
                kelurahanFormDTO.setKode(kelurahan.getId());
                kelurahanFormDTO.setKelurahan(kelurahan.getNamaKelurahan());
                kelurahanFormDTO.setKodePos(kelurahan.getKodePos());
                kelurahanFormDTO.setStatus(kelurahan.getStatus());
            } catch (Exception ignored){}
        }
        return kelurahanFormDTO;
    }

    @Override
    public void save(KelurahanFormDTO kelurahanFormDTO) {
        Kelurahan kelurahan = new Kelurahan();
        kelurahan.setId(kelurahanFormDTO.getKode());
        kelurahan.setIdProvinsi(provinsiRepository.getProvinsiFormByName(kelurahanFormDTO.getProvinsi()).getId());
        kelurahan.setIdKabupaten(kabupatenRepository.getKabupatenFormByName(kelurahanFormDTO.getKabupaten()).getId());
        kelurahan.setIdKecamatan(kecamatanRepository.getKecamatanFormByName(kelurahanFormDTO.getKecamatan()).getId());
        kelurahan.setNamaKelurahan(kelurahanFormDTO.getKelurahan());
        kelurahan.setKodePos(kelurahanFormDTO.getKodePos());
        kelurahan.setStatus(kelurahanFormDTO.getStatus());

        repository.save(kelurahan);
    }

    @Override
    public void delete(Integer id) {
        if (id!=null){
            try {
                Kelurahan kelurahan = repository.findById(id).orElseThrow();
                kelurahan.setDeleteDate(LocalDate.now());
                repository.save(kelurahan);
            } catch (Exception ignored){}
        }
    }

    @Override
    public List<FilterIndexOptionDTO> getFilterAsItem() {
        return List.of(
                new FilterIndexOptionDTO("Nama Provinsi", "provinsi"),
                new FilterIndexOptionDTO("Nama Kabupaten", "kabupaten"),
                new FilterIndexOptionDTO("Nama Kecamatan", "kecamatan"),
                new FilterIndexOptionDTO("Kode Kelurahan", "kode"),
                new FilterIndexOptionDTO("Nama Kelurahan", "kelurahan"),
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
                case "kabupaten":
                    searchItems = kabupatenRepository.getkabupatenItems();
                    break;
                case "kecamatan":
                    searchItems = kecamatanRepository.getKecamatanItems();
                    break;
                case "kode":
                    searchItems = repository.getKelurahanItemsById();
                    break;
                case "kelurahan":
                    searchItems = repository.getKelurahanItemsByName();
                    break;
                case "status":
                    searchItems = repository.getKelurahanItemsByStatus();
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

    @Override
    public List<FilterOptionDTO> getKabupatenOption() {
        List<Kabupaten> kabupatenList = kabupatenRepository.getAllKabupatenOption();
        List<FilterOptionDTO> filterOptionDTOS = new LinkedList<>();
        for (Kabupaten kabupaten : kabupatenList){
            FilterOptionDTO filterOptionDTO = new FilterOptionDTO();
            filterOptionDTO.setName(kabupaten.getNamaKabupatenKota());
            filterOptionDTO.setId(kabupaten.getId());
            filterOptionDTOS.add(filterOptionDTO);
        }
        return filterOptionDTOS;
    }

    @Override
    public List<FilterOptionDTO> getKecamatanOption() {
        List<Kecamatan> kecamatanList    = kecamatanRepository.getAllKecamatanOption();
        List<FilterOptionDTO> filterOptionDTOS = new LinkedList<>();
        for (Kecamatan kecamatan : kecamatanList){
            FilterOptionDTO filterOptionDTO = new FilterOptionDTO();
            filterOptionDTO.setName(kecamatan.getNamaKecamatan());
            filterOptionDTO.setId(kecamatan.getId());
            filterOptionDTOS.add(filterOptionDTO);
        }
        return filterOptionDTOS;
    }
}
