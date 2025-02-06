package com.sales.service.hargaPasar;

import com.sales.dto.cabang.CabangDetailDTO;
import com.sales.dto.cabang.CabangFormDTO;
import com.sales.dto.hargaPasar.HargaPasarDetailDTO;
import com.sales.dto.hargaPasar.HargaPasarFormDTO;
import com.sales.dto.hargaPasar.HargaPasarIndexDTO;
import com.sales.dto.hargaPasar.HargaPasarIndexOptionDTO;
import com.sales.entity.HargaPasar;
import com.sales.repository.HargaPasarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class HargaPasarServiceImplementation implements HargaPasarService{
    private final HargaPasarRepository repository;
    private final int rowInPage = 4;

    @Autowired
    public HargaPasarServiceImplementation(HargaPasarRepository repository) {
        this.repository = repository;
    }

    @Override
    public int getTotalPages(String filter, String search) {
        double page = 0;
        if (filter.isEmpty()) {
            page = repository.getTotalPages();
        } else {
            switch (filter) {
                case "wilayah":
                    page = repository.getTotalPagesByWilayah(search);
                    break;
                case "merk":
                    page = repository.getTotalPagesByMerk(search);
                    break;
                case "tipe":
                    page = repository.getTotalPagesByTipe(search);
                    break;
                case "model":
                    page = repository.getTotalPagesByModel(search);
                    break;
                case "jenis":
                    page = repository.getTotalPagesByJenis(search);
                    break;
                case "tipeUnit":
                    page = repository.getTotalPagesByTipeUnit(search);
                    break;
                case "tahun":
                    page = repository.getTotalPagesByTahun(search);
                    break;
                case "harga":
                    page = repository.getTotalPagesByHarga(search);
                    break;
                case "tahunBerlaku":
                    page = repository.getTotalPagesByTahunBerlaku(search);
                    break;
            }
        }
        return (int) Math.ceil(page / rowInPage);

    }

    @Override
    public List<HargaPasarIndexDTO> getAll(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0){
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<HargaPasar> hargaPasarList = new LinkedList<>();
        if (filter.isEmpty()){
            hargaPasarList = repository.getAllHargaPasar(pageable);
        }else {
            switch (filter) {
                case "wilayah":
                    hargaPasarList = repository.getHargaPasarByWilayah(pageable, search);
                    break;
                case "merk":
                    hargaPasarList = repository.getHargaPasarByMerk(pageable, search);
                    break;
                case "tipe":
                    hargaPasarList = repository.getHargaPasarByTipe(pageable, search);
                    break;
                case "model":
                    hargaPasarList = repository.getHargaPasarByModel(pageable, search);
                    break;
                case "jenis":
                    hargaPasarList = repository.getHargaPasarByJenis(pageable, search);
                    break;
                case "tipeUnit":
                    hargaPasarList = repository.getHargaPasarByTipeUnit(pageable, search);
                    break;
                case "tahun":
                    hargaPasarList = repository.getHargaPasarByTahun(pageable, search);
                    break;
                case "harga":
                    hargaPasarList = repository.getHargaPasarByHarga(pageable, search);
                    break;
                case "tahunBerlaku":
                    hargaPasarList = repository.getHargaPasarByTahunBerlaku(pageable, search);
                    break;
            }
        }
        List<HargaPasarIndexDTO> hargaPasarIndexDTOS = new LinkedList<>();
        for (HargaPasar hargaPasar:
             hargaPasarList) {
            HargaPasarIndexDTO hargaPasarIndexDTO =  new HargaPasarIndexDTO();
            hargaPasarIndexDTO.setWilayah(hargaPasar.getWilayahHargaPasar().getNamaWilayah());
            hargaPasarIndexDTO.setKategori(hargaPasar.getKategoriHargaPasar().getNamaKategori());
            hargaPasarIndexDTO.setMerk(hargaPasar.getMerkHargaPasar().getNamaMerk());
            hargaPasarIndexDTO.setTipe(hargaPasar.getTipeHargaPasar().getNamaTipe());
            hargaPasarIndexDTO.setModel(hargaPasar.getModelHargaPasar().getNamaModel());
            hargaPasarIndexDTO.setJenis(hargaPasar.getJenisHargaPasar().getNamaJenis());
            hargaPasarIndexDTO.setTipeUnit(hargaPasar.getTipeUnit());
            hargaPasarIndexDTO.setTahun(hargaPasar.getTahun());
            hargaPasarIndexDTO.setHarga(hargaPasar.getHarga());
            hargaPasarIndexDTO.setTanggalMulaiBerlaku(hargaPasar.getTanggalBerlaku());
            hargaPasarIndexDTOS.add(hargaPasarIndexDTO);
        }
        return hargaPasarIndexDTOS;
    }

    @Override
    public HargaPasarFormDTO getHargaPasarById(Integer id) {
        HargaPasarFormDTO hargaPasarFormDTO = new HargaPasarFormDTO();
        if (id != null){
            try {
                HargaPasar hargaPasar = repository.findById(id).orElseThrow();
//                hargaPasarFormDTO
            }catch (Exception ignored){}
        }
        return null;
    }

    @Override
    public HargaPasarDetailDTO getDetailHargaPasarById(Integer id) {
        return null;
    }

    @Override
    public void save(HargaPasarFormDTO hargaPasarFormDTO) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<HargaPasarIndexOptionDTO> getFilterAsItem() {
        return null;
    }

    @Override
    public List<HargaPasarIndexOptionDTO> getSearchItems(String filter) {
        return null;
    }
}
