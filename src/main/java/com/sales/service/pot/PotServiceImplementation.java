package com.sales.service.pot;


import com.sales.dto.OptionDTO;
import com.sales.dto.cabang.CabangProdukDTO;
import com.sales.dto.pot.PotDetailDTO;
import com.sales.dto.pot.PotFormDTO;
import com.sales.dto.pot.PotIndexDTO;
import com.sales.dto.tipe.TipeDetailDTO;
import com.sales.entity.Cabang;
import com.sales.entity.POT;
import com.sales.entity.Produk;
import com.sales.entity.Tipe;
import com.sales.helper.DateHelper;
import com.sales.repository.PotRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Service
public class PotServiceImplementation implements PotService {
    private final PotRepository repository;
    private final Integer rowInPage = 10;
    public final Locale indo = new Locale("id", "ID");

    public PotServiceImplementation(PotRepository repository) {
        this.repository = repository;
    }

    @Override
    public PotFormDTO getPotById(Integer potId) {
        PotFormDTO potFormDTO = new PotFormDTO();
        potId = potId == null ? 0 : potId;
        if(potId != 0) {
            var pot = repository.findById(potId).orElseThrow();
            Integer id = pot.getId();
            String namaPot = pot.getNamaPOT();
            Integer idProduk = pot.getIdProduk();
            String idKriteriaPaket = pot.getIdKriteriaPaket();
            LocalDate tanggalMulai = pot.getTanggalMulai();
            LocalDate tanggalAkhir = pot.getTanggalAkhir();
            String pokokAwal = pot.getPokokHutangAwal().stripTrailingZeros().toString();
            String pokokAkhir = pot.getPokokHutangAkhir().stripTrailingZeros().toString();
            Integer idInterval = pot.getIdInterval();
            String tenor = String.valueOf(pot.getTenor());
            String effectiveRate = String.valueOf(pot.getEffectRate());
            String nilaiAdmin = pot.getNilaiAdmin().stripTrailingZeros().toString();
            String nilaiProvisi = pot.getNilaiProvisi().stripTrailingZeros().toString();
            String dp = String.valueOf(pot.getDp());
            Boolean statusMerchandise = pot.getStatusMerchandise();
            Integer idKategori = pot.getIdKategori();
            String idMerk = pot.getIdMerk();
            String idTipe = pot.getIdTipe();
            String idModel = pot.getIdModel();
            potFormDTO.setId(id);
            potFormDTO.setNamaPot(namaPot);
            potFormDTO.setNamaProduk(pot.getProduk().getNamaProduk());
            potFormDTO.setIdProduk(idProduk);
            potFormDTO.setIdKriteriaPaket(idKriteriaPaket);
            potFormDTO.setNamaKriteriaPaket(pot.getKriteriaPaket().getNamaKriteria());
            potFormDTO.setTanggalMulai(tanggalMulai);
            potFormDTO.setTanggalAkhir(tanggalAkhir);
            potFormDTO.setPokokHutangAwal(pokokAwal);
            potFormDTO.setPokokHutangAkhir(pokokAkhir);
            potFormDTO.setIdIntevalPembayaran(idInterval);
            potFormDTO.setTenor(tenor);
            potFormDTO.setEffectiveRate(effectiveRate);
            potFormDTO.setNilaiAdmin(nilaiAdmin);
            potFormDTO.setNilaiProvisi(nilaiProvisi);
            potFormDTO.setDp(dp);
            potFormDTO.setStatusMerchandise(statusMerchandise);
            potFormDTO.setIdKategori(idKategori);
            potFormDTO.setIdMerk(idMerk);
            potFormDTO.setIdTipe(idTipe);
            potFormDTO.setIdModel(idModel);
        }
        return potFormDTO;
    }

