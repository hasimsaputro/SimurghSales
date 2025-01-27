package com.sales.service.dataLeads;

import com.sales.dto.OptionDTO;
import com.sales.dto.dataLeads.*;
import com.sales.entity.*;
import com.sales.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class DataLeadsServiceImplementation implements DataLeadsService{
    private final ProdukRepository produkRepository;
    private final KeteranganAplikasiRepository keteranganAplikasiRepository;
    private final ModelRepository modelRepository;
    private final TipeRepository tipeRepository;
    private final MerkRepository merkRepository;
    private final KategoriRepository kategoriRepository;
    private final MitraAgenRepository mitraAgenRepository;
    private final DataLeadsRepository repository;
    private final UserRepository userRepository;
    private final KelurahanRepository kelurahanRepository;
    private final PotRepository potRepository;
    private final HargaPasarRepository hargaPasarRepository;
    private final Integer rowInPage = 10;

    public DataLeadsServiceImplementation(ProdukRepository produkRepository, HargaPasarRepository hargaPasarRepository, KeteranganAplikasiRepository keteranganAplikasiRepository, ModelRepository modelRepository, TipeRepository tipeRepository, MerkRepository merkRepository, KategoriRepository kategoriRepository, MitraAgenRepository mitraAgenRepository, DataLeadsRepository repository, UserRepository userRepository, KelurahanRepository kelurahanRepository, PotRepository potRepository) {
        this.produkRepository = produkRepository;
        this.keteranganAplikasiRepository = keteranganAplikasiRepository;
        this.modelRepository = modelRepository;
        this.tipeRepository = tipeRepository;
        this.merkRepository = merkRepository;
        this.kategoriRepository = kategoriRepository;
        this.mitraAgenRepository = mitraAgenRepository;
        this.repository = repository;
        this.userRepository = userRepository;
        this.kelurahanRepository = kelurahanRepository;
        this.potRepository = potRepository;
        this.hargaPasarRepository = hargaPasarRepository;
    }


    @Override
    public List<DataLeadsIndexDTO> getAll(String filter, String search, int page) {
        Pageable pagination = PageRequest.of(page-1,rowInPage, Sort.by("id"));
        List<DataLeads> dataLeads = new LinkedList<>();
        if(filter.equals("null") || filter.isBlank()){
            dataLeads = repository.getAll( pagination);
        }else {
            switch (filter){
                case "id":
                    dataLeads = repository.getByIdDataLeads(search, pagination);
                    break;
                case "nomorAplikasi":
                    dataLeads = repository.getByNomorAplikasi(search,pagination);
                    break;
                case "namaDebitur":
                    dataLeads = repository.getByDebitur(search,pagination);
                    break;
                case "tipeAplikasi":
                    dataLeads = repository.getByTipeAplikasi(search, pagination);
                    break;
                case "keterangan":
                    dataLeads = repository.getByKeterangan(search, pagination);
                    break;
                case "status":
                    search = search.equalsIgnoreCase("Aktif") ? String.valueOf(1) : String.valueOf(0) ;
                    dataLeads = repository.getByStatus(Boolean.parseBoolean(search),pagination);
                    break;
            }
        }
        var gridDataLeads = new LinkedList<DataLeadsIndexDTO>();
        for (var datalead :dataLeads) {
            if(datalead.getDeleteDate() == null){
                String id = datalead.getId();
                String nomorAplikasi = datalead.getNomorAplikasi();
                String namaDebitur = datalead.getDebiturDataLeads().getNamaDepan()+" "+datalead.getDebiturDataLeads().getNamaTengah()+" "+datalead.getDebiturDataLeads().getNamaAkhir();
                String tipeAplikasi = datalead.getTipeAplikasiDataLeads().getNamaTipeAplikasi();
                String keterangan = datalead.getKeteranganAplikasi().getNamaKeteranganAplikasi();
                String status = datalead.getStatus() ? "Aktif":"Tidak Akrif";
                DataLeadsIndexDTO dataLeadsIndexDTO = new DataLeadsIndexDTO();
                dataLeadsIndexDTO.setNomorDataLeads(id);
                dataLeadsIndexDTO.setNomorAplikasi(nomorAplikasi);
                dataLeadsIndexDTO.setNamaDebitur(namaDebitur);
                dataLeadsIndexDTO.setTipeAplikasi(tipeAplikasi);
                dataLeadsIndexDTO.setKeterangan(keterangan);
                dataLeadsIndexDTO.setStatus(status);
                gridDataLeads.add(dataLeadsIndexDTO);
            }
        }
        return gridDataLeads;
    }

    @Override
    public DataLeadsFormDTO getDataLeadsById(String dataLeadsId) {
        DataLeads dataLeads = repository.getDataLeadsById(dataLeadsId);
        DataLeadsFormDTO dto = new DataLeadsFormDTO();

        if(dataLeads != null){

            dto.setId(dataLeads.getId());
            dto.setIdProduk(dataLeads.getProdukDataleads().getNamaProduk());
            dto.setTipeDebitur(dataLeads.getTipeDebitur() == "PU" ? true : false);
            dto.setTipeAplikasi(dataLeads.getTipeAplikasiDataLeads().getNamaTipeAplikasi()==null ? "":dataLeads.getTipeAplikasiDataLeads().getNamaTipeAplikasi());
            dto.setNamaDepanDebitur(dataLeads.getDebiturDataLeads().getNamaDepan());
            dto.setNamaTengahDebitur(dataLeads.getDebiturDataLeads().getNamaTengah());
            dto.setNamaBelakangDebitur(dataLeads.getDebiturDataLeads().getNamaAkhir());
            dto.setIdIdentitas(dataLeads.getDebiturDataLeads().getIdIdentitas());
            dto.setNomorIdentitas(dataLeads.getDebiturDataLeads().getNomorIdentitas());
            dto.setJenisKelamin(dataLeads.getDebiturDataLeads().getJenisKelamin());
            dto.setAlamatIdentitas(dataLeads.getDebiturDataLeads().getAlamatIdentitas());
            dto.setKelurahan(dataLeads.getDebiturDataLeads().getKelurahan().getNamaKelurahan());
            dto.setKodePos(dataLeads.getDebiturDataLeads().getKelurahan().getKodePos());
            dto.setKecamatan(dataLeads.getDebiturDataLeads().getKelurahan().getKecamatan().getNamaKecamatan());
            dto.setKotaKabupaten(dataLeads.getDebiturDataLeads().getKelurahan().getKecamatan().getKabupaten().getNamaKabupatenKota());
            dto.setProvinsi(dataLeads.getDebiturDataLeads().getKelurahan().getKecamatan().getKabupaten().getProvinsi().getNamaProvinsi());
            dto.setAlamatDomisili(dataLeads.getDebiturDataLeads().getAlamatDomisili());
            dto.setKelurahanDomisili(dataLeads.getDebiturDataLeads().getKelurahan().getNamaKelurahan());
            dto.setKodePosDomisili(dataLeads.getDebiturDataLeads().getKelurahanDomisili().getKodePos());
            dto.setKecamatanDomisili(dataLeads.getDebiturDataLeads().getKelurahanDomisili().getKecamatan().getNamaKecamatan());
            dto.setKotaKabupatenDomisili(dataLeads.getDebiturDataLeads().getKelurahanDomisili().getKecamatan().getKabupaten().getNamaKabupatenKota());
            dto.setProvinsiDomisili(dataLeads.getDebiturDataLeads().getKelurahanDomisili().getKecamatan().getProvinsi().getNamaProvinsi());
            //Kurang Nama Cabang Tujuan
            dto.setNomorHandphone1(dataLeads.getDebiturDataLeads().getNomorHp1());
            dto.setNomorHandphone2(dataLeads.getDebiturDataLeads().getNomorHP2());
            dto.setNomorTelepon(dataLeads.getDebiturDataLeads().getNomorTelepon());
            dto.setSumberDataAplikasi(dataLeads.getMitraAgenDataLeads().getNamaMitraAgen());
            dto.setReferensi(dataLeads.getIdDebiturReferensi()== null? "" : dataLeads.getDebiturReferensiDataLeads().getNamaDepan().concat(" ").concat(dataLeads.getDebiturReferensiDataLeads().getNamaTengah()).concat(" ").concat(dataLeads.getDebiturReferensiDataLeads().getNamaAkhir()));
            dto.setJenisUsaha(dataLeads.getJenisUsaha());
            dto.setKeteranganAplikasi(dataLeads.getKeteranganAplikasi().getNamaKeteranganAplikasi());
            dto.setSurveyor(userRepository.getUserByCabangAndSurveyor(dataLeads.getIdCabang()) == null ? "Cabang Tidak Ada Surveyor" :  userRepository.getUserByCabangAndSurveyor(dataLeads.getIdCabang()).getNamaKaryawan());
            dto.setStatus(dataLeads.getStatus());
            dto.setPot(dataLeads.getPotDataLeads().getNamaPOT());
            dto.setTenor(dataLeads.getPotDataLeads().getTenor().toString());
            //Kurang Estimasi Nilai Faunding dan Data Unit
            //dan unit
        }

        return dto;
    }

    @Override
    public DataLeadsDetailDTO getDataLeadsByIdDetail(String dataLeadsId) {
        DataLeads dataLeads = repository.getDataLeadsById(dataLeadsId);
        DataLeadsDetailDTO dto = new DataLeadsDetailDTO();

        if(dataLeads != null){

            dto.setId(dataLeads.getId());
            dto.setIdProduk(dataLeads.getIdProduk());
            dto.setTipeDebitur(Boolean.TRUE.equals(dataLeads.getTipeDebitur()) ? "PU" : "PO");
            dto.setTipeAplikasi(dataLeads.getTipeAplikasiDataLeads().getNamaTipeAplikasi()==null ? "":dataLeads.getTipeAplikasiDataLeads().getNamaTipeAplikasi());
            dto.setNamaDepanDebitur(dataLeads.getDebiturDataLeads().getNamaDepan());
            dto.setNamaTengahDebitur(dataLeads.getDebiturDataLeads().getNamaTengah());
            dto.setNamaBelakangDebitur(dataLeads.getDebiturDataLeads().getNamaAkhir());
            switch (dataLeads.getDebiturDataLeads().getIdIdentitas()){
                case 1:
                    dto.setIdIdentitas("KTP - ");
                    break;
                case 2:
                    dto.setIdIdentitas("SIM - ");
                    break;
                case 3:
                    dto.setIdIdentitas("Passport - ");
                default:
                    dto.setIdIdentitas(" ");
                    break;
            }
            dto.setNomorIdentitas(dataLeads.getDebiturDataLeads().getNomorIdentitas());
            dto.setJenisKelamin(dataLeads.getDebiturDataLeads().getJenisKelamin());
            dto.setAlamatIdentitas(dataLeads.getDebiturDataLeads().getAlamatIdentitas());
            dto.setKelurahan(dataLeads.getDebiturDataLeads().getKelurahan().getNamaKelurahan());
            dto.setKodePos(dataLeads.getDebiturDataLeads().getKelurahan().getKodePos());
            dto.setKecamatan(dataLeads.getDebiturDataLeads().getKelurahan().getKecamatan().getNamaKecamatan());
            dto.setKotaKabupaten(dataLeads.getDebiturDataLeads().getKelurahan().getKecamatan().getKabupaten().getNamaKabupatenKota());
            dto.setProvinsi(dataLeads.getDebiturDataLeads().getKelurahan().getKecamatan().getKabupaten().getProvinsi().getNamaProvinsi());
            dto.setAlamatDomisili(dataLeads.getDebiturDataLeads().getAlamatDomisili());
            dto.setKelurahanDomisili(dataLeads.getDebiturDataLeads().getKelurahan().getNamaKelurahan());
            dto.setKodePosDomisili(dataLeads.getDebiturDataLeads().getKelurahanDomisili().getKodePos());
            dto.setKecamatanDomisili(dataLeads.getDebiturDataLeads().getKelurahanDomisili().getKecamatan().getNamaKecamatan());
            dto.setKotaKabupatenDomisili(dataLeads.getDebiturDataLeads().getKelurahanDomisili().getKecamatan().getKabupaten().getNamaKabupatenKota());
            dto.setProvinsiDomisili(dataLeads.getDebiturDataLeads().getKelurahanDomisili().getKecamatan().getProvinsi().getNamaProvinsi());
            //Kurang Nama Cabang Tujuan
            dto.setNomorHandphone1(dataLeads.getDebiturDataLeads().getNomorHp1());
            dto.setNomorHandphone2(dataLeads.getDebiturDataLeads().getNomorHP2());
            dto.setNomorTelepon(dataLeads.getDebiturDataLeads().getNomorTelepon());
            dto.setSumberDataAplikasi(dataLeads.getMitraAgenDataLeads().getNamaMitraAgen());
            dto.setReferensi(dataLeads.getIdDebiturReferensi()== null? "" : dataLeads.getDebiturReferensiDataLeads().getNamaDepan().concat(" ").concat(dataLeads.getDebiturReferensiDataLeads().getNamaTengah()).concat(" ").concat(dataLeads.getDebiturReferensiDataLeads().getNamaAkhir()));
            dto.setJenisUsaha(dataLeads.getJenisUsaha());
            dto.setKeteranganAplikasi(dataLeads.getKeteranganAplikasi().getNamaKeteranganAplikasi());
            dto.setSurveyor(userRepository.getUserByCabangAndSurveyor(dataLeads.getIdCabang()) == null ? "Cabang Tidak Ada Surveyor" :  userRepository.getUserByCabangAndSurveyor(dataLeads.getIdCabang()).getNamaKaryawan());
            dto.setStatus(dataLeads.getStatus());
            dto.setPot(dataLeads.getPotDataLeads().getNamaPOT());
            dto.setTenor(dataLeads.getPotDataLeads().getTenor().toString());
            //Kurang Estimasi Nilai Faunding dan Data Unit
            //dan unit
        }

        return dto;
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
                case "nomorAplikasi":
                    totalRows = repository.countByNomorAplikasi(search);
                    break;
                case "namaDebitur":
                    totalRows = repository.countByDebitur(search);
                    break;
                case "tipeAplikasi":
                    totalRows = repository.countByTipeAplikasi(search);
                    break;
                case "keterangan":
                    totalRows = repository.countByKeterangan(search);
                    break;
                case "status":
                    totalRows = repository.countByStatus(search);
                    break;
            }
        }
        return (int)(Math.ceil(totalRows/rowInPage));
    }

    @Override
    public List<OptionDTO> getfilterAsItem() {
        return List.of(
                new OptionDTO("Nomor Data Leads", "id"),
                new OptionDTO("Nomor Aplikasi", "nomorAplikasi"),
                new OptionDTO("Nama Debitur", "namaDebitur"),
                new OptionDTO("Tipe Aplikasi", "tipeAplikasi"),
                new OptionDTO("Keterangan", "keterangan"),
                new OptionDTO("Status", "status")
        );
    }

    @Override
    public void deleteDataLeads(String dataLeadsId) {
        var dataLeads = repository.findById(dataLeadsId).orElseThrow();
        dataLeads.setDeleteDate(LocalDate.now());
        repository.save(dataLeads);
    }

    @Override
    public void updateInsertDataLeads(DataLeadsFormDTO dataLeadsFormDTO) {
        DataLeads dataLeads = repository.getDataLeadsById(dataLeadsFormDTO.getId());
        Boolean isexist = dataLeads.getId() == null ? false : true;

        if(isexist == false){
            dataLeads = new DataLeads();
            List<DataLeads> listUrutan = repository.getAllOnly();

            String newId;
            if (!listUrutan.isEmpty()) {
                DataLeads lastDataLead = listUrutan.get(listUrutan.size() - 1);
                String lastId = lastDataLead.getId();

                if (lastId != null && lastId.startsWith("LEAD-")) {
                    try {
                        int lastNumber = Integer.parseInt(lastId.substring(5));
                        newId = String.format("LEAD-%05d", lastNumber + 1);
                    } catch (NumberFormatException e) {
                        newId = "LEAD-00001";
                    }
                } else {
                    newId = "LEAD-00001";
                }
            } else {
                newId = "LEAD-00001";
            }

            dataLeads.setId(newId);
            dataLeads.setIdProduk(produkRepository.getProdukByName(dataLeadsFormDTO.getIdProduk()).getId());
            dataLeads.setNomorAplikasi("000000");
            dataLeads.setTipeDebitur(dataLeadsFormDTO.getTipeDebitur() == true ? "PU" : "PO");



            dataLeads.setIdKeteranganAplikasi(keteranganAplikasiRepository.getKeteranganAplikasiByName(dataLeadsFormDTO.getKeteranganAplikasi()).getId());

        }


        repository.save(dataLeads);
    }

    @Override
    public List<OptionDTO> getOptionSumberDataAplikasi() {
        List<OptionDTO> optionDTOList = new LinkedList<>();
        List<MitraAgen> listSumberdataAplikasi = mitraAgenRepository.getAll();

        for(var mitraAgen : listSumberdataAplikasi){
            OptionDTO dto = new OptionDTO();
            dto.setText(mitraAgen.getNamaMitraAgen());
            dto.setValue(mitraAgen.getId());
            optionDTOList.add(dto);
        }
        return optionDTOList;
    }

    @Override
    public List<OptionDTO> getOptionReferensi() {
        return null;
    }

    @Override
    public List<OptionDTO> getOptionKeteranganAplikasi() {
        OptionDTO dto1 = new OptionDTO();
        OptionDTO dto2 = new OptionDTO();
        List<OptionDTO> listdto = new LinkedList<>();
        dto1.setValue("1");
        dto2.setValue("2");
        dto1.setText("Interest");
        dto2.setText("Prospect");
        listdto.add(dto1);
        listdto.add(dto2);

        return listdto;
    }

    @Override
    public List<OptionDTO> getOptionPOT() {
        return null;
    }

    @Override
    public List<OptionDTO> getOptionKategori() {
        List<OptionDTO> optionDTOList = new LinkedList<>();
        List<Kategori> list = kategoriRepository.getAll();

        for(var kategori : list){
            OptionDTO dto = new OptionDTO();
            dto.setText(kategori.getNamaKategori());
            dto.setValue(kategori.getId().toString());
            optionDTOList.add(dto);
        }
        return optionDTOList;
    }

    @Override
    public List<OptionDTO> getOptionMerek(Integer kategoriId) {
        List<OptionDTO> optionDTOList = new LinkedList<>();
////        List<Merk> list = merkRepository.getAll(kategoriId);
//
//        for(var merk : list){
//            OptionDTO dto = new OptionDTO();
//            dto.setText(merk.getNamaMerk());
//            dto.setValue(merk.getId());
//            optionDTOList.add(dto);
//        }
        return optionDTOList;
    }

    @Override
    public List<OptionDTO> getOptionTipe(Integer kategori, String merk) {
        List<OptionDTO> optionDTOList = new LinkedList<>();
//        List<Tipe> list = tipeRepository.getAll(kategori,merk);
//
//        for(var tipe : list){
//            OptionDTO dto = new OptionDTO();
//            dto.setText(tipe.getNamaTipe());
//            dto.setValue(tipe.getId());
//            optionDTOList.add(dto);
//        }
        return optionDTOList;
    }

    @Override
    public List<OptionDTO> getOptionModel(Integer kategori, String merk, String tipe) {
        List<OptionDTO> optionDTOList = new LinkedList<>();
//        List<Model> list = modelRepository.getAll(kategori,merk,tipe);
//
//        for(var model : list){
//            OptionDTO dto = new OptionDTO();
//            dto.setText(model.getNamaModel());
//            dto.setValue(model.getId());
//            optionDTOList.add(dto);
//        }
        return optionDTOList;
    }



    @Override
    public List<OptionDTO> getSearchItems(String filter) {
        List<String > searchItems = new LinkedList<>();
        if(!filter.isBlank()){
            switch (filter){
                case "id":
                    searchItems = repository.getItemsById();
                    break;
                case "nomorAplikasi":
                    searchItems = repository.getItemsByNomorAplikasi();
                    break;
                case "namaDebitur":
                    searchItems = repository.getItemsByDebitur();
                    break;
                case "tipeAplikasi":
                    searchItems = repository.getItemsByTipeAplikasi();
                    break;
                case "keterangan":
                    searchItems = repository.getItemsByKeterangan();
                    break;
                case "status":
                    searchItems = repository.getItemsByStatus();
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
    public List<OptionDTO> getKelurahanItems() {
        var kelurahanItems = kelurahanRepository.getItemsKelurahan();
        List<OptionDTO> kelurahans = new LinkedList<>();
        for (var kelurahanItem : kelurahanItems){
            OptionDTO item = new OptionDTO(kelurahanItem, kelurahanItem);
            kelurahans.add(item);
        }
        return kelurahans;
    }

    @Override
    public List<OptionDTO> getPotItems() {
        var potItems = potRepository.getItemsPot();
        List<OptionDTO> pots = new LinkedList<>();
        for (var potItem : potItems){
            OptionDTO item = new OptionDTO(potItem.getNamaPOT(),String.valueOf(potItem.getId()));
            pots.add(item);
        }
        return pots;
    }

    @Override
    public POTDataDTO getPotData(Integer idPOT) {
        POT potData = potRepository.getPotById(idPOT);
        POTDataDTO potDataDTO = new POTDataDTO();
        potDataDTO.setId(potData.getId());
        potDataDTO.setNamaPOT(potData.getNamaPOT());
        OptionDTO kategori = new OptionDTO();
        List<OptionDTO> merk = new LinkedList<>();
        List<OptionDTO> tipe = new LinkedList<>();
        List<OptionDTO> model = new LinkedList<>();
        kategori.setText(potData.getKategoriPOT().getNamaKategori());
        kategori.setValue(String.valueOf(potData.getIdKategori()));
        potDataDTO.setKategori(kategori);
        potDataDTO.setTenor(String.valueOf(potData.getTenor()));
        List<Merk> merkData;
        if(potData.getIdMerk() == null){
            merkData = merkRepository.getAll(potData.getIdKategori());
            for (var data : merkData){
                OptionDTO item = new OptionDTO(data.getNamaMerk(),String.valueOf(data.getId()));
                merk.add(item);
            }
            potDataDTO.setMerk(merk);
        }else {
            OptionDTO merks = new OptionDTO(potData.getMerkPOT().getNamaMerk(), potData.getIdMerk());
            merk.add(merks);
            potDataDTO.setMerk(merk);
        }
        List<Tipe> tipeData = new LinkedList<>();
        if(potData.getIdTipe() == null && potData.getIdMerk() == null){
            tipeData = tipeRepository.getAll(potData.getIdKategori());
            for (var data : tipeData){
                OptionDTO item = new OptionDTO(data.getNamaTipe(),String.valueOf(data.getId()));
                tipe.add(item);
            }
            potDataDTO.setTipe(tipe);
        } else if (potData.getIdTipe() == null) {
            tipeData = tipeRepository.getAllByMerk(potData.getIdKategori(), potData.getIdMerk());
            for (var data : tipeData){
                OptionDTO item = new OptionDTO(data.getNamaTipe(),String.valueOf(data.getId()));
                tipe.add(item);
            }
            potDataDTO.setTipe(tipe);
        }else {
            OptionDTO tipes = new OptionDTO(potData.getTipePOT().getNamaTipe(), potData.getIdTipe());
            tipe.add(tipes);
            potDataDTO.setTipe(tipe);
        }
        List<Model> modelData = new LinkedList<>();
        if(potData.getIdModel() == null && potData.getIdMerk() == null && potData.getIdTipe() == null){
            modelData = modelRepository.getAll(potData.getIdKategori());
            for (var data : modelData){
                OptionDTO item = new OptionDTO(data.getNamaModel(),String.valueOf(data.getId()));
                model.add(item);
            }
            potDataDTO.setModel(model);
        } else if (potData.getIdModel() == null && potData.getIdTipe() == null) {
            modelData = modelRepository.getAllByMerk(potData.getIdKategori(), potData.getIdMerk());
            for (var data : modelData){
                OptionDTO item = new OptionDTO(data.getNamaModel(),String.valueOf(data.getId()));
                model.add(item);
            }
            potDataDTO.setModel(model);
        }else if (potData.getIdModel() == null){
            modelData = modelRepository.getAllByMerkTipe(potData.getIdKategori(), potData.getIdMerk(), potData.getIdTipe());
            for (var data : modelData){
                OptionDTO item = new OptionDTO(data.getNamaModel(),String.valueOf(data.getId()));
                model.add(item);
            }
            potDataDTO.setModel(model);
        }else{
            OptionDTO models = new OptionDTO(potData.getModelPOT().getNamaModel(), potData.getIdModel());
            model.add(models);
            potDataDTO.setModel(model);
        }
        return potDataDTO;
    }

    @Override
    public String getEstimasiNilaiFunding(EstimasiNilaiFundingDTO dto) {
        POT potData = potRepository.getPotById(dto.getIdPot());
        var pokokHutang = hargaPasarRepository.getHarga(dto.getIdKategori(),dto.getIdMerk(),dto.getIdTipe(),dto.getIdModel());
        var biayaProvisi = potData.getNilaiProvisi();
        var biayaAsuransi = new BigDecimal("1187500");
        var dp = potData.getDp()/100;
        var totalDp = pokokHutang.multiply(BigDecimal.valueOf(dp+1));
        var totalEstimasi = pokokHutang.add(biayaProvisi).add(biayaAsuransi).subtract(totalDp);

        return String.valueOf(totalEstimasi);
    }

}
