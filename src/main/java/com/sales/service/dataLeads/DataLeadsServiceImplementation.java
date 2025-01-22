package com.sales.service.dataLeads;

import com.sales.dto.OptionDTO;
import com.sales.dto.dataLeads.DataLeadsDetailDTO;
import com.sales.dto.dataLeads.DataLeadsFormDTO;
import com.sales.dto.dataLeads.DataLeadsIndexDTO;
import com.sales.entity.*;
import com.sales.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class DataLeadsServiceImplementation implements DataLeadsService{
    private final ModelRepository modelRepositoryl;
    private final TipeRepository tipeRepository;
    private final MerkRepository merkRepository;
    private final KategoriRepository kategoriRepository;
    private final MitraAgenRepository mitraAgenRepository;
    private final DataLeadsRepository repository;
    private final UserRepository userRepository;
    private final Integer rowInPage = 10;

    public DataLeadsServiceImplementation(ModelRepository modelRepositoryl, TipeRepository tipeRepository, MerkRepository merkRepository, KategoriRepository kategoriRepository, MitraAgenRepository mitraAgenRepository, DataLeadsRepository repository, UserRepository userRepository) {
        this.modelRepositoryl = modelRepositoryl;
        this.tipeRepository = tipeRepository;
        this.merkRepository = merkRepository;
        this.kategoriRepository = kategoriRepository;
        this.mitraAgenRepository = mitraAgenRepository;
        this.repository = repository;
        this.userRepository = userRepository;
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
                    dataLeads = repository.getByStatus(search,pagination);
                    break;
            }
        }
        var gridDataLeads = new LinkedList<DataLeadsIndexDTO>();
        for (var datalead :dataLeads) {
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
        return gridDataLeads;
    }

    @Override
    public DataLeadsFormDTO getDataLeadsById(String dataLeadsId) {
        DataLeads dataLeads = repository.getDataLeadsById(dataLeadsId);
        DataLeadsFormDTO dto = new DataLeadsFormDTO();

        if(dataLeads != null){

            dto.setId(dataLeads.getId());
            dto.setIdProduk(dataLeads.getIdProduk());
            dto.setTipeDebitur(dataLeads.getTipeDebitur());
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
            dto.setTipeDebitur(dataLeads.getTipeDebitur() == true ?"Perusahaan" : "Perseorangan" );
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
    public void updateInsertDataLeads(DataLeadsFormDTO dataLeadsFormDTO) {
        DataLeads dataLeads = repository.getDataLeadsById(dataLeadsFormDTO.getId());
        Boolean isexist = dataLeads.getId() == null ? false : true;

        if(isexist == false){
            dataLeads = new DataLeads();
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
        dto1.setValue("Interest");
        dto2.setValue("Prospect");
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
    public List<OptionDTO> getOptionMerek() {
        List<OptionDTO> optionDTOList = new LinkedList<>();
        List<Merk> list = merkRepository.getAll();

        for(var merk : list){
            OptionDTO dto = new OptionDTO();
            dto.setText(merk.getNamaMerk());
            dto.setValue(merk.getId());
            optionDTOList.add(dto);
        }
        return optionDTOList;
    }

    @Override
    public List<OptionDTO> getOptionTipe() {
        List<OptionDTO> optionDTOList = new LinkedList<>();
        List<Tipe> list = tipeRepository.getAll();

        for(var tipe : list){
            OptionDTO dto = new OptionDTO();
            dto.setText(tipe.getNamaTipe());
            dto.setValue(tipe.getId());
            optionDTOList.add(dto);
        }
        return optionDTOList;
    }

    @Override
    public List<OptionDTO> getOptionModel() {
        List<OptionDTO> optionDTOList = new LinkedList<>();
        List<Model> list = modelRepositoryl.getAll();

        for(var tipe : list){
            OptionDTO dto = new OptionDTO();
            dto.setText(tipe.getNamaModel());
            dto.setValue(tipe.getId());
            optionDTOList.add(dto);
        }
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


}