    @Override
    public List<PotIndexDTO> getAll(String filter, String search, int page) {
        Pageable pagination = PageRequest.of(page-1,rowInPage, Sort.by("id"));
        List<POT> potList = new LinkedList<>();
        if(filter.equals("null") || filter.isBlank()){
            potList = repository.getAll( pagination);
        }else {
            switch (filter){
                case "id":
                    potList = repository.getByIdPot(search, pagination);
                    break;
                case "namaPot":
                    potList = repository.getByName(search,pagination);
                    break;
                case "namaProduk":
                    potList = repository.getByProduk(search,pagination);
                    break;
                case "tanggalAwal":
                    potList = repository.getByTanggalAwal(search, pagination);
                    break;
                case "tanggalAkhir":
                    potList = repository.getByTanggalAkhir(search, pagination);
                    break;
                case "pokokAwal":
                    potList = repository.getByPokokAwal(search, pagination);
                    break;
                case "pokokAkhir":
                    potList = repository.getByPokokAkhir(search, pagination);
                    break;
                case "tenor":
                    potList = repository.getByTenor(search, pagination);
                    break;
                case "effectiveRate":
                    potList = repository.getByEffectRate(search, pagination);
                    break;

            }
        }
        var gridPOT = new LinkedList<PotIndexDTO>();
        for (var pot :potList) {
            if(pot.getDeleteDate() == null){
                String id = String.valueOf(pot.getId());
                String namaPot = pot.getNamaPOT();
                String namaProduk = pot.getProduk().getNamaProduk();
                String tanggalAwal = DateHelper.dateParse(pot.getTanggalMulai(),"dd/MM/YYYY",indo);
                String tanggalAkhir = DateHelper.dateParse(pot.getTanggalAkhir(),"dd/MM/YYYY",indo);
                String pokokAwal = NumberFormat.getCurrencyInstance(indo).format(pot.getPokokHutangAwal());
                String pokokAkhir = NumberFormat.getCurrencyInstance(indo).format(pot.getPokokHutangAkhir());
                String tenor = String.valueOf(pot.getTenor());
                String effectifRate = String.format("%.1f", pot.getEffectRate());
                PotIndexDTO potIndexDTO = new PotIndexDTO();
                potIndexDTO.setId(id);
                potIndexDTO.setNamaPot(namaPot);
                potIndexDTO.setNamaProduk(namaProduk);
                potIndexDTO.setTanggalAwal(tanggalAwal);
                potIndexDTO.setTanggalAkhir(tanggalAkhir);
                potIndexDTO.setPokokHutangAwal(pokokAwal);
                potIndexDTO.setPokokHutangAkhir(pokokAkhir);
                potIndexDTO.setTenor(tenor);
                potIndexDTO.setEffectiveRate(effectifRate);
                gridPOT.add(potIndexDTO);
            }
        }
        return gridPOT;
    }


    @Override
    public int getTotal(String filter, String search) {
        double totalRows = 0;
        if(filter.equals("null") || filter.isBlank()){
            totalRows = repository.countAll();
        }else {
            switch (filter){
                case "id":
                    totalRows = repository.countById(search);
                    break;
                case "namaPot":
                    totalRows = repository.countByNamaPot(search);
                    break;
                case "namaProduk":
                    totalRows = repository.countByNamaProduk(search);
                    break;
                case "tanggalAwal":
                    totalRows = repository.countByTanggalAwal(search);
                    break;
                case "tanggalAkhir":
                    totalRows = repository.countByTanggalAkhir(search);
                    break;
                case "pokokAwal":
                    totalRows = repository.countByPokokAwal(search);
                    break;
                case "pokokAkhir":
                    totalRows = repository.countByPokokAkhir(search);
                    break;
                case "tenor":
                    totalRows = repository.countByTenor(search);
                    break;
                case "effectiveRate":
                    totalRows = repository.countByEffectRate(search);
                    break;
            }
        }
        return (int)(Math.ceil(totalRows/rowInPage));
    }

