package com.sales.service.tipeMaster;

import com.sales.dto.tipeMaster.TipeMasterDetailDTO;
import com.sales.dto.tipeMaster.TipeMasterFormDTO;
import com.sales.dto.tipeMaster.TipeMasterIndexDTO;
import com.sales.dto.tipeMaster.TipeMasterIndexOptionDTO;
import com.sales.entity.TipeMaster;
import com.sales.repository.TipeMasterRepository;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class TipeMasterServiceImplementation implements TipeMasterService{

    private final TipeMasterRepository repository;
    private final int rowInPage = 1;

    public TipeMasterServiceImplementation(TipeMasterRepository repository) {
        this.repository = repository;
    }

    @Override
    public int getTotalPages(String filter, String search) {
        double page = 0;
        if (filter.isEmpty()) {
            page = repository.getTotalPages();
        } else {
            switch (filter) {
                case "id":
                    page = repository.getTotalPagesById(search);
                    break;
                case "namaTipeMaster":
                    page = repository.getTotalpagesByName(search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    page = repository.getTotalPagesByStatus(Boolean.parseBoolean(search));
                    break;
            }
        }
        return (int) Math.ceil(page / rowInPage);
    }

    @Override
    public List<TipeMasterIndexDTO> getAll(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0) {
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<TipeMaster> tipeMasterList = new LinkedList<>();
        if (filter.isEmpty()) {
            tipeMasterList = repository.getAllTipeMaster(pageable);
        } else {
            switch (filter) {
                case "id":
                    tipeMasterList = repository.getTipeMasterById(pageable, search);
                    break;
                case "namaTipeMaster":
                    tipeMasterList = repository.getTipeMasterByName(pageable, search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    tipeMasterList = repository.getTipeMasterByStatus(pageable, Boolean.parseBoolean(search));
                    break;
            }
        }

        List<TipeMasterIndexDTO> tipeMasterIndexDTOS = new LinkedList<>();
        for (TipeMaster tipeMaster : tipeMasterList) {
            TipeMasterIndexDTO tipeMasterIndexDTO = new TipeMasterIndexDTO();
            tipeMasterIndexDTO.setKodeTipeMaster(tipeMaster.getId());
            tipeMasterIndexDTO.setNamaTipeMaster(tipeMaster.getNamaTipeMaster());
            String statusTipeMaster = !tipeMaster.getStatus() ? "Tidak Aktif" : "Aktif";
            tipeMasterIndexDTO.setStatus(statusTipeMaster);

            tipeMasterIndexDTOS.add(tipeMasterIndexDTO);
        }
        return tipeMasterIndexDTOS;

    }

    @Override
    public TipeMasterFormDTO getTipeMasterById(Integer id) {
        TipeMasterFormDTO tipeMasterFormDTO = new TipeMasterFormDTO();
        if (id != null) {
            try {
                TipeMaster tipeMaster = repository.findById(id).orElseThrow();
                tipeMasterFormDTO.setId(tipeMaster.getId());
                tipeMasterFormDTO.setNamaTipeMaster(tipeMaster.getNamaTipeMaster());
                tipeMasterFormDTO.setStatus(tipeMaster.getStatus());
                return tipeMasterFormDTO;
            } catch (Exception ignored) { }
        }
        return tipeMasterFormDTO;
    }

    @Override
    public TipeMasterDetailDTO getDetailTipeMasterById(Integer id) {
        TipeMasterDetailDTO tipeMasterDetailDTO = new TipeMasterDetailDTO();
        if (id != null) {
            try {
                TipeMaster tipeMaster = repository.findById(id).orElseThrow();
                tipeMasterDetailDTO.setKodeTipeMaster(String.valueOf(tipeMaster.getId()));
                tipeMasterDetailDTO.setNamaTipeMaster(tipeMaster.getNamaTipeMaster());
                String statusTipeMaster = !tipeMaster.getStatus() ? "Tidak Aktif" : "Aktif";
                tipeMasterDetailDTO.setStatus(statusTipeMaster);
            } catch (Exception ignored) { }
        }
        return tipeMasterDetailDTO;

    }

    @Override
    public void save(TipeMasterFormDTO tipeMasterFormDTO) {
        TipeMaster tipeMaster = new TipeMaster();
        tipeMaster.setId(tipeMasterFormDTO.getId());
        tipeMaster.setNamaTipeMaster(tipeMasterFormDTO.getNamaTipeMaster());
        tipeMaster.setStatus(tipeMasterFormDTO.getStatus());
        repository.save(tipeMaster);
    }

    @Override
    public void delete(int id) {
        try {
            TipeMaster tipeMaster = repository.findById(id).orElseThrow();
            tipeMaster.setDeleteDate(LocalDate.now());
            repository.save(tipeMaster);
        } catch (Exception ignored) { }
    }

    @Override
    public List<TipeMasterIndexOptionDTO> getFilterAsItem() {
        return List.of(
                new TipeMasterIndexOptionDTO("Kode TipeMaster", "id"),
                new TipeMasterIndexOptionDTO("Nama TipeMaster", "namaTipeMaster"),
                new TipeMasterIndexOptionDTO("Status", "status")
        );
    }

    @Override
    public List<TipeMasterIndexOptionDTO> getSearchItems(String filter) {
        List<String> searchItems = new LinkedList<>();
        if (!filter.isEmpty()) {
            switch (filter) {
                case "id":
                    searchItems = repository.getTipeMasterItemsById();
                    break;
                case "namaTipeMaster":
                    searchItems = repository.getTipeMasterItemsByName();
                    break;
                case "status":
                    searchItems = repository.getTipeMasterItemsByStatus();
                    break;
            }
        }

        List<TipeMasterIndexOptionDTO> tipeMasterIndexOptionDTOS = new LinkedList<>();
        for (String searchItem : searchItems) {
            TipeMasterIndexOptionDTO tipeMasterIndexOptionDTO = new TipeMasterIndexOptionDTO(searchItem, searchItem);
            tipeMasterIndexOptionDTOS.add(tipeMasterIndexOptionDTO);
        }
        return tipeMasterIndexOptionDTOS;
    }
}
