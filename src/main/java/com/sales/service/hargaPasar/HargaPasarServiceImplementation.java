package com.sales.service.hargaPasar;

import com.sales.dto.OptionDTO;
import com.sales.dto.cabang.CabangDetailDTO;
import com.sales.dto.cabang.CabangFormDTO;
import com.sales.dto.hargaPasar.HargaPasarDetailDTO;
import com.sales.dto.hargaPasar.HargaPasarFormDTO;
import com.sales.dto.hargaPasar.HargaPasarIndexDTO;
import com.sales.dto.hargaPasar.HargaPasarIndexOptionDTO;
import com.sales.dto.model.ModelDetailDTO;
import com.sales.dto.tipe.TipeJenisDTO;
import com.sales.entity.HargaPasar;
import com.sales.entity.Model;
import com.sales.entity.WilayahHargaPasar;
import com.sales.helper.DateHelper;
import com.sales.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Service
public class HargaPasarServiceImplementation implements HargaPasarService{
    private final HargaPasarRepository repository;
    private final KategoriRepository kategoriRepository;
    private final WilayahHargaPasarRepository wilayahHargaPasarRepository;
    private final MerkRepository merkRepository;
    private final TipeRepository tipeRepository;
    private final ModelRepository modelRepository;
    private final TipeUnitRepository tipeUnitRepository;
    private final int rowInPage = 4;
    public final Locale indo = new Locale("id", "ID");

