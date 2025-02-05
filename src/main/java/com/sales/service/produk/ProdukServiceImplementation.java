package com.sales.service.produk;

import com.sales.dto.cabang.CabangProdukGridDTO;
import com.sales.dto.produk.ProdukDetailDTO;
import com.sales.dto.produk.ProdukFormDTO;
import com.sales.dto.produk.ProdukIndexDTO;
import com.sales.dto.produk.ProdukIndexOptionDTO;
import com.sales.entity.Cabang;
import com.sales.entity.Produk;
import com.sales.repository.ProdukRepository;
import com.sales.service.cabang.CabangService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProdukServiceImplementation implements ProdukService {
    private final ProdukRepository repository;
    private final int rowInPage = 4;

    public ProdukServiceImplementation(ProdukRepository repository) {
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
                case "nama":
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
    public List<ProdukIndexDTO> getAll(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0){
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<Produk> produkList = new LinkedList<>();
        if (filter.isEmpty()){
            produkList = repository.getAllProduk(pageable);
        }else {
            switch (filter){
                case "id":
                    produkList = repository.getProdukById(pageable, search);
                    break;
                case "namaProduk":
                    produkList = repository.getProdukByName(pageable, search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    produkList = repository.getProdukByStatus(pageable, Boolean.parseBoolean(search));
                    break;
            }
        }

        List<ProdukIndexDTO> produkIndexDTOS = new LinkedList<>();
        for (Produk produk : produkList){
            ProdukIndexDTO produkIndexDTO = new ProdukIndexDTO();
            produkIndexDTO.setKodeProduk(produk.getId());
            produkIndexDTO.setNamaProduk(produk.getNamaProduk());
            String statusProduk = !produk.getStatus() ? "Tidak Aktif" : "Aktif";
            produkIndexDTO.setStatus(statusProduk);

            produkIndexDTOS.add(produkIndexDTO);
        }
        return produkIndexDTOS;
    }

    @Override
    public List<ProdukIndexDTO> getAllAktif(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0){
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<Produk> produkList = new LinkedList<>();
        if (filter.isEmpty()){
            produkList = repository.getAllProdukAktif(pageable);
        }else {
            switch (filter){
                case "id":
                    produkList = repository.getProdukAktifById(pageable,search);
                    break;
                case "namaProduk":
                    produkList = repository.getProdukAktifByName(pageable, search);
                    break;
            }
        }

        List<ProdukIndexDTO> produkIndexDTOS = new LinkedList<>();
        for (Produk produk : produkList){
            ProdukIndexDTO produkIndexDTO = new ProdukIndexDTO();
            produkIndexDTO.setKodeProduk(produk.getId());
            produkIndexDTO.setNamaProduk(produk.getNamaProduk());
            String statusProduk = !produk.getStatus() ? "Tidak Aktif" : "Aktif";
            produkIndexDTO.setStatus(statusProduk);

            produkIndexDTOS.add(produkIndexDTO);
        }
        return produkIndexDTOS;
    }


    @Override
    public CabangProdukGridDTO getAllRest(int page, String filter, String search){
        List<ProdukIndexDTO> produkIndexDTOS = this.getAllAktif(page, filter, search);
        CabangProdukGridDTO cabangProdukGridDTO = new CabangProdukGridDTO();
        cabangProdukGridDTO.setProdukIndexDTOS(produkIndexDTOS);
        cabangProdukGridDTO.setCurrentPage(page);
        cabangProdukGridDTO.setTotalPages(this.getTotalPages(filter, search));
        return cabangProdukGridDTO;
    };


    @Override
    public ProdukFormDTO getProdukById(Integer id) {
        ProdukFormDTO produkFormDTO = new ProdukFormDTO();
        if(id != null){
            try{
                Produk produk = repository.findById(id).orElseThrow();
                produkFormDTO.setId(produk.getId());
                produkFormDTO.setNamaProduk(produk.getNamaProduk());
                produkFormDTO.setStatus(produk.getStatus());
                return produkFormDTO;
            }  catch (Exception ignored){ }
        }
        return produkFormDTO;
    }

    @Override
    public ProdukDetailDTO getDetailProdukById(Integer id) {
        ProdukDetailDTO produkDetailDTO = new ProdukDetailDTO();
        if(id != null ){
            try {
                Produk produk = repository.findById(id).orElseThrow();
                produkDetailDTO.setKodeProduk(String.valueOf(produk.getId()));
                produkDetailDTO.setNamaProduk(produk.getNamaProduk());
                String statusProduk = !produk.getStatus() ? "Tidak Aktif" : "Aktif";
                produkDetailDTO.setStatus(statusProduk);
            } catch (Exception ignored){}
        }
        return produkDetailDTO;
    }

    @Override
    public void save(ProdukFormDTO produkFormDTO) {
        Produk produk = new Produk();
        produk.setId(produkFormDTO.getId());
        produk.setNamaProduk(produkFormDTO.getNamaProduk());
        produk.setStatus(produkFormDTO.getStatus());
        repository.save(produk);
    }

    @Override
    public void delete(int id) {
        try{
            Produk produk = repository.findById(id).orElseThrow();
            produk.setDeleteDate(LocalDate.now());
            repository.save(produk);
        } catch (Exception ignored){}
    }

    @Override
    public List<ProdukIndexOptionDTO> getFilterAsItem() {
        return List.of(
                new ProdukIndexOptionDTO("Kode Produk","id"),
                new ProdukIndexOptionDTO("Nama Produk", "namaProduk"),
                new ProdukIndexOptionDTO("Status", "status")
        );
    }
    @Override
    public List<ProdukIndexOptionDTO> getFilterAsItemNonStatus() {
        return List.of(
                new ProdukIndexOptionDTO("Kode Produk","id"),
                new ProdukIndexOptionDTO("Nama Produk", "namaProduk")
        );
    }

    @Override
    public List<ProdukIndexOptionDTO> getSearchItems(String filter) {
        List<String> searchItems = new LinkedList<>();
        if (!filter.isEmpty()){
            switch (filter){
                case "id":
                    searchItems = repository.getProdukItemsById();
                    break;
                case "namaProduk":
                    searchItems = repository.getProdukItemsByName();
                    break;
                case "status":
                    searchItems = repository.getProdukItemsByStatus();
                    break;
            }
        }
        List<ProdukIndexOptionDTO> produkIndexOptionDTOS = new LinkedList<>();
        for(String searchItem : searchItems){
            ProdukIndexOptionDTO produkIndexOptionDTO = new ProdukIndexOptionDTO(searchItem, searchItem);
            produkIndexOptionDTOS.add(produkIndexOptionDTO);
        }
        return produkIndexOptionDTOS;
    }


    @Override
    public List<ProdukIndexDTO> getAllProduks() {
        List<Produk> produkList = repository.getAllProduk();
        List<ProdukIndexDTO> produkIndexDTOS = new LinkedList<>();
        for (Produk produk:
             produkList) {
            ProdukIndexDTO produkIndexDTO = new ProdukIndexDTO();
            produkIndexDTO.setKodeProduk(produk.getId());
            produkIndexDTO.setNamaProduk(produk.getNamaProduk());
            produkIndexDTOS.add(produkIndexDTO);
        }
        return produkIndexDTOS;
    }

}
