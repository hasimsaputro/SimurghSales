package com.sales.service.kecamatan;

import com.sales.dto.FilterIndexOptionDTO;
import com.sales.dto.FilterOptionDTO;
import com.sales.dto.kecamatan.KecamatanDetailDTO;
import com.sales.dto.kecamatan.KecamatanFormDTO;
import com.sales.dto.kecamatan.KecamatanIndexDTO;
import com.sales.dto.kelurahan.KelurahanFormDTO;
import com.sales.entity.Kabupaten;
import com.sales.entity.Kecamatan;
import com.sales.entity.Kelurahan;
import com.sales.entity.Provinsi;
import com.sales.repository.KabupatenRepository;
import com.sales.repository.KecamatanRepository;
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
public class KecamatanServiceImplementation implements KecamatanService{
    private final KecamatanRepository repository;
    private final KabupatenRepository kabupatenRepository;
    private final ProvinsiRepository provinsiRepository;
    private final int rowInPage = 1;

    @Autowired
    public KecamatanServiceImplementation(KecamatanRepository repository, KabupatenRepository kabupatenRepository, ProvinsiRepository provinsiRepository) {
        this.repository = repository;
        this.kabupatenRepository = kabupatenRepository;
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
                case "kabupaten":
                    page = repository.getTotalPagesByKabupaten(search);
                    break;
                case "kode":
                    page = repository.getTotalPagesById(search);
                    break;
                case "kecamatan":
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
    public List<KecamatanIndexDTO> getAllKecamatan(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0){
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<Kecamatan> kecamatanList = new LinkedList<>();
        if (filter.isEmpty()){
            kecamatanList = repository.getAllKecamatan(pageable);
        } else {
            switch (filter){
                case "provinsi":
                    kecamatanList = repository.getKecamatanByProvinsi(pageable, search);
                    break;
                case "kabupaten":
                    kecamatanList = repository.getKecamatanByKabupaten(pageable, search);
                    break;
                case "kode":
                    kecamatanList = repository.getKecamatanById(pageable, search);
                    break;
                case "kecamatan":
                    kecamatanList = repository.getKecamatanByName(pageable, search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    kecamatanList = repository.getKecamatanByStatus(pageable, Boolean.parseBoolean(search));
                    break;
            }
        }

        List<KecamatanIndexDTO> kecamatanIndexDTOS = new LinkedList<>();
        for (Kecamatan kecamatan : kecamatanList){
            KecamatanIndexDTO kecamatanIndexDTO = new KecamatanIndexDTO();
            kecamatanIndexDTO.setProvinsi(kecamatan.getProvinsi().getNamaProvinsi());
            kecamatanIndexDTO.setKabupaten(kecamatan.getKabupaten().getNamaKabupatenKota());
            kecamatanIndexDTO.setKode(kecamatan.getId());
            kecamatanIndexDTO.setKecamatan(kecamatan.getNamaKecamatan());
            String statusKelurahan = !kecamatan.getStatus() ? "Tidak Aktif" : "Aktif";
            kecamatanIndexDTO.setStatus(statusKelurahan);

            kecamatanIndexDTOS.add(kecamatanIndexDTO);
        }
        return kecamatanIndexDTOS;
    }

    @Override
    public KecamatanDetailDTO getKecamatanDetailById(Integer id) {
        KecamatanDetailDTO kecamatanDetailDTO = new KecamatanDetailDTO();
        if (id!=null) {
            try {
                Kecamatan kecamatan = repository.findById(id).orElseThrow();
                String detail = String.format("%s - %s", kecamatan.getProvinsi().getId(), kecamatan.getProvinsi().getNamaProvinsi());
                kecamatanDetailDTO.setProvinsi(detail);
                detail = String.format("%s - %s", kecamatan.getKabupaten().getId(), kecamatan.getKabupaten().getNamaKabupatenKota());
                kecamatanDetailDTO.setKabupaten(detail);
                kecamatanDetailDTO.setKode(kecamatan.getId());
                kecamatanDetailDTO.setKecamatan(kecamatan.getNamaKecamatan());
                String statusKelurahan = kecamatan  .getStatus() ? "Aktif" : "Tidak Aktif";
                kecamatanDetailDTO.setStatus(statusKelurahan);
            } catch (Exception ignored){}
        }
        return kecamatanDetailDTO;
    }

    @Override
    public KecamatanFormDTO getKecamatanFormById(Integer id) {
        KecamatanFormDTO kecamatanFormDTO = new KecamatanFormDTO();
        if (id!=null){
            try {
                Kecamatan kecamatan = repository.findById(id).orElseThrow();
                String getIdNameLocation = String.format("%s - %s", kecamatan.getProvinsi().getId(), kecamatan.getProvinsi().getNamaProvinsi());
                kecamatanFormDTO.setProvinsi(getIdNameLocation);
                getIdNameLocation = String.format("%s - %s", kecamatan.getKabupaten().getId(), kecamatan.getKabupaten().getNamaKabupatenKota());
                kecamatanFormDTO.setKabupaten(getIdNameLocation);
                kecamatanFormDTO.setKode(kecamatan.getId());
                kecamatanFormDTO.setKecamatan(kecamatan.getNamaKecamatan());
                kecamatanFormDTO.setStatus(kecamatan.getStatus());
            } catch (Exception ignored){}
        }
        return kecamatanFormDTO;
    }

    @Override
    public void save(KecamatanFormDTO kecamatanFormDTO) {
        Kecamatan kecamatan = new Kecamatan();
        kecamatan.setId(kecamatanFormDTO.getKode());
        Integer getIdLocation = Integer.parseInt(kecamatanFormDTO.getProvinsi().substring(0, kecamatanFormDTO.getProvinsi().indexOf(" - ")));
        kecamatan.setIdProvinsi(provinsiRepository.getProvinsiFormById(getIdLocation).getId());
        getIdLocation = Integer.parseInt(kecamatanFormDTO.getKabupaten().substring(0, kecamatanFormDTO.getKabupaten().indexOf(" - ")));
        kecamatan.setIdKabupaten(kabupatenRepository.getKabupatenFormById(getIdLocation).getId());
        kecamatan.setNamaKecamatan(kecamatanFormDTO.getKecamatan());
        kecamatan.setStatus(kecamatanFormDTO.getStatus());

        repository.save(kecamatan);
    }

    @Override
    public void delete(Integer id) {
        if (id!=null){
            try {
                Kecamatan kecamatan = repository.findById(id).orElseThrow();
                kecamatan.setDeleteDate(LocalDate.now());
                repository.save(kecamatan);
            } catch (Exception ignored){}
        }
    }

    @Override
    public List<FilterIndexOptionDTO> getFilterAsItem() {
        return List.of(
                new FilterIndexOptionDTO("Nama Provinsi", "provinsi"),
                new FilterIndexOptionDTO("Nama Kabupaten", "kabupaten"),
                new FilterIndexOptionDTO("Kode Kecamatan", "kode"),
                new FilterIndexOptionDTO("Nama Kecamatan", "kecamatan"),
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
                case "kode":
                    searchItems = repository.getKecamatanItemsById();
                    break;
                case "kecamatan":
                    searchItems = repository.getKecamatanItemsByName();
                    break;
                case "status":
                    searchItems = repository.getKecamatanItemsByStatus();
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
    public List<FilterOptionDTO> getKabupatenOption(Integer id) {
        List<Kabupaten> kabupatenList = kabupatenRepository.getAllKabupatenOption(id);
        List<FilterOptionDTO> filterOptionDTOS = new LinkedList<>();
        for (Kabupaten kabupaten : kabupatenList){
            FilterOptionDTO filterOptionDTO = new FilterOptionDTO();
            filterOptionDTO.setName(kabupaten.getNamaKabupatenKota());
            filterOptionDTO.setId(kabupaten.getId());
            filterOptionDTOS.add(filterOptionDTO);
        }
        return filterOptionDTOS;
    }
}
