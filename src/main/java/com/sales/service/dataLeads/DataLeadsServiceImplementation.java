package com.sales.service.dataLeads;

import com.sales.dto.OptionDTO;
import com.sales.dto.OptionKelurahanDTO;
import com.sales.dto.dataLeads.*;
import com.sales.dto.dataLeads.KeteranganAplikasiSurveyorDTO;
import com.sales.entity.*;
import com.sales.repository.*;
import com.sales.service.master.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Locale;

@Service
public class DataLeadsServiceImplementation implements DataLeadsService {
    private final ReferensiRepository referensiRepository;
    private final CabangRepository cabangRepository;
    private final KelurahanRepository kelurahanRepository;
    private final DebiturRepository debiturRepository;
    private final TipeAplikasiRepository tipeAplikasiRepository;
    private final ProdukRepository produkRepository;
    private final KeteranganAplikasiRepository keteranganAplikasiRepository;
    private final ModelRepository modelRepository;
    private final TipeRepository tipeRepository;
    private final MerkRepository merkRepository;
    private final MitraAgenRepository mitraAgenRepository;
    private final DataLeadsRepository repository;
    private final UserRepository userRepository;
    private final KelurahanRepository kelurahanRepository;
    private final PotRepository potRepository;
    private final HargaPasarRepository hargaPasarRepository;
    private final Integer rowInPage = 10;
    public Locale indo = new Locale("id", "ID");

