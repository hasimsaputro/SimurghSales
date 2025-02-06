package com.sales.service.wilayahHargaPasar;

import com.sales.dto.OptionDTO;
import com.sales.dto.wilayahHargaPasar.CabangWilayahDTO;
import com.sales.dto.wilayahHargaPasar.WilayahHargaPasarDetailDTO;
import com.sales.dto.FilterIndexOptionDTO;
import com.sales.dto.wilayahHargaPasar.WilayahHargaPasarFormDTO;
import com.sales.entity.Cabang;
import com.sales.dto.wilayahHargaPasar.WilayahHargaPasarIndexDTO;
import com.sales.entity.WilayahHargaPasar;
import com.sales.repository.CabangRepository;
import com.sales.repository.KategoriRepository;
import com.sales.repository.WilayahHargaPasarRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class WilayahHargaPasarServiceImplementation implements WilayahHargaPasarService{
    private final WilayahHargaPasarRepository repository;
    private final CabangRepository cabangRepository;
    private final KategoriRepository kategoriRepository;
    private final int rowInPage = 4;

    public WilayahHargaPasarServiceImplementation(WilayahHargaPasarRepository wilayahHargaPasarRepository, CabangRepository cabangRepository,
                                                  KategoriRepository kategoriRepository) {
        this.repository = wilayahHargaPasarRepository;
        this.cabangRepository = cabangRepository;
        this.kategoriRepository = kategoriRepository;
    }

    @Override
    public int getTotalPages(String filter, String search) {
        double page = 0;
        if (filter.isEmpty()){
            page = repository.getTotalPages();
        } else {
            switch (filter){
                case "kategori":
                    page = repository.getTotalPagesByKategori(search);
                    break;
                case "kode":
                    page = repository.getTotalPagesById(search);
                    break;
                case "wilayah":
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
    public List<WilayahHargaPasarIndexDTO> getAllWilayahHargaPasar(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0){
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<WilayahHargaPasar> wilayahHargaPasarList = new LinkedList<>();
        if (filter.isEmpty()){
            wilayahHargaPasarList = repository.getAllWilayahHargaPasar(pageable);
        } else {
            switch (filter){
                case "kategori":
                    wilayahHargaPasarList = repository.getWilayahHargaPasarByKategori(pageable, search);
                    break;
                case "kode":
                    wilayahHargaPasarList = repository.getWilayahHargaPasarById(pageable, search);
                    break;
                case "wilayah":
                    wilayahHargaPasarList = repository.getWilayahHargaPasarByName(pageable, search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    wilayahHargaPasarList = repository.getWilayahHargaPasarByStatus(pageable, Boolean.parseBoolean(search));
                    break;
            }
        }

        List<WilayahHargaPasarIndexDTO> wilayahHargaPasarIndexDTOS = new LinkedList<>();
        for (WilayahHargaPasar wilayahHargaPasar : wilayahHargaPasarList){
            WilayahHargaPasarIndexDTO wilayahHargaPasarIndexDTO = new WilayahHargaPasarIndexDTO();
            wilayahHargaPasarIndexDTO.setKategori(wilayahHargaPasar.getKategoriWilayah().getNamaKategori());
            wilayahHargaPasarIndexDTO.setKode(wilayahHargaPasar.getId());
            wilayahHargaPasarIndexDTO.setWilayahHargaPasar(wilayahHargaPasar.getNamaWilayah());
            String statusWilayahHargaPasar = !wilayahHargaPasar.getStatus() ? "Tidak Aktif" : "Aktif";
            wilayahHargaPasarIndexDTO.setStatus(statusWilayahHargaPasar);

            wilayahHargaPasarIndexDTOS.add(wilayahHargaPasarIndexDTO);
        }
        return wilayahHargaPasarIndexDTOS;
    }


    @Override
    public void deleteWilayah(String wilayahId) {
        if (!wilayahId.isEmpty()){
            try {
                WilayahHargaPasar wilayahHargaPasar = repository.findById(wilayahId).orElseThrow();
                wilayahHargaPasar.setDeleteDate(LocalDate.now());
                repository.save(wilayahHargaPasar);
            } catch (Exception ignored){}
        }
    }

    @Override
    public List<FilterIndexOptionDTO> getFilterAsItem() {
        return List.of(
                new FilterIndexOptionDTO("Nama Kategori", "kategori"),
                new FilterIndexOptionDTO("Kode Wilayah Harga", "kode"),
                new FilterIndexOptionDTO("Nama Wilayah Harga", "wilayah"),
                new FilterIndexOptionDTO("Status", "status")
        );
    }

    @Override
    public List<FilterIndexOptionDTO> getSearchItems(String filter) {
        List<String> searchItems = new LinkedList<>();
        if (!filter.isEmpty()){
            switch (filter){
                case "kategori":
                    searchItems = kategoriRepository.getItemsNamaKategori();
                    break;
                case "kode":
                    searchItems = repository.getWilayahHargaPasarItemsById();
                    break;
                case "wilayah":
                    searchItems = repository.getWilayahHargaPasarItemsByName();
                    break;
                case "status":
                    searchItems = repository.getWilayahHargaPasarItemsByStatus();
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
public WilayahHargaPasarFormDTO getWilayahById(String wilayahId) {
    WilayahHargaPasarFormDTO wilayahHargaPasarFormDTO = new WilayahHargaPasarFormDTO();
    wilayahId = wilayahId == null ? "" : wilayahId;
    if(!wilayahId.isBlank()) {
        var wilayah = repository.findById(wilayahId).orElseThrow();
        wilayahHargaPasarFormDTO.setId(wilayah.getId());
        wilayahHargaPasarFormDTO.setNamaWilayah(wilayah.getNamaWilayah());
        wilayahHargaPasarFormDTO.setIdKategori(wilayah.getIdKategori());
        wilayahHargaPasarFormDTO.setNamaKategori(wilayah.getKategoriWilayah().getNamaKategori());
        wilayahHargaPasarFormDTO.setStatus(wilayah.getStatus());

    }
    return wilayahHargaPasarFormDTO;
}

@Override
public List<CabangWilayahDTO> getCabangByWilayahId(String wilayahId) {
    var wilayah = repository.findById(wilayahId).orElseThrow();
    List<CabangWilayahDTO> cabangWilayahDTOList = new LinkedList<>();
    for (Cabang cabangList: wilayah.getCabangSet()){
        CabangWilayahDTO cabang = new CabangWilayahDTO();
        cabang.setId(String.valueOf(cabangList.getId()));
        cabang.setNamaCabang(cabangList.getNamaCabang());
        cabangWilayahDTOList.add(cabang);
    }
    return cabangWilayahDTOList;
}

    @Override
    public WilayahHargaPasarDetailDTO getWilayahDetailById(String wilayahId) {
        var wilayahHargaPasar = repository.findById(wilayahId).orElseThrow();
        WilayahHargaPasarDetailDTO dto = new WilayahHargaPasarDetailDTO();
        dto.setId(wilayahHargaPasar.getId());
        dto.setNamaWilayah(wilayahHargaPasar.getNamaWilayah());
        dto.setNamaKategori(wilayahHargaPasar.getKategoriWilayah().getNamaKategori());
        dto.setStatus(wilayahHargaPasar.getStatus()?"Aktif":"Tidak Aktif");
        return dto;
    }

    @Override
    public void saveWilayah(WilayahHargaPasarFormDTO dto) {
        WilayahHargaPasar wilayahHargaPasar = new WilayahHargaPasar();
        wilayahHargaPasar.setId(dto.getId());
        wilayahHargaPasar.setNamaWilayah(dto.getNamaWilayah());
        wilayahHargaPasar.setIdKategori(dto.getIdKategori());
        wilayahHargaPasar.setStatus(dto.getStatus());
        Set<Cabang> cabangSet = new HashSet<>();
        var cabangList = dto.getCabangList();
        for (Integer kodeCabang : cabangList) {
            Cabang cabang = cabangRepository.findById(kodeCabang).orElseThrow();
            cabangSet.add(cabang);
            wilayahHargaPasar.setCabangSet(cabangSet);
        }
        repository.save(wilayahHargaPasar);
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

}
