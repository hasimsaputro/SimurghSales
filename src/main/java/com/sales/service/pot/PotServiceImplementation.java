package com.sales.service.pot;


import com.sales.dto.OptionDTO;
import com.sales.dto.pot.PotFormDTO;
import com.sales.dto.pot.PotIndexDTO;
import com.sales.entity.POT;
import com.sales.helper.DateHelper;
import com.sales.repository.PotRepository;
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
            BigDecimal pokokAwal = pot.getPokokHutangAwal();
            BigDecimal pokokAkhir = pot.getPokokHutangAkhir();
            Integer idInterval = pot.getIdInterval();
            Integer tenor = pot.getTenor();
            Double effectiveRate = pot.getEffectRate();
            BigDecimal nilaiAdmin = pot.getNilaiAdmin();
            BigDecimal nilaiProvisi = pot.getNilaiProvisi();
            Integer dp = pot.getDp();
            Boolean statusMerchandise = pot.getStatusMerchandise();
            Integer idKategori = pot.getIdKategori();
            String idMerk = pot.getIdMerk();
            String idTipe = pot.getIdTipe();
            String idModel = pot.getIdModel();
            potFormDTO.setId(id);
            potFormDTO.setNamaPot(namaPot);
            potFormDTO.setIdProduk(idProduk);
            potFormDTO.setIdKriteriaPaket(idKriteriaPaket);
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
                    potList = repository.getByTanggalAkhir(search, pagination);
                    break;
                case "pokokAkhir":
                    potList = repository.getByTanggalAkhir(search, pagination);
                    break;
                case "tenor":
                    potList = repository.getByTanggalAkhir(search, pagination);
                    break;
                case "effectiveRate":
                    potList = repository.getByTanggalAkhir(search, pagination);
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
        return 0;
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
        pot.setPokokHutangAwal(dto.getPokokHutangAwal());
        pot.setPokokHutangAkhir(dto.getPokokHutangAkhir());
        pot.setIdInterval(dto.getIdIntevalPembayaran());
        pot.setTenor(dto.getTenor());
        pot.setEffectRate(dto.getEffectiveRate());
        pot.setNilaiAdmin(dto.getNilaiAdmin());
        pot.setNilaiProvisi(dto.getNilaiProvisi());
        pot.setDp(dto.getDp());
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
    public Object getPotByIdDetail(String potId) {
        return null;
    }
}