    public DataLeadsServiceImplementation(ReferensiRepository referensiRepository, CabangRepository cabangRepository, DebiturRepository debiturRepository, TipeAplikasiRepository tipeAplikasiRepository, ProdukRepository produkRepository, HargaPasarRepository hargaPasarRepository, KeteranganAplikasiRepository keteranganAplikasiRepository, ModelRepository modelRepository, TipeRepository tipeRepository, MerkRepository merkRepository, KategoriRepository kategoriRepository, MitraAgenRepository mitraAgenRepository, DataLeadsRepository repository, UserRepository userRepository, KelurahanRepository kelurahanRepository, PotRepository potRepository) {
        this.referensiRepository = referensiRepository;
        this.cabangRepository = cabangRepository;
        this.kelurahanRepository = kelurahanRepository;
        this.debiturRepository = debiturRepository;
        this.tipeAplikasiRepository = tipeAplikasiRepository;
    public DataLeadsServiceImplementation(ProdukRepository produkRepository, HargaPasarRepository hargaPasarRepository, KeteranganAplikasiRepository keteranganAplikasiRepository, ModelRepository modelRepository, TipeRepository tipeRepository, MerkRepository merkRepository, MitraAgenRepository mitraAgenRepository, DataLeadsRepository repository, UserRepository userRepository, KelurahanRepository kelurahanRepository, PotRepository potRepository) {
        this.produkRepository = produkRepository;
        this.keteranganAplikasiRepository = keteranganAplikasiRepository;
        this.modelRepository = modelRepository;
        this.tipeRepository = tipeRepository;
        this.merkRepository = merkRepository;
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
                    search = search.equalsIgnoreCase("Aktif") ? "true" : "false";
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
            dto.setReferensi(referensiRepository.getReferenceById(dataLeads.getIdDebiturReferensi()).getNamaDepan());
            dto.setJenisUsaha(dataLeads.getJenisUsaha());
            dto.setKeteranganAplikasi(dataLeads.getKeteranganAplikasi().getNamaKeteranganAplikasi());
            if(dataLeads.getUserDataLeads() == null){

            }else {
                dto.setSurveyor(dataLeads.getUserDataLeads().getNamaKaryawan());
            }

            dto.setStatus(dataLeads.getStatus());
            dto.setPot(dataLeads.getPotDataLeads().getNamaPOT());
            dto.setTenor(dataLeads.getPotDataLeads().getTenor().toString());
            dto.setKategori(dataLeads.getKategoriDataLeads().getNamaKategori());
            dto.setMerek(dataLeads.getMerkDataLeads().getNamaMerk());
            dto.setModel(dataLeads.getModelDataLeads().getNamaModel());
            dto.setTipe(dataLeads.getTipeDataLeads().getNamaTipe());
            dto.setTahun(dataLeads.getTahun());
            dto.setTahunPajakSTNK(dataLeads.getTahunPajakSTNK());
            dto.setNomorBPKB(dataLeads.getNomorBPKB());
            dto.setNomorPolisi(dataLeads.getNomorPolisi());
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
            dto.setReferensi(" ");
            dto.setJenisUsaha(dataLeads.getJenisUsaha());
            dto.setKeteranganAplikasi(dataLeads.getKeteranganAplikasi().getNamaKeteranganAplikasi());
            dto.setSurveyor(dataLeads.getUserDataLeads().getNamaKaryawan());
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
                    search = search.equalsIgnoreCase("Aktif") ? "true" : "false";
                    totalRows = repository.countByStatus(Boolean.parseBoolean(search));
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
        Boolean isexist = dataLeads == null ? false : true;
        if(isexist == false) {
            List<DataLeads> list = repository.findAll();

            if(list.size() == 0){
                dataLeads = new DataLeads();
                String newId = "LEAD-00001";
                dataLeads.setId(newId);
                dataLeads.setNomorAplikasi("240100000001");
            }else {
                dataLeads = new DataLeads();
                List<DataLeads> listUrutan = repository.getAllOnly();
                DataLeads lastDataLead = listUrutan.get(listUrutan.size() - 1);
                BigDecimal lastNomorAplikasi = new BigDecimal(lastDataLead.getNomorAplikasi());
                BigDecimal newNomorAplikasi = lastNomorAplikasi.add(BigDecimal.ONE);
                String stringNomorAplikasi = newNomorAplikasi.toString();
                dataLeads.setNomorAplikasi(stringNomorAplikasi);
                String lastId = lastDataLead.getId();
                int lastNumber = Integer.parseInt(lastId.substring(5));
                String newId = String.format("LEAD-%05d", lastNumber + 1);
                dataLeads.setId(newId);
            }

        }else{
            dataLeads.setId(dataLeadsFormDTO.getId());
        }

            dataLeads.setIdProduk(produkRepository.getProdukByName(dataLeadsFormDTO.getIdProduk()).getId());
            List<DataLeads> dataLeadsList =  repository.getAllOnly();
            dataLeads.setTipeDebitur(dataLeadsFormDTO.getTipeDebitur() == true ? "PU" : "PO");
            dataLeads.setIdTipeAplikasi(tipeAplikasiRepository.getTipeAplikasiByName(dataLeadsFormDTO.getTipeAplikasi()).getId());

            List<Debitur> debiturList = debiturRepository.getAllOnly();
            Debitur debitur = new Debitur();
            if(dataLeads.getIdTipeAplikasi() == 1 /*Artinya Debitur Baruu*/) {
                if(debiturList.size() == 0){
                    String newId = "C012400001";
                    debitur.setId(newId);
                }else {
                    Debitur lastDebitur = debiturList.get(debiturList.size() - 1);
                    String lastId = lastDebitur.getId();
                    int lastNumber = Integer.parseInt(lastId.substring(5));
                    String newId = String.format("C0124%05d", lastNumber + 1);
                    debitur.setId(newId);
                }

            }else {
                Debitur debiturByNik = debiturRepository.getDebiturByNik(dataLeadsFormDTO.getNomorIdentitas());

                if (debiturByNik == null || debiturByNik.getId() == null) {
                    // Tidak ditemukan debitur, buat ID baru untuk debitur
                    Debitur lastDebitur = debiturList.get(debiturList.size() - 1);
                    String lastId = lastDebitur.getId();
                    int lastNumber = Integer.parseInt(lastId.substring(5));
                    String newId = String.format("C0124%05d", lastNumber + 1);
                    debitur.setId(newId);
                    dataLeads.setIdTipeAplikasi(1);
                } else {
                    // Ditemukan debitur, gunakan ID yang ada
                    debitur.setId(debiturByNik.getId());
                }



            }

            debitur.setNamaDepan(dataLeadsFormDTO.getNamaDepanDebitur());
            debitur.setNamaTengah(dataLeadsFormDTO.getNamaTengahDebitur());
            debitur.setNamaAkhir(dataLeadsFormDTO.getNamaBelakangDebitur());
            debitur.setNamaPanggilan(dataLeadsFormDTO.getNamaDepanDebitur());
            debitur.setIdIdentitas(dataLeadsFormDTO.getIdIdentitas());
            debitur.setNomorIdentitas(dataLeadsFormDTO.getNomorIdentitas());
            debitur.setJenisKelamin(dataLeadsFormDTO.getJenisKelamin());
            debitur.setAlamatIdentitas(dataLeadsFormDTO.getAlamatIdentitas());
            debitur.setIdKelurahan(kelurahanRepository.getKelurahanByNama(dataLeadsFormDTO.getKelurahan()).getId());
            debitur.setAlamatDomisili(dataLeadsFormDTO.getAlamatDomisili());
            debitur.setIdKelurahanDomisili(kelurahanRepository.getKelurahanByNama(dataLeadsFormDTO.getKelurahanDomisili()).getId());
            debitur.setNomorHp1(dataLeadsFormDTO.getNomorHandphone1());
            debitur.setNomorHP2(dataLeadsFormDTO.getNomorHandphone2());
            debitur.setNomorTelepon(dataLeadsFormDTO.getNomorTelepon());





            dataLeads.setIdDebitur(debitur.getId());
            //dataLeads.setIdCabang(cabangRepository.getCabangByNama(dataLeadsFormDTO.getNamaCabangTujuan()).getId());
            dataLeads.setIdCabang(1); //SEMENTARA HARD CODE
            dataLeads.setIdMitraAgen(mitraAgenRepository.getByNama(dataLeadsFormDTO.getSumberDataAplikasi()).getId() );
            dataLeads.setIdDebiturReferensi(dataLeadsFormDTO.getReferensiValue()); //sementara
            dataLeads.setJenisUsaha(dataLeadsFormDTO.getJenisUsaha());
            dataLeads.setIdKeteranganAplikasi(keteranganAplikasiRepository.getKeteranganAplikasiByName(dataLeadsFormDTO.getKeteranganAplikasi()).getId());
            if (dataLeadsFormDTO.getSurveyor().isBlank() || dataLeadsFormDTO.getSurveyor().equals(null)){
                dataLeads.setIdUser(null);
            }else {
                dataLeads.setIdUser(userRepository.getUserByName(dataLeadsFormDTO.getSurveyor()).getNik());
            }
            dataLeads.setStatus(dataLeadsFormDTO.getStatus());
            dataLeads.setIdPOT(Integer.parseInt(dataLeadsFormDTO.getPot())); // sementara hardcode
            dataLeads.setEstimasiNilaiFunding(new BigDecimal(300000000) ); // sementara hardcode
            dataLeads.setIdKategori(Integer.parseInt(dataLeadsFormDTO.getKategori())); // sementara hardcode
            dataLeads.setIdMerk(dataLeadsFormDTO.getMerek()); // sementara hardcode
            dataLeads.setIdTipe(dataLeadsFormDTO.getTipe()); // sementara hardcode
            dataLeads.setIdModel(dataLeadsFormDTO.getModel()); // sementara hardcode
            //dataLeads.setEstimasiNilaiFunding(dataLeadsFormDTO.getEstimasiNilaiFunding());

            //kurang POT dan bawahnya yang seharusnya pakai ID


            dataLeads.setTahun(dataLeadsFormDTO.getTahun());
            dataLeads.setTahunPajakSTNK(dataLeadsFormDTO.getTahunPajakSTNK());
            dataLeads.setNomorBPKB(dataLeadsFormDTO.getNomorBPKB());
            dataLeads.setNomorPolisi(dataLeadsFormDTO.getNomorPolisi());
            debiturRepository.save(debitur);
            repository.save(dataLeads);
        }




    @Override
    public List<OptionDTO> getOptionSumberDataAplikasi( String produkName , Integer cabangId) {
        List<OptionDTO> optionDTOList = new LinkedList<>();
        Produk produk = produkRepository.getProdukByName(produkName);
        List<MitraAgen> listSumberdataAplikasi = mitraAgenRepository.getAllByProductIdCabangId(produk.getId(),cabangId);

        for(var mitraAgen : listSumberdataAplikasi){
            OptionDTO dto = new OptionDTO();
            dto.setText(mitraAgen.getNamaMitraAgen());
            dto.setValue(mitraAgen.getId());
            optionDTOList.add(dto);
        }
        return optionDTOList;
    }

    @Override
    public List<OptionKelurahanDTO> getOptionKelurahan() {
        List<Kelurahan> listKelurahan = kelurahanRepository.findAll();
        List<OptionKelurahanDTO> optionKelurahanDTOS = new LinkedList<>();

        for (var kelurahan : listKelurahan){
            OptionKelurahanDTO dto = new OptionKelurahanDTO();
            dto.setValue(kelurahan.getId());
            dto.setKelurahan(kelurahan.getNamaKelurahan());
            dto.setKecamatan(kelurahan.getKecamatan().getNamaKecamatan());
            dto.setKotaKabupaten(kelurahan.getKecamatan().getKabupaten().getNamaKabupatenKota());
            dto.setProvinsi(kelurahan.getKecamatan().getKabupaten().getProvinsi().getNamaProvinsi());
            dto.setKodePos(kelurahan.getKodePos());
            optionKelurahanDTOS.add(dto);
        }
        return optionKelurahanDTOS;
    }

    @Override
    public List<OptionDTO> getOptionProduk() {

        List<Produk> listProduk = produkRepository.getAll();
        List<OptionDTO> listDto = new LinkedList<>();
        for(var pro : listProduk){
            OptionDTO dto1 = new OptionDTO();
            dto1.setText(pro.getNamaProduk());
            dto1.setValue(pro.getId().toString());
            listDto.add(dto1);
        }

        return listDto;
    }

    @Override
    public List<OptionDTO> getOptionTipeAplikasi() {
        List<OptionDTO> optionDTOList = new LinkedList<>();
        List<TipeAplikasi> list = tipeAplikasiRepository.findAll();

        for(var tipe : list){
            OptionDTO dto = new OptionDTO();
            dto.setText(tipe.getNamaTipeAplikasi());
            dto.setValue(tipe.getId().toString());
            optionDTOList.add(dto);
        }
        return optionDTOList;
    }

    @Override
    public List<OptionDTO> getOptionReferensi() {
        List<OptionDTO> optionDTOList = new LinkedList<>();
        List<Referensi> list = referensiRepository.findAll();

        for(var tipe : list){
            OptionDTO dto = new OptionDTO();
            dto.setText(tipe.getNamaDepan().concat(" ").concat(tipe.getNamaTengah().concat(" ").concat(tipe.getNamaAkhir())));
            dto.setValue(tipe.getId());
            optionDTOList.add(dto);
        }
        return optionDTOList;

    }

    @Override
    public List<KeteranganAplikasiSurveyorDTO> getOptionKeteranganAplikasi(Integer cabangId) {
        List<KeteranganAplikasi> listket = keteranganAplikasiRepository.findAll();
        List<KeteranganAplikasiSurveyorDTO> listdto = new LinkedList<>();

        for (var ket : listket){
            KeteranganAplikasiSurveyorDTO dto = new KeteranganAplikasiSurveyorDTO();
            dto.setText(ket.getNamaKeteranganAplikasi());
            dto.setValue(ket.getId().toString());
            if(dto.getValue().equals("2")){
                OptionDTO surveyor = getRandomSurveyor(cabangId);
                dto.setIdSurveyor(surveyor.getValue());
                dto.setSurveyor(surveyor.getText());
            }
            listdto.add(dto);
        }

        return listdto;
    }



    @Override
    public List<OptionDTO>  getSearchItems(String filter) {
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
            merkData = merkRepository.getMerkByKategoriId(potData.getIdKategori());
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
            tipeData = tipeRepository.getAllTipeByKategoriId(potData.getIdKategori());
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

        return NumberFormat.getCurrencyInstance(indo).format(totalEstimasi);
    }

    @Override
    public OptionDTO getRandomSurveyor(Integer cabangId) {
        OptionDTO randomSurveyor = new OptionDTO();
        List<User> listSurveyor = userRepository.getUserByCabangAndSurveyor(cabangId);
        Random random = new Random();
        Integer randomIndex = random.nextInt(listSurveyor.size());
        User surveyor = listSurveyor.get(randomIndex);
        randomSurveyor.setValue(surveyor.getNik());
        randomSurveyor.setText(surveyor.getNamaKaryawan());
        return randomSurveyor;
    }

}
