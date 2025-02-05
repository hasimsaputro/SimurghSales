package com.sales.service.pot;


import com.sales.dto.OptionDTO;
import com.sales.dto.pot.CabangPotDTO;
import com.sales.dto.pot.PotDetailDTO;
import com.sales.dto.pot.PotFormDTO;
import com.sales.dto.pot.PotIndexDTO;
import com.sales.entity.Cabang;
import com.sales.entity.POT;
import com.sales.helper.DateHelper;
import com.sales.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class PotServiceImplementation implements PotService {
    private final PotRepository repository;
    private final ProdukRepository produkRepository;
    private final KriteriaPaketRepository kriteriaPaketRepository;
    private final IntevalPembayaranRepository intevalPembayaranRepository;
    private final KategoriRepository kategoriRepository;
    private final MerkRepository merkRepository;
    private final TipeRepository tipeRepository;
    private final ModelRepository modelRepository;
    private final CabangRepository cabangRepository;
    private final Integer rowInPage = 10;
    public final Locale indo = new Locale("id", "ID");

    public PotServiceImplementation(PotRepository repository, ProdukRepository produkRepository,
                                    KriteriaPaketRepository kriteriaPaketRepository, IntevalPembayaranRepository intevalPembayaranRepository,
                                    KategoriRepository kategoriRepository, MerkRepository merkRepository, TipeRepository tipeRepository, ModelRepository modelRepository, CabangRepository cabangRepository) {
        this.repository = repository;
        this.produkRepository = produkRepository;
        this.kriteriaPaketRepository = kriteriaPaketRepository;
        this.intevalPembayaranRepository = intevalPembayaranRepository;
        this.kategoriRepository = kategoriRepository;
        this.merkRepository = merkRepository;
        this.tipeRepository = tipeRepository;
        this.modelRepository = modelRepository;
        this.cabangRepository = cabangRepository;
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
            String pokokAwal = pot.getPokokHutangAwal().stripTrailingZeros().toPlainString();
            String pokokAkhir = pot.getPokokHutangAkhir().stripTrailingZeros().toPlainString();
            Integer idInterval = pot.getIdInterval();
            String tenor = String.valueOf(pot.getTenor());
            String effectiveRate = String.valueOf(pot.getEffectRate());
            String nilaiAdmin = pot.getNilaiAdmin().stripTrailingZeros().toPlainString();
            String nilaiProvisi = pot.getNilaiProvisi().stripTrailingZeros().toPlainString();
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
            potFormDTO.setNamaInterval(pot.getIntervalPembayaran().getNamaIntervalPembayaran());
            potFormDTO.setTenor(tenor);
            potFormDTO.setEffectiveRate(effectiveRate);
            potFormDTO.setNilaiAdmin(nilaiAdmin);
            potFormDTO.setNilaiProvisi(nilaiProvisi);
            potFormDTO.setDp(dp);
            potFormDTO.setStatusMerchandise(statusMerchandise);
            potFormDTO.setIdKategori(idKategori);
            potFormDTO.setNamaKategori(pot.getKategoriPOT().getNamaKategori());
            potFormDTO.setIdMerk(idMerk);
            potFormDTO.setNamaMerk(pot.getMerkPOT().getNamaMerk());
            potFormDTO.setIdTipe(idTipe);
            potFormDTO.setNamaTipe(pot.getTipePOT().getNamaTipe());
            potFormDTO.setIdModel(idModel);
            potFormDTO.setNamaModel(pot.getModelPOT().getNamaModel());
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
        if(dto.getId() != null){
            pot.setId(dto.getId());
        }
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
        Set<Cabang> cabangSet = new HashSet<>();
        var cabangList = dto.getCabangList();
        for (Integer kodeCabang : cabangList) {
            Cabang cabang = cabangRepository.findById(kodeCabang).orElseThrow();
            cabangSet.add(cabang);
            pot.setCabangSet(cabangSet);
        }
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
        dto.setNamaPot(pot.getNamaPOT());
        dto.setProduk(pot.getProduk().getNamaProduk());
        dto.setKriteriaPaket(pot.getIdKriteriaPaket()+" - "+pot.getKriteriaPaket().getNamaKriteria());
        dto.setTanggalMulai(DateHelper.dateParse(pot.getTanggalMulai(),"dd/MM/YYYY",indo));
        dto.setTanggalAkhir(DateHelper.dateParse(pot.getTanggalAkhir(),"dd/MM/YYYY",indo));
        dto.setPokokAwal(NumberFormat.getCurrencyInstance(indo).format(pot.getPokokHutangAwal()));
        dto.setPokokAkhir(NumberFormat.getCurrencyInstance(indo).format(pot.getPokokHutangAkhir()));
        dto.setInterval(pot.getIntervalPembayaran().getNamaIntervalPembayaran());
        dto.setTenor(String.valueOf(pot.getTenor()));
        dto.setEffectRate(String.format("%.1f", pot.getEffectRate()));
        dto.setNilaiAdmin(NumberFormat.getCurrencyInstance(indo).format(pot.getNilaiAdmin()));
        dto.setNilaiProvisi(NumberFormat.getCurrencyInstance(indo).format(pot.getNilaiProvisi()));
        dto.setDp(String.valueOf(pot.getDp()));
        dto.setStatusMerchandise(pot.getStatusMerchandise()?"Aktif":"Tidak Aktif");
        dto.setKategori(pot.getKategoriPOT().getNamaKategori());
        dto.setMerk(pot.getMerkPOT()==null?"ALL - ALL":pot.getIdMerk()+" - "+pot.getMerkPOT().getNamaMerk());
        dto.setTipe(pot.getTipePOT()==null?"ALL - ALL":pot.getIdTipe()+" - "+pot.getTipePOT().getNamaTipe());
        dto.setModel(pot.getModelPOT()==null?"ALL - ALL":pot.getIdModel()+" - "+pot.getModelPOT().getNamaModel());
        return dto;
    }

    @Override
    public List<CabangPotDTO> getCabangByPotId(Integer id) {
        var pot = repository.findById(id).orElseThrow();
        List<CabangPotDTO> cabangPotDTOList = new LinkedList<>();
        for (Cabang cabangList: pot.getCabangSet()){
            CabangPotDTO cabang = new CabangPotDTO();
            cabang.setId(String.valueOf(cabangList.getId()));
            cabang.setNamaCabang(cabangList.getNamaCabang());
            cabang.setTipeStruktur(cabangList.getTipeStruktur().getNamaStruktur());
            cabangPotDTOList.add(cabang);
        }
        return cabangPotDTOList;
    }

    @Override
    public List<OptionDTO> getSearchItems(String filter) {
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
    public List<OptionDTO> getProdukItems() {
        var produkItem = produkRepository.getAllProduk();
        List<OptionDTO> produkList = new LinkedList<>();
        for (var produk : produkItem){
            OptionDTO item = new OptionDTO(produk.getNamaProduk(),String.valueOf(produk.getId()));
            produkList.add(item);
        }
        return produkList;
    }

    @Override
    public List<OptionDTO> getKriteriaItems() {
        var kriteriaItem = kriteriaPaketRepository.getAllKriteria();
        List<OptionDTO> kriteriaList = new LinkedList<>();
        for (var kriteria : kriteriaItem){
            OptionDTO item = new OptionDTO(kriteria.getNamaKriteria(),String.valueOf(kriteria.getId()));
            kriteriaList.add(item);
        }
        return kriteriaList;
    }

    @Override
    public List<OptionDTO> getIntervalItems() {
        var intervalItem = intevalPembayaranRepository.getAllInterval();
        List<OptionDTO> interavalList = new LinkedList<>();
        for (var interval : intervalItem){
            OptionDTO item = new OptionDTO(interval.getNamaIntervalPembayaran(),String.valueOf(interval.getId()));
            interavalList.add(item);
        }
        return interavalList;
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
    public List<OptionDTO> getMerkItems() {
        var merkItem = merkRepository.getAllMerk();
        List<OptionDTO> merkList = new LinkedList<>();
        for (var merk : merkItem){
            OptionDTO item = new OptionDTO(merk.getNamaMerk(),String.valueOf(merk.getId()));
            merkList.add(item);
        }
        return merkList;
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
}
