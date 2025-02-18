package com.sales.service.cabang;

import com.sales.dto.OptionDTO;
import com.sales.dto.cabang.*;
import com.sales.dto.mitraAgen.KelurahanOptionDTO;
import com.sales.dto.pot.CabangPotGridDTO;
import com.sales.dto.produk.ProdukIndexOptionDTO;
import com.sales.entity.Cabang;
import com.sales.entity.Kelurahan;
import com.sales.entity.Produk;
import com.sales.helper.DateHelper;
import com.sales.repository.CabangRepository;
import com.sales.repository.KelurahanRepository;
import com.sales.repository.ProdukRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CabangServiceImplementation implements CabangService{
    private final CabangRepository cabangRepository;
    private final ProdukRepository produkRepository;
    private final KelurahanRepository kelurahanRepository;
    private final int rowInPage = 4;

    public CabangServiceImplementation(CabangRepository cabangRepository, ProdukRepository produkRepository, KelurahanRepository kelurahanRepository) {
        this.cabangRepository = cabangRepository;
        this.produkRepository = produkRepository;
        this.kelurahanRepository = kelurahanRepository;
    }

    @Override
    public int getTotalPages(String filter, String search) {
        double page = 0;
        if(filter.isEmpty()){
            page = cabangRepository.getTotalPages();
        } else {
            switch (filter){
                case "id":
                    page = cabangRepository.getTotalPagesById(search);
                    break;
                case "namaCabang":
                    page = cabangRepository.getTotalpagesByName(search);
                    break;
                case "tipeStruktur":
                    page = cabangRepository.getTotalpagesByTipeStruktur(search);
                    break;
                case "alamat":
                    page = cabangRepository.getTotalpagesByAlamat(search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    page = cabangRepository.getTotalpagesByStatus(Boolean.parseBoolean(search));
                    break;
            }
        }
        return (int) Math.ceil(page/rowInPage);
    }

    @Override
    public int getTotalPagesAktif(String filter, String search) {
        double page = 0;
        if(filter.isEmpty()){
            page = cabangRepository.getTotalPagesAktif();
        } else {
            switch (filter){
                case "id":
                    page = cabangRepository.getTotalPagesByIdAktif(search);
                    break;
                case "namaCabang":
                    page = cabangRepository.getTotalpagesByNameAktif(search);
                    break;
                case "tipeStruktur":
                    page = cabangRepository.getTotalpagesByTipeStrukturAktif(search);
                    break;
                case "alamat":
                    page = cabangRepository.getTotalpagesByAlamatAktif(search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    page = cabangRepository.getTotalpagesByStatusAktif(Boolean.parseBoolean(search));
                    break;
            }
        }
        return (int) Math.ceil(page/rowInPage) == 0 ? 1 : (int) Math.ceil(page/rowInPage);
    }

    @Override
    public List<CabangIndexDTO> getAll(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0){
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<Cabang> cabangList = new LinkedList<>();
        if (filter.isEmpty()){
            cabangList = cabangRepository.getAllCabang(pageable);
        }else {
            switch (filter){
                case "id":
                    cabangList = cabangRepository.getCabangById(pageable, search);
                    break;
                case "namaCabang":
                    cabangList = cabangRepository.getCabangByName(pageable, search);
                    break;
                case "tipeStruktur":
                    cabangList = cabangRepository.getCabangByTipeStruktur(pageable, search);
                    break;
                case "alamat":
                    cabangList = cabangRepository.getCabangByAlamat(pageable, search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    cabangList = cabangRepository.getCabangByStatus(pageable, Boolean.parseBoolean(search));
                    break;
            }
        }

        List<CabangIndexDTO> cabangIndexDTOS = new LinkedList<>();
        for (Cabang cabang : cabangList){
            CabangIndexDTO cabangIndexDTO = new CabangIndexDTO();
            cabangIndexDTO.setKodeCabang(cabang.getId());
            cabangIndexDTO.setNamaCabang(cabang.getNamaCabang());
            cabangIndexDTO.setTipeStruktur(cabang.getTipeStruktur().getNamaStruktur());
            cabangIndexDTO.setAlamat(cabang.getAlamat());
            String statusCabang = !cabang.getStatus() ? "Tidak Aktif" : "Aktif";
            cabangIndexDTO.setStatus(statusCabang);

            cabangIndexDTOS.add(cabangIndexDTO);
        }
        return cabangIndexDTOS;
    }

    @Override
    public CabangFormDTO getCabangById(Integer id) {

        CabangFormDTO cabangFormDTO = new CabangFormDTO();
        if(id != null){
          try{
              Cabang cabang = cabangRepository.findById(id).orElseThrow();
              cabangFormDTO.setId(cabang.getId());
              cabangFormDTO.setNamaCabang(cabang.getNamaCabang());
              cabangFormDTO.setTipeStruktur(cabang.getTipeStruktur().getNamaStruktur());
              cabangFormDTO.setAlamat(cabang.getAlamat());
              cabangFormDTO.setKodePos(cabang.getKelurahan().getKodePos());
              cabangFormDTO.setKelurahan(cabang.getKelurahan().getNamaKelurahan());
              cabangFormDTO.setKecamatan(cabang.getKelurahan().getKecamatan().getNamaKecamatan());
              cabangFormDTO.setKotaKabupaten(cabang.getKelurahan().getKabupaten().getNamaKabupatenKota());
              cabangFormDTO.setProvinsi(cabang.getKelurahan().getProvinsi().getNamaProvinsi());
              cabangFormDTO.setNomorTelepon(cabang.getNomorTelepon());
              cabangFormDTO.setNpwp(cabang.getNomorNPWP());
              cabangFormDTO.setTanggalBerdiri(cabang.getTanggalBerdiri());
              cabangFormDTO.setStatus(cabang.getStatus());
              return cabangFormDTO;
          }  catch (Exception ignored){}
        }
        return cabangFormDTO;
    }

    @Override
    public CabangDetailDTO getDetailCabangById(Integer id) {
        CabangDetailDTO cabangDetailDTO = new CabangDetailDTO();
        if (id != null){
            try {
                Cabang cabang =  cabangRepository.findById(id).orElseThrow();
                cabangDetailDTO.setKodeCabang(String.valueOf(cabang.getId()));
                cabangDetailDTO.setNamaCabang(cabang.getNamaCabang());
                cabangDetailDTO.setTipeStruktur(cabang.getTipeStruktur().getNamaStruktur());
                cabangDetailDTO.setAlamat(cabang.getAlamat());
                cabangDetailDTO.setKodePos(cabang.getKelurahan().getKodePos());
                cabangDetailDTO.setKecamatan(cabang.getKelurahan().getNamaKelurahan());
                cabangDetailDTO.setKotaKabupaten(cabang.getKelurahan().getKabupaten().getNamaKabupatenKota());
                cabangDetailDTO.setProvinsi(cabang.getKelurahan().getProvinsi().getNamaProvinsi());
                cabangDetailDTO.setNomorTelepon(cabang.getNomorTelepon());
                cabangDetailDTO.setNpwp(cabang.getNomorNPWP());
                cabangDetailDTO.setTanggalBerdiri(DateHelper.dateParse(cabang.getTanggalBerdiri(),"dd MMMM yyyy", new Locale("id","ID")));
                cabangDetailDTO.setProduk("skip");
                String statusCabang = !cabang.getStatus() ? "Tidak Aktif" : "Aktif";
                cabangDetailDTO.setStatus(statusCabang);
            } catch (Exception ignored){}
        }
        return cabangDetailDTO;
    }

    @Override
    public void save(CabangFormDTO cabangFormDTO) {
        Cabang cabang = new Cabang();
        cabang.setId(cabangFormDTO.getId());
        cabang.setNamaCabang(cabangFormDTO.getNamaCabang());
        cabang.setIdTipeStruktur(1);
        cabang.setAlamat(cabangFormDTO.getAlamat());
        cabang.setIdKelurahan(1);
        cabang.setNomorTelepon(cabangFormDTO.getNomorTelepon());
        cabang.setNomorNPWP(cabangFormDTO.getNpwp());
        cabang.setTanggalBerdiri(cabangFormDTO.getTanggalBerdiri());
        cabang.setStatus(cabangFormDTO.getStatus());
        Set<Produk> produkSet = new HashSet<>();
        for (Integer kodeProduk: cabangFormDTO.getProdukList()) {
                    Produk produk = produkRepository.getProdukById(kodeProduk);
                    produkSet.add(produk);
                    cabang.setProdukSet(produkSet);
            }
        cabangRepository.save(cabang);
    }

    @Override
    public void delete(int id) {
        try {
            Cabang cabang = cabangRepository.findById(id).orElseThrow();
            cabang.setDeleteDate(LocalDate.now());
            cabangRepository.save(cabang);
        }catch (Exception ignored){}
    }

    @Override
    public List<CabangIndexOptionDTO> getFilterAsItem() {
        return List.of(
                new CabangIndexOptionDTO("Kode Cabang", "id"),
                new CabangIndexOptionDTO("Nama Cabang", "namaCabang"),
                new CabangIndexOptionDTO("Tipe Struktur", "tipeStruktur"),
                new CabangIndexOptionDTO("Alamat", "alamat"),
                new CabangIndexOptionDTO("Status", "status")
        );
    }

    @Override
    public List<CabangIndexOptionDTO> getSearchItems(String filter) {
        List<String> searchItems = new LinkedList<>();
        if (!filter.isEmpty()){
            switch (filter){
                case "id":
                    searchItems = cabangRepository.getCabangItemsById();
                    break;
                case "namaCabang":
                    searchItems = cabangRepository.getCabangItemsByName();
                    break;
                case "tipeStruktur":
                    searchItems = cabangRepository.getCabangItemsByTipeStruktur();
                    break;
                case "alamat":
                    searchItems = cabangRepository.getCabangItemsByAlamat();
                    break;
                case "status":
                    searchItems = cabangRepository.getCabangItemsByStatus();
                    break;
            }
        }
        List<CabangIndexOptionDTO> cabangIndexOptionDTOS = new LinkedList<>();
        for(String searchItem : searchItems){
            CabangIndexOptionDTO cabangIndexOptionDTO = new CabangIndexOptionDTO(searchItem, searchItem);
            cabangIndexOptionDTOS.add(cabangIndexOptionDTO);
        }
        return cabangIndexOptionDTOS;
    }

    @Override
    public List<CabangProdukDTO> getProdukByCabang(Integer id) {
        Cabang cabang = cabangRepository.findById(id).orElseThrow();
        List<CabangProdukDTO> cabangProdukDTOS = new LinkedList<>();
        for (Produk produk: cabang.getProdukSet()){
            CabangProdukDTO cabangProdukDTO = new CabangProdukDTO();
            cabangProdukDTO.setId(produk.getId());
            cabangProdukDTO.setNama(produk.getNamaProduk());
            cabangProdukDTOS.add(cabangProdukDTO);
        }
        return cabangProdukDTOS;
    }

    @Override
    public List<KelurahanOptionDTO> getKelurahanOption() {
        List<Kelurahan> kelurahanList = kelurahanRepository.getAllKelurahanOption();
        List<KelurahanOptionDTO> kelurahanOptionDTOS = new LinkedList<>();
        for (Kelurahan kelurahan: kelurahanList
        ) {
            KelurahanOptionDTO kelurahanOptionDTO = new KelurahanOptionDTO();
            kelurahanOptionDTO.setId(kelurahan.getId());
            kelurahanOptionDTO.setNamaKelurahan(kelurahan.getNamaKelurahan());
            kelurahanOptionDTO.setKecamatan(kelurahan.getKecamatan().getNamaKecamatan());
            kelurahanOptionDTO.setProvinsi(kelurahan.getProvinsi().getNamaProvinsi());
            kelurahanOptionDTO.setKodePos(kelurahan.getKodePos());
            kelurahanOptionDTO.setKotaKabupaten(kelurahan.getKabupaten().getNamaKabupatenKota());
            kelurahanOptionDTOS.add(kelurahanOptionDTO);
        }
        return kelurahanOptionDTOS;
    }

    @Override
    public List<ProdukIndexOptionDTO> getSearchProdukItems(String filter) {
        List<String> searchItems = new LinkedList<>();
        if(!filter.isBlank()){
            switch (filter){
                case "id":
                    searchItems = produkRepository.getProdukItemsById();
                    break;
                case "namaProduk":
                    searchItems = produkRepository.getProdukItemsByName();
                    break;
            }
        }
        List<ProdukIndexOptionDTO> searchsItems = new LinkedList<>();
        for (var searchItem : searchItems){
            ProdukIndexOptionDTO item = new ProdukIndexOptionDTO(searchItem, searchItem);
            searchsItems.add(item);
        }
        return searchsItems;
    }

    @Override
    public List<OptionDTO> getfilterAsItem() {
        return List.of(
                new OptionDTO("Kode Cabang", "id"),
                new OptionDTO("Nama Cabang", "namaCabang"),
                new OptionDTO("Tipe Struktur", "tipeStruktur")
        );
    }

    @Override
    public List<CabangIndexDTO> getAllAktif(int page, String filter, String search) {
        int totalPages = getTotalPagesAktif(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0){
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<Cabang> cabangList = new LinkedList<>();
        if (filter.isEmpty()){
            cabangList = cabangRepository.getAllCabangAktif(pageable);
        }else {
            switch (filter){
                case "id":
                    cabangList = cabangRepository.getCabangByIdAktif(pageable, search);
                    break;
                case "namaCabang":
                    cabangList = cabangRepository.getCabangByNameAktif(pageable, search);
                    break;
                case "tipeStruktur":
                    cabangList = cabangRepository.getCabangByTipeStrukturAktif(pageable, search);
                    break;
                case "alamat":
                    cabangList = cabangRepository.getCabangByAlamatAktif(pageable, search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    cabangList = cabangRepository.getCabangByStatusAktif(pageable, Boolean.parseBoolean(search));
                    break;
            }
        }

        List<CabangIndexDTO> cabangIndexDTOS = new LinkedList<>();
        for (Cabang cabang : cabangList){
            CabangIndexDTO cabangIndexDTO = new CabangIndexDTO();
            cabangIndexDTO.setKodeCabang(cabang.getId());
            cabangIndexDTO.setNamaCabang(cabang.getNamaCabang());
            cabangIndexDTO.setTipeStruktur(cabang.getTipeStruktur().getNamaStruktur());
            cabangIndexDTO.setAlamat(cabang.getAlamat());
            String statusCabang = !cabang.getStatus() ? "Tidak Aktif" : "Aktif";
            cabangIndexDTO.setStatus(statusCabang);

            cabangIndexDTOS.add(cabangIndexDTO);
        }
        return cabangIndexDTOS;
    }

    @Override
    public CabangPotGridDTO getAllCabang(int page, String filter, String search) {
        List<CabangIndexDTO> cabangIndexDTOS = this.getAllAktif(page, filter, search);
        CabangPotGridDTO cabangPotGridDTO = new CabangPotGridDTO();
        cabangPotGridDTO.setCabangIndexDTOS(cabangIndexDTOS);
        cabangPotGridDTO.setCurrentPage(page);
        cabangPotGridDTO.setTotalPages(this.getTotalPagesAktif(filter, search));
        return cabangPotGridDTO;
    }

    @Override
    public List<CabangIndexDTO> getAllCabangs() {
        List<Cabang> cabangList = cabangRepository.getAllCabangs();
        List<CabangIndexDTO> cabangIndexDTOS = new LinkedList<>();
        for (Cabang cabang: cabangList) {
            CabangIndexDTO cabangIndexDTO = new CabangIndexDTO();
            cabangIndexDTO.setKodeCabang(cabang.getId());
            cabangIndexDTO.setNamaCabang(cabang.getNamaCabang());
            cabangIndexDTO.setTipeStruktur(cabang.getTipeStruktur().getNamaStruktur());
            cabangIndexDTOS.add(cabangIndexDTO);
        }
        return cabangIndexDTOS;
    }

    @Override
    public List<OptionDTO> getSearchCabangItems(String filter) {
        List<String > searchItems = new LinkedList<>();
        if(!filter.isBlank()){
            switch (filter){
                case "id":
                    searchItems = cabangRepository.getCabangItemsById();
                    break;
                case "namaCabang":
                    searchItems = cabangRepository.getCabangItemsByName();
                    break;
                case "tipeStruktur":
                    searchItems = cabangRepository.getCabangItemsByTipeStruktur();
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
    public List<OptionDTO> getFilterCabangWilayah() {
        return List.of(
                new OptionDTO("Kode Cabang", "id"),
                new OptionDTO("Nama Cabang", "namaCabang")
        );
    }


}