    @Override
    public List<OptionDTO> getfilterAsItem() {
        return List.of(
                new OptionDTO("Kode POT", "id"),
                new OptionDTO("Nama POT", "namaPot"),
                new OptionDTO("Nama Produk", "namaProduk"),
                new OptionDTO("Tanggal Awal Berlaku", "tanggalAwal"),
                new OptionDTO("Tanggal Akhir Berlaku", "tanggalAkhir"),
                new OptionDTO("Pokok Hutang Awal", "pokokAwal"),
                new OptionDTO("Pokok Hutang Akhir", "pokokAkhir"),
                new OptionDTO("Tenor", "tenor"),
                new OptionDTO("Effective Rate", "effectiveRate")
        );
    }

    @Override
    public void savePOT(PotFormDTO dto) {
        POT pot = new POT();
        pot.setId(dto.getId());
        pot.setNamaPOT(dto.getNamaPot());
        pot.setIdProduk(dto.getIdProduk());
        pot.setIdKriteriaPaket(dto.getIdKriteriaPaket());
        pot.setTanggalMulai(dto.getTanggalMulai());
        pot.setTanggalAkhir(dto.getTanggalAkhir());
        var pokokAwal = new BigDecimal(dto.getPokokHutangAwal());
        var pokokAkhir = new BigDecimal(dto.getPokokHutangAkhir());
        pot.setPokokHutangAwal(pokokAwal);
        pot.setPokokHutangAkhir(pokokAkhir);
        pot.setIdInterval(dto.getIdIntevalPembayaran());
        pot.setTenor(Integer.parseInt(dto.getTenor()));
        pot.setEffectRate(Double.parseDouble(dto.getEffectiveRate()));
        var nilaiAdmin = new BigDecimal(dto.getNilaiAdmin());
        var nilaiProvisi = new BigDecimal(dto.getNilaiProvisi());
        pot.setNilaiAdmin(nilaiAdmin);
        pot.setNilaiProvisi(nilaiProvisi);
        pot.setDp(Integer.parseInt(dto.getDp()));
        pot.setStatusMerchandise(dto.getStatusMerchandise());
        pot.setIdKategori(dto.getIdKategori());
        pot.setIdMerk(dto.getIdMerk());
        pot.setIdTipe(dto.getIdTipe());
        pot.setIdModel(dto.getIdModel());
        repository.save(pot);
    }

    @Override
    public void deletePot(Integer potId) {
        var pot = repository.findById(potId).orElseThrow();
        pot.setDeleteDate(LocalDate.now());
        repository.save(pot);
    }

    @Override
    public PotDetailDTO getPotByIdDetail(Integer potId) {
        var pot = repository.findById(potId).orElseThrow();
        PotDetailDTO dto = new PotDetailDTO();
        dto.setId(pot.getId());
        return dto;
    }

    @Override
    public List<OptionDTO> getCabangByPotId(Integer id) {
        var pot = repository.findById(id).orElseThrow();
        List<OptionDTO> produkList = new LinkedList<>();
        for (Cabang cabangList: pot.getCabangSet()){
            OptionDTO cabang = new OptionDTO();
            cabang.setValue(String.valueOf(cabangList.getId()));
            cabang.setText(cabangList.getNamaCabang());
            produkList.add(cabang);
        }
        return produkList;
    }

    @Override
    public List<OptionDTO> getSearchCabangItems(String filter) {
        List<String > searchItems = new LinkedList<>();
        if(!filter.isBlank()){
            switch (filter){
                case "id":
                    searchItems = repository.getItemsId();
                    break;
                case "namaPot":
                    searchItems = repository.getItemsNamaPot();
                    break;
                case "namaProduk":
                    searchItems = repository.getItemsNamaProduk();
                    break;
                case "tanggalAwal":
                    searchItems = repository.getItemsTanggalAwal();
                    break;
                case "tanggalAkhir":
                    searchItems = repository.getItemsTanggalAkhir();
                    break;
                case "pokokAwal":
                    searchItems = repository.getItemsPokokAwal();
                    break;
                case "pokokAkhir":
                    searchItems = repository.getItemsPokokAkhir();
                    break;
                case "tenor":
                    searchItems = repository.getItemsTenor();
                    break;
                case "effectiveRate":
                    searchItems = repository.getItemsEffectRate();
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
}
