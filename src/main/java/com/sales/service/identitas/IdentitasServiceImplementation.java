package com.sales.service.identitas;

import com.sales.dto.identitas.IdentitasDetailDTO;
import com.sales.dto.identitas.IdentitasFormDTO;
import com.sales.dto.identitas.IdentitasIndexDTO;
import com.sales.dto.identitas.IdentitasIndexOptionDTO;
import com.sales.entity.Identitas;
import com.sales.repository.IdentitasRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class IdentitasServiceImplementation implements IdentitasService{
    private final IdentitasRepository repository;
    private final int rowInPage = 4;

    public IdentitasServiceImplementation(IdentitasRepository repository) {
        this.repository = repository;
    }

    @Override
    public int getTotalPages(String filter, String search) {
        double page = 0;
        if(filter.isEmpty()){
            page = repository.getTotalPages();
        } else {
            switch (filter){
                case "id":
                    page = repository.getTotalPagesById(search);
                    break;
                case "namaIdentitas":
                    page = repository.getTotalpagesByName(search);
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
    public List<IdentitasIndexDTO> getAll(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0){
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<Identitas> identitasList = new LinkedList<>();
        if (filter.isEmpty()){
            identitasList = repository.getAllIdentitas(pageable);
        } else {
            switch (filter){
                case "id":
                    identitasList = repository.getIdentitasById(pageable, search);
                    break;
                case "namaIdentitas":
                    identitasList = repository.getIdentitasByName(pageable, search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    identitasList = repository.getIdentitasByStatus(pageable, Boolean.parseBoolean(search));
                    break;
            }
        }

        List<IdentitasIndexDTO> identitasIndexDTOS = new LinkedList<>();
        for (Identitas identitas : identitasList){
            IdentitasIndexDTO identitasIndexDTO = new IdentitasIndexDTO();
            identitasIndexDTO.setKodeIdentitas(identitas.getId());
            identitasIndexDTO.setNamaIdentitas(identitas.getNamaIdentitas());
            String statusIdentitas = !identitas.getStatus() ? "Tidak Aktif" : "Aktif";
            identitasIndexDTO.setStatus(statusIdentitas);

            identitasIndexDTOS.add(identitasIndexDTO);
        }
        return identitasIndexDTOS;
    }

    @Override
    public IdentitasFormDTO getIdentitasById(Integer id) {
        IdentitasFormDTO identitasFormDTO = new IdentitasFormDTO();
        if(id != null){
            try{
                Identitas identitas = repository.findById(id).orElseThrow();
                identitasFormDTO.setId(identitas.getId());
                identitasFormDTO.setNamaIdentitas(identitas.getNamaIdentitas());
                identitasFormDTO.setStatus(identitas.getStatus());
                return identitasFormDTO;
            } catch (Exception ignored){ }
        }
        return identitasFormDTO;
    }

    @Override
    public IdentitasDetailDTO getDetailIdentitasById(Integer id) {
        IdentitasDetailDTO identitasDetailDTO = new IdentitasDetailDTO();
        if(id != null ){
            try {
                Identitas identitas = repository.findById(id).orElseThrow();
                identitasDetailDTO.setKodeIdentitas(String.valueOf(identitas.getId()));
                identitasDetailDTO.setNamaIdentitas(identitas.getNamaIdentitas());
                String statusIdentitas = !identitas.getStatus() ? "Tidak Aktif" : "Aktif";
                identitasDetailDTO.setStatus(statusIdentitas);
            } catch (Exception ignored){}
        }
        return identitasDetailDTO;
    }

    @Override
    public void save(IdentitasFormDTO identitasFormDTO) {
        Identitas identitas = new Identitas();
        identitas.setId(identitasFormDTO.getId());
        identitas.setNamaIdentitas(identitasFormDTO.getNamaIdentitas());
        identitas.setStatus(identitasFormDTO.getStatus());
        repository.save(identitas);
    }

    @Override
    public void delete(int id) {
        try{
            Identitas identitas = repository.findById(id).orElseThrow();
            identitas.setDeleteDate(LocalDate.now());
            repository.save(identitas);
        } catch (Exception ignored){}
    }

    @Override
    public List<IdentitasIndexOptionDTO> getFilterAsItem() {
        return List.of(
                new IdentitasIndexOptionDTO("Kode Identitas", "id"),
                new IdentitasIndexOptionDTO("Nama Identitas", "namaIdentitas"),
                new IdentitasIndexOptionDTO("Status", "status")
        );
    }

    @Override
    public List<IdentitasIndexOptionDTO> getSearchItems(String filter) {
        List<String> searchItems = new LinkedList<>();
        if (!filter.isEmpty()){
            switch (filter){
                case "id":
                    searchItems = repository.getIdentitasItemsById();
                    break;
                case "namaIdentitas":
                    searchItems = repository.getIdentitasItemsByName();
                    break;
                case "status":
                    searchItems = repository.getIdentitasItemsByStatus();
                    break;
            }
        }
        List<IdentitasIndexOptionDTO> identitasIndexOptionDTOS = new LinkedList<>();
        for(String searchItem : searchItems){
            IdentitasIndexOptionDTO identitasIndexOptionDTO = new IdentitasIndexOptionDTO(searchItem, searchItem);
            identitasIndexOptionDTOS.add(identitasIndexOptionDTO);
        }
        return identitasIndexOptionDTOS;
    }

}
