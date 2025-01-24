package com.sales.service.mitraAgen;

import com.sales.dto.mitraAgen.*;
import com.sales.entity.*;
import com.sales.helper.DateHelper;
import com.sales.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Service
public class MitraAgenServiceImplementation implements MitraAgenService{
    private final MitraAgenRepository repository;
    private final ProdukRepository produkRepository;
    private final TipeMasterRepository tipeMasterRepository;
    private final KelurahanRepository kelurahanRepository;
    private final IdentitasRepository identitasRepository;
    private final BankRepository bankRepository;
    private final CabangRepository cabangRepository;
    private final int rowInPage = 1;

    @Autowired
    public MitraAgenServiceImplementation(MitraAgenRepository repository, ProdukRepository produkRepository, TipeMasterRepository tipeMasterRepository, KelurahanRepository kelurahanRepository, IdentitasRepository identitasRepository, BankRepository bankRepository, CabangRepository cabangRepository) {
        this.repository = repository;
        this.produkRepository = produkRepository;
        this.tipeMasterRepository = tipeMasterRepository;
        this.kelurahanRepository = kelurahanRepository;
        this.identitasRepository = identitasRepository;
        this.bankRepository = bankRepository;
        this.cabangRepository = cabangRepository;
    }

    @Override
    public int getTotalPages(String filter, String search) {
        double page = 0;
        if (filter.isEmpty()){
            page = repository.getTotalPages();
        } else {
            switch (filter){
                case "id":
                    page = repository.getTotalPagesById(search);
                    break;
                case "tipe":
                    page = repository.getTotalpagesByTipe(search);
                    break;
                case "name":
                    page = repository.getTotalpagesByName(search);
                    break;
                case "kelurahan":
                    page = repository.getTotalpagesByKelurahan(search);
                    break;
                case "cabang":
                    page = repository.getTotalpagesByCabang(search);
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
    public List<MitraAgenIndexDTO> getAll(int page, String filter, String search) {
        int totalPages = getTotalPages(filter, search);
        if (page < 1) {
            page = 1;
        }
        if (page > totalPages && totalPages > 0){
            page = totalPages;
        }
        Pageable pageable = PageRequest.of(page - 1, rowInPage, Sort.by("id"));

        List<MitraAgen> mitraAgenList = new LinkedList<>();
        if (filter.isEmpty()){
            mitraAgenList = repository.getAllMitraAgen(pageable);
        } else {
            switch (filter){
                case "id":
                    mitraAgenList = repository.getMitraAgenById(pageable, search);
                    break;
                case "tipe":
                    mitraAgenList = repository.getMitraAgenByTipe(pageable, search);
                    break;
                case "name":
                    mitraAgenList = repository.getMitraAgenByName(pageable, search);
                    break;
                case "kelurahan":
                    mitraAgenList = repository.getMitraAgenByKelurahan(pageable, search);
                    break;
                case "cabang":
                    mitraAgenList = repository.getMitraAgenByCabang(pageable, search);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(true) : String.valueOf(false);
                    mitraAgenList = repository.getMitraAgenByStatus(pageable, Boolean.parseBoolean(search));
                    break;
            }
        }

        List<MitraAgenIndexDTO> mitraAgenIndexDTOS = new LinkedList<>();
        for (MitraAgen mitraAgen : mitraAgenList){
            MitraAgenIndexDTO mitraAgenIndexDTO = new MitraAgenIndexDTO();
            mitraAgenIndexDTO.setTipe(mitraAgen.getTipeMasterMitraAgen().getNamaTipeMaster());
            mitraAgenIndexDTO.setKode(mitraAgen.getId());
            mitraAgenIndexDTO.setNama(mitraAgen.getNamaMitraAgen());
            mitraAgenIndexDTO.setKelurahan(mitraAgen.getKelurahanDomisiliMitraAgen().getNamaKelurahan());
            mitraAgenIndexDTO.setCabang(mitraAgen.getCabangMitraAgen().getNamaCabang());
            String statusMitraAgen = !mitraAgen.getStatus() ? "Tidak Aktif" : "Aktif";
            mitraAgenIndexDTO.setStatus(statusMitraAgen);

            mitraAgenIndexDTOS.add(mitraAgenIndexDTO);
        }
        return mitraAgenIndexDTOS;
    }

    @Override
    public MitraAgenFormDTO getMitraAgenById(String id) {
        MitraAgenFormDTO mitraAgenFormDTO = new MitraAgenFormDTO();
        if (!id.isEmpty()){
            try {
                MitraAgen mitraAgen = repository.findById(id).orElseThrow();
                mitraAgenFormDTO.setId(mitraAgen.getId());
                mitraAgenFormDTO.setIdTipeMaster(mitraAgen.getIdTipeMaster());
                mitraAgenFormDTO.setNamaTipeMaster(mitraAgen.getTipeMasterMitraAgen().getNamaTipeMaster());
                mitraAgenFormDTO.setNamaProduk(mitraAgen.getProdukMitraAgen().getNamaProduk());
                mitraAgenFormDTO.setIdProduk(mitraAgen.getIdProduk());
                mitraAgenFormDTO.setIdCabang(mitraAgen.getIdCabang());
                mitraAgenFormDTO.setNamaCabang(mitraAgen.getCabangMitraAgen().getNamaCabang());
                // Data Pribadi
                mitraAgenFormDTO.setIdIdentitas(mitraAgen.getIdIdentitas());
//                mitraAgenFormDTO.setNamaIdentitas(mitraAgen.getIdentitasMitraAgen().getNamaIdentitas());
                mitraAgenFormDTO.setNomorIdentitas(mitraAgen.getNomorIdentitas());
                mitraAgenFormDTO.setNama(mitraAgen.getNamaMitraAgen());
                mitraAgenFormDTO.setJenisKelamin(mitraAgen.getJenisKelamin());
                mitraAgenFormDTO.setNpwp(mitraAgen.getNpwp());
                mitraAgenFormDTO.setAlamatIdentitas(mitraAgen.getAlamatIdentitas());
                mitraAgenFormDTO.setKodeposDomisili(mitraAgen.getKelurahanDomisiliMitraAgen().getKodePos());
                mitraAgenFormDTO.setKodeposIdentitas(mitraAgen.getKelurahanIdentitasMitraAgen().getKodePos());
                mitraAgenFormDTO.setKecamatanDomisili(mitraAgen.getKelurahanDomisiliMitraAgen().getKecamatan().getNamaKecamatan());
                mitraAgenFormDTO.setKecamatanIdentitas(mitraAgen.getKelurahanIdentitasMitraAgen().getKecamatan().getNamaKecamatan());
                mitraAgenFormDTO.setKotaDomisili(mitraAgen.getKelurahanDomisiliMitraAgen().getKabupaten().getNamaKabupatenKota());
                mitraAgenFormDTO.setKotaIdentitas(mitraAgen.getKelurahanIdentitasMitraAgen().getKabupaten().getNamaKabupatenKota());
                mitraAgenFormDTO.setProvinsiDomisili(mitraAgen.getKelurahanDomisiliMitraAgen().getProvinsi().getNamaProvinsi());
                mitraAgenFormDTO.setProvinsiIdentitas(mitraAgen.getKelurahanIdentitasMitraAgen().getProvinsi().getNamaProvinsi());
                mitraAgenFormDTO.setAlamatDomisili(mitraAgen.getAlamatDomisili());
                mitraAgenFormDTO.setNamaKelurahanDomisili(mitraAgen.getKelurahanDomisiliMitraAgen().getNamaKelurahan());
                mitraAgenFormDTO.setNamaKelurahanIdentitas(mitraAgen.getKelurahanIdentitasMitraAgen().getNamaKelurahan());
                mitraAgenFormDTO.setTempatLahir(mitraAgen.getTempatLahir());
                mitraAgenFormDTO.setTanggalLahir(mitraAgen.getTanggalLahir());
                mitraAgenFormDTO.setNomorTelepon(mitraAgen.getNomorTelepon());
                mitraAgenFormDTO.setNomorHandPhone(mitraAgen.getNomorHandphone());
                // Bank
                mitraAgenFormDTO.setNomorRekening(mitraAgen.getNomorRekening());
                mitraAgenFormDTO.setNamaRekening(mitraAgen.getNamaRekening());
                mitraAgenFormDTO.setNamaBank(mitraAgen.getBankMitraAgen().getNamaBank());
                // Status
                mitraAgenFormDTO.setStatus(mitraAgen.getStatus());

                return mitraAgenFormDTO;
            } catch (Exception ignored){}
        }
        return mitraAgenFormDTO;
    }

    @Override
    public MitraAgenDetailDTO getDetailMitraAgenById(String id) {
        MitraAgenDetailDTO mitraAgenDetailDTO = new MitraAgenDetailDTO();
        if (!id.isEmpty()){
            try {
                MitraAgen mitraAgen = repository.findById(id).orElseThrow();
                mitraAgenDetailDTO.setId(mitraAgen.getId());
                mitraAgenDetailDTO.setTipe(mitraAgen.getTipeMasterMitraAgen().getNamaTipeMaster());
                mitraAgenDetailDTO.setProduk(mitraAgen.getProdukMitraAgen().getNamaProduk());
                mitraAgenDetailDTO.setCabang(mitraAgen.getCabangMitraAgen().getNamaCabang());
                // Data Pribadi
                String nomorIdentitas = String.format("%s - %s", mitraAgen.getIdentitasMitraAgen().getNamaIdentitas(), mitraAgen.getNomorIdentitas());
                mitraAgenDetailDTO.setNomorIdentitas(nomorIdentitas);
                mitraAgenDetailDTO.setNama(mitraAgen.getNamaMitraAgen());
                String jenisKelamin = mitraAgen.getJenisKelamin().equals("L") ? "Laki-laki" : "Perempuan";
                mitraAgenDetailDTO.setJenisKelamin(jenisKelamin);
                mitraAgenDetailDTO.setNpwp(mitraAgen.getNpwp());
                // Alamat Identitas
                mitraAgenDetailDTO.setAlamatIdentitas(mitraAgen.getAlamatIdentitas());
                mitraAgenDetailDTO.setKelurahanIdentitas(mitraAgen.getKelurahanIdentitasMitraAgen().getNamaKelurahan());
                mitraAgenDetailDTO.setKodePosIdentitas(mitraAgen.getKelurahanIdentitasMitraAgen().getKodePos());
                mitraAgenDetailDTO.setKecamatanIdentitas(mitraAgen.getKelurahanIdentitasMitraAgen().getKecamatan().getNamaKecamatan());
                mitraAgenDetailDTO.setKecamatanIdentitas(mitraAgen.getKelurahanIdentitasMitraAgen().getKecamatan().getNamaKecamatan());
                mitraAgenDetailDTO.setKotaIdentitas(mitraAgen.getKelurahanIdentitasMitraAgen().getKecamatan().getKabupaten().getNamaKabupatenKota());
                mitraAgenDetailDTO.setProvinsiIdentitas(mitraAgen.getKelurahanIdentitasMitraAgen().getKecamatan().getKabupaten().getProvinsi().getNamaProvinsi());
                // Alamat Domisili
                mitraAgenDetailDTO.setAlamatDomisili(mitraAgen.getAlamatDomisili());
                mitraAgenDetailDTO.setKelurahanDomisili(mitraAgen.getKelurahanDomisiliMitraAgen().getNamaKelurahan());
                mitraAgenDetailDTO.setKodePosDomisili(mitraAgen.getKelurahanDomisiliMitraAgen().getKodePos());
                mitraAgenDetailDTO.setKecamatanDomisili(mitraAgen.getKelurahanDomisiliMitraAgen().getKecamatan().getNamaKecamatan());
                mitraAgenDetailDTO.setKotaDomisili(mitraAgen.getKelurahanDomisiliMitraAgen().getKecamatan().getKabupaten().getNamaKabupatenKota());
                mitraAgenDetailDTO.setProvinsiDomisili(mitraAgen.getKelurahanDomisiliMitraAgen().getKecamatan().getKabupaten().getProvinsi().getNamaProvinsi());
                // TTL & Nomor Kontak
                mitraAgenDetailDTO.setTempatLahir(mitraAgen.getTempatLahir());
                mitraAgenDetailDTO.setTanggalLahir(DateHelper.dateParse(mitraAgen.getTanggalLahir(), "dd MMMM yyyy",  new Locale("id", "ID")));
                mitraAgenDetailDTO.setNomorTelepon(mitraAgen.getNomorTelepon());
                mitraAgenDetailDTO.setNomorHandphone(mitraAgen.getNomorHandphone());
                // Bank
                mitraAgenDetailDTO.setBank(mitraAgen.getBankMitraAgen().getNamaBank());
                mitraAgenDetailDTO.setNomorRekening(mitraAgen.getNomorRekening());
                mitraAgenDetailDTO.setNamaRekening(mitraAgen.getNamaRekening());

                String statusMitraAgen = !mitraAgen.getStatus() ? "Tidak Aktif" : "Aktif";
                mitraAgenDetailDTO.setStatus(statusMitraAgen);

            } catch (Exception ignored){}
        }
        return mitraAgenDetailDTO;
    }

    @Override
    public void save(MitraAgenFormDTO mitraAgenFormDTO) {
        MitraAgen mitraAgen = new MitraAgen();
        int idTipeMaster = tipeMasterRepository.getTipeMasterByName(mitraAgenFormDTO.getNamaTipeMaster()).getId();
        if (mitraAgenFormDTO.getId().isEmpty() || mitraAgenFormDTO.getId() == null){
            String idBaru = generateId(idTipeMaster);
            mitraAgen.setId(idBaru);
        } else {
            mitraAgen.setId(mitraAgenFormDTO.getId());
        }
        mitraAgen.setIdTipeMaster(idTipeMaster);
        mitraAgen.setIdProduk(produkRepository.getProdukByName(mitraAgenFormDTO.getNamaProduk()).getId());
        if (mitraAgenFormDTO.getIdCabang()==null){
            mitraAgen.setIdCabang(1);
        } else {
            mitraAgen.setIdCabang(cabangRepository.getCabangByName(mitraAgenFormDTO.getNamaCabang()).getId());
        }
        mitraAgen.setIdIdentitas(mitraAgenFormDTO.getIdIdentitas());
        mitraAgen.setNomorIdentitas(mitraAgenFormDTO.getNomorIdentitas());
        mitraAgen.setNamaMitraAgen(mitraAgenFormDTO.getNama());
        mitraAgen.setJenisKelamin(mitraAgenFormDTO.getJenisKelamin());
        mitraAgen.setNpwp(mitraAgenFormDTO.getNpwp());
        mitraAgen.setAlamatIdentitas(mitraAgenFormDTO.getAlamatIdentitas());
        mitraAgen.setIdKelurahanIdentitas(kelurahanRepository.getKelurahanByName(mitraAgenFormDTO.getNamaKelurahanIdentitas()).getId());
        mitraAgen.setAlamatDomisili(mitraAgenFormDTO.getAlamatDomisili());
        mitraAgen.setIdKelurahanDomisili(kelurahanRepository.getKelurahanByName(mitraAgenFormDTO.getNamaKelurahanDomisili()).getId());
        mitraAgen.setTempatLahir(mitraAgenFormDTO.getTempatLahir());
        mitraAgen.setTanggalLahir(mitraAgenFormDTO.getTanggalLahir());
        mitraAgen.setNomorTelepon(mitraAgenFormDTO.getNomorTelepon());
        mitraAgen.setNomorHandphone(mitraAgenFormDTO.getNomorHandPhone());
        mitraAgen.setIdBank(bankRepository.getBankByName(mitraAgenFormDTO.getNamaBank()).getId());
        mitraAgen.setNomorRekening(mitraAgenFormDTO.getNomorRekening());
        mitraAgen.setNamaRekening(mitraAgenFormDTO.getNamaRekening());
        mitraAgen.setStatus(mitraAgenFormDTO.getStatus());

        repository.save(mitraAgen);
    }

    @Override
    public void delete(String id) {
        if (!id.isEmpty()){
            try {
                MitraAgen mitraAgen = repository.findById(id).orElseThrow();
                mitraAgen.setDeleteDate(LocalDate.now());
                repository.save(mitraAgen);
            }catch (Exception ignored){}
        }
    }

    @Override
    public String generateId(int tipe) {
        String prefix = (tipe == 1) ? "MIT" : "AGN";
        String lastId = repository.getLastNumberById(prefix + "%");

        int lastNumber = 0;
        if (lastId != null && !lastId.isEmpty()){
            String numberPart = lastId.substring(3);
            lastNumber = Integer.parseInt(numberPart);
        }
        lastNumber++;

        String formattedNumber = String.format("%03d", lastNumber);

        return prefix + formattedNumber;
    }

    @Override
    public List<MitraAgenIndexOptionDTO> getFilterAsItem() {
        return List.of(
                new MitraAgenIndexOptionDTO("Tipe Master", "tipe"),
                new MitraAgenIndexOptionDTO("Kode Mitra/Agen", "id"),
                new MitraAgenIndexOptionDTO("Nama Mitra/Agen", "name"),
                new MitraAgenIndexOptionDTO("Kelurahan", "kelurahan"),
                new MitraAgenIndexOptionDTO("Cabang", "cabang"),
                new MitraAgenIndexOptionDTO("Status", "status")
        );
    }

    @Override
    public List<MitraAgenIndexOptionDTO> getSearchItems(String filter) {
        List<String> searchItems = new LinkedList<>();
        if (!filter.isEmpty()){
            switch (filter){
                case "id":
                    searchItems = repository.getMitraAgenItemsById();
                    break;
                case "tipe":
                    searchItems = repository.getMitraAgenItemsByTipe();
                    break;
                case "name":
                    searchItems = repository.getMitraAgenItemsByName();
                    break;
                case "kelurahan":
                    searchItems = repository.getMitraAgenByItemsKelurahan();
                    break;
                case "cabang":
                    searchItems = repository.getMitraAgenItemsByCabang();
                    break;
                case "status":
                    searchItems = repository.getMitraAgenItemsByStatus();
                    break;
            }
        }
        List<MitraAgenIndexOptionDTO> searchsItems = new LinkedList<>();
        for (String searchItem : searchItems){
            MitraAgenIndexOptionDTO mitraAgenIndexOptionDTO = new MitraAgenIndexOptionDTO(searchItem, searchItem);
            searchsItems.add(mitraAgenIndexOptionDTO);
        }
        return searchsItems;
    }


    @Override
    public List<TipeMasterOptionDTO> getTipeMasterOption() {
        List<TipeMaster> tipeMasterList = tipeMasterRepository.getAllTipeMaster();
        List<TipeMasterOptionDTO> tipeMasterOptionDTOS = new LinkedList<>();
        for (TipeMaster tipeMaster: tipeMasterList
        ) {
            TipeMasterOptionDTO tipeMasterOptionDTO = new TipeMasterOptionDTO();
            tipeMasterOptionDTO.setId(tipeMaster.getId());
            tipeMasterOptionDTO.setNamaTipeMaster(tipeMaster.getNamaTipeMaster());
            tipeMasterOptionDTOS.add(tipeMasterOptionDTO);
        }
        return tipeMasterOptionDTOS;
    }

    @Override
    public List<ProdukOptionDTO> getProdukOption() {
        List<Produk> produkList = produkRepository.getAllProduk();
        List<ProdukOptionDTO> produkOptionDTOS = new LinkedList<>();
        for (Produk produk: produkList
        ) {
            ProdukOptionDTO produkOptionDTO = new ProdukOptionDTO();
            produkOptionDTO.setId(produk.getId());
            produkOptionDTO.setNamaProduk(produk.getNamaProduk());
            produkOptionDTOS.add(produkOptionDTO);
        }
        return produkOptionDTOS;
    }

    @Override
    public List<KelurahanOptionDTO> getKelurahanOption() {
        List<Kelurahan> kelurahanList = kelurahanRepository.getAllKelurahan();
        List<KelurahanOptionDTO> kelurahanOptionDTOS = new LinkedList<>();
        for (Kelurahan kelurahan: kelurahanList
        ) {
            KelurahanOptionDTO kelurahanOptionDTO = new KelurahanOptionDTO();
            kelurahanOptionDTO.setId(kelurahan.getId());
            kelurahanOptionDTO.setNamaKelurahan(kelurahan.getNamaKelurahan());
            kelurahanOptionDTOS.add(kelurahanOptionDTO);
        }
        return kelurahanOptionDTOS;
    }

    @Override
    public List<IdentitasOptionDTO> getIdentitasOption() {
        List<Identitas> identitasList = identitasRepository.getAllIdentitas();
        List<IdentitasOptionDTO> identitasOptionDTOS = new LinkedList<>();
        for (Identitas identitas: identitasList
        ) {
            IdentitasOptionDTO identitasOptionDTO = new IdentitasOptionDTO();
            identitasOptionDTO.setId(identitas.getId());
            identitasOptionDTO.setNamaIdentitas(identitas.getNamaIdentitas());
            identitasOptionDTOS.add(identitasOptionDTO);
        }
        return identitasOptionDTOS;
    }

    @Override
    public List<BankOptionDTO> getBankOption() {
        List<Bank> bankList = bankRepository.getAllBank();
        List<BankOptionDTO> bankOptionDTOS = new LinkedList<>();
        for (Bank bank : bankList
        ) {
            BankOptionDTO bankOptionDTO = new BankOptionDTO();
            bankOptionDTO.setId(bank.getId());
            bankOptionDTO.setNamaBank(bank.getNamaBank());
            bankOptionDTOS.add(bankOptionDTO);
        }
        return bankOptionDTOS;
    }


}