    @Autowired
    public HargaPasarServiceImplementation(HargaPasarRepository repository, KategoriRepository kategoriRepository,
                                           WilayahHargaPasarRepository wilayahHargaPasarRepository, MerkRepository merkRepository,
                                           TipeRepository tipeRepository, ModelRepository modelRepository, TipeUnitRepository tipeUnitRepository) {
        this.repository = repository;
        this.kategoriRepository = kategoriRepository;
        this.wilayahHargaPasarRepository = wilayahHargaPasarRepository;
        this.merkRepository = merkRepository;
        this.tipeRepository = tipeRepository;
        this.modelRepository = modelRepository;
        this.tipeUnitRepository = tipeUnitRepository;
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
            hargaPasarIndexDTO.setIdHargaPasar(hargaPasar.getId());
            hargaPasarIndexDTO.setWilayah(hargaPasar.getWilayahHargaPasar().getNamaWilayah());
            hargaPasarIndexDTO.setKategori(hargaPasar.getKategoriHargaPasar().getNamaKategori());
            hargaPasarIndexDTO.setMerk(hargaPasar.getMerkHargaPasar().getNamaMerk());
            hargaPasarIndexDTO.setTipe(hargaPasar.getTipeHargaPasar().getNamaTipe());
            hargaPasarIndexDTO.setModel(hargaPasar.getModelHargaPasar().getNamaModel());
            hargaPasarIndexDTO.setJenis(hargaPasar.getJenisHargaPasar().getNamaJenis());
            hargaPasarIndexDTO.setTipeUnit(hargaPasar.getTipeUnitHargaPasar().getNamaUnit());
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
                hargaPasarFormDTO.setId(hargaPasar.getId());
                hargaPasarFormDTO.setIdKategori(hargaPasar.getIdKategori());
                hargaPasarFormDTO.setNamaKategori(hargaPasar.getKategoriHargaPasar().getNamaKategori());
                hargaPasarFormDTO.setIdWilayah(hargaPasar.getIdWilayah());
                hargaPasarFormDTO.setNamaWilayah(hargaPasar.getWilayahHargaPasar().getNamaWilayah());
                hargaPasarFormDTO.setIdMerk(hargaPasar.getIdMerk());
                hargaPasarFormDTO.setNamaMerk(hargaPasar.getMerkHargaPasar().getNamaMerk());
                hargaPasarFormDTO.setIdTipe(hargaPasar.getIdTipe());
                hargaPasarFormDTO.setNamaTipe(hargaPasar.getTipeHargaPasar().getNamaTipe());
                hargaPasarFormDTO.setIdModel(hargaPasar.getIdModel());
                hargaPasarFormDTO.setNamaModel(hargaPasar.getModelHargaPasar().getNamaModel());
                hargaPasarFormDTO.setIdJenis(hargaPasar.getTipeHargaPasar().getIdJenis());
                hargaPasarFormDTO.setNamaJenis(hargaPasar.getTipeHargaPasar().getJenisTipe().getNamaJenis());
                hargaPasarFormDTO.setIdTipeUnit(hargaPasar.getIdUnit());
                hargaPasarFormDTO.setNamaTipeUnit(hargaPasar.getTipeUnitHargaPasar().getNamaUnit());
                hargaPasarFormDTO.setTahun(hargaPasar.getTahun());
                hargaPasarFormDTO.setHarga(hargaPasar.getHarga().stripTrailingZeros().toPlainString());
                hargaPasarFormDTO.setTanggalMulai(hargaPasar.getTanggalBerlaku());
                hargaPasarFormDTO.setStatus(hargaPasar.getStatus());
            }catch (Exception ignored){}
        }
        return hargaPasarFormDTO;
    }

    @Override
    public void saveHargaPasar(HargaPasarFormDTO dto) {
        HargaPasar hargaPasar = new HargaPasar();
        if(dto.getId() != null){
            hargaPasar.setId(dto.getId());
        }
        hargaPasar.setIdKategori(dto.getIdKategori());
        hargaPasar.setIdWilayah(dto.getIdWilayah());
        hargaPasar.setIdMerk(dto.getIdMerk());
        hargaPasar.setIdTipe(dto.getIdTipe());
        hargaPasar.setIdModel(dto.getIdModel());
        hargaPasar.setIdJenis(dto.getIdJenis());
        hargaPasar.setIdUnit(dto.getIdTipeUnit());
        hargaPasar.setTahun(dto.getTahun());
        var harga = new BigDecimal(dto.getHarga());
        hargaPasar.setHarga(harga);
        hargaPasar.setTanggalBerlaku(dto.getTanggalMulai());
        hargaPasar.setStatus(dto.getStatus());
        repository.save(hargaPasar);
    }

    @Override
    public HargaPasarDetailDTO getDetailHargaPasarById(Integer id) {
        HargaPasar hargaPasar = repository.findById(id).orElseThrow();
        HargaPasarDetailDTO dto = new HargaPasarDetailDTO();
        dto.setId(String.valueOf(hargaPasar.getId()));
        dto.setNamaKategori(hargaPasar.getKategoriHargaPasar().getNamaKategori());
        String namaWilayah = hargaPasar.getWilayahHargaPasar().getNamaWilayah();
        String idWilayah = hargaPasar.getIdWilayah();
        dto.setWilayah(idWilayah+" - "+namaWilayah);
        String namaMerk = hargaPasar.getMerkHargaPasar().getNamaMerk();
        String idMerk = hargaPasar.getIdMerk();
        dto.setMerk(idMerk+" - "+namaMerk);
        String namaTipe = hargaPasar.getTipeHargaPasar().getNamaTipe();
        String idTipe = hargaPasar.getIdTipe();
        dto.setTipe(idTipe+" - "+namaTipe);
        String namaModel = hargaPasar.getModelHargaPasar().getNamaModel();
        String idModel = hargaPasar.getIdModel();
        dto.setModel(idModel+" - "+namaModel);
        String namaJenis = hargaPasar.getTipeHargaPasar().getJenisTipe().getNamaJenis();
        String idJenis = hargaPasar.getTipeHargaPasar().getIdJenis();
        dto.setJenis(idJenis+" - "+namaJenis);
        dto.setNamaTipeUnit(hargaPasar.getTipeUnitHargaPasar().getNamaUnit());
        dto.setTahun(hargaPasar.getTahun());
        dto.setHarga(NumberFormat.getCurrencyInstance(indo).format(hargaPasar.getHarga()));
        dto.setTanggalMulai(DateHelper.dateParse(hargaPasar.getTanggalBerlaku(),"dd MMMM YYYY",indo));
        dto.setStatus(hargaPasar.getStatus()? "Aktif":"Tidak Akrif");
        return dto;
    }


    @Override
    public List<HargaPasarIndexOptionDTO> getFilterAsItem() {
        return null;
    }

    @Override
    public List<HargaPasarIndexOptionDTO> getSearchItems(String filter) {
        return null;
    }



    @Override
    public void deleteHargaPasar(Integer id) {
        var hargaPasar = repository.findById(id).orElseThrow();
        hargaPasar.setDeleteDate(LocalDate.now());
        repository.save(hargaPasar);
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

    @Override
    public List<OptionDTO> getWilayahItems() {
        var wilayahItem = wilayahHargaPasarRepository.getAllWilayah();
        List<OptionDTO> merkList = new LinkedList<>();
        for (var wilayah : wilayahItem){
            OptionDTO item = new OptionDTO(wilayah.getNamaWilayah(),String.valueOf(wilayah.getId()));
            merkList.add(item);
        }
        return merkList;
    }

    @Override
    public List<OptionDTO> getMerkItems() {
        var merkItem = merkRepository.getAllMerk();
        List<OptionDTO> wilayahList = new LinkedList<>();
        for (var merk : merkItem){
            OptionDTO item = new OptionDTO(merk.getNamaMerk(),String.valueOf(merk.getId()));
            wilayahList.add(item);
        }
        return wilayahList;
    }

    @Override
    public List<OptionDTO> getTipeItems() {
        var tipeItem = tipeRepository.getAllTipe();
        List<OptionDTO> tipeList = new LinkedList<>();
        for (var tipe : tipeItem){
            OptionDTO item = new OptionDTO(tipe.getNamaTipe(),String.valueOf(tipe.getId()));
            tipeList.add(item);
        }
        return tipeList;
    }

    @Override
    public List<OptionDTO> getModelItems() {
        var modelItem = modelRepository.getAllModel();
        List<OptionDTO> modelList = new LinkedList<>();
        for (var model : modelItem){
            OptionDTO item = new OptionDTO(model.getNamaModel(),String.valueOf(model.getId()));
            modelList.add(item);
        }
        return modelList;
    }

    @Override
    public OptionDTO getTipeJenis(String idTipe) {
        var tipeData = tipeRepository.findById(idTipe).orElseThrow();
        OptionDTO tipeJenisDTO = new OptionDTO();
        tipeJenisDTO.setText(tipeData.getJenisTipe().getNamaJenis());
        tipeJenisDTO.setValue(tipeData.getIdJenis());
        return tipeJenisDTO;
    }

    @Override
    public List<OptionDTO> getTipeUnitItems() {
        var tipeUnits = tipeUnitRepository.getAllTipeUnit();
        List<OptionDTO> tipeUnitList = new LinkedList<>();
        for (var tipeUnit : tipeUnits){
            OptionDTO item = new OptionDTO(tipeUnit.getNamaUnit(),String.valueOf(tipeUnit.getId()));
            tipeUnitList.add(item);
        }
        return tipeUnitList;
    }
}
