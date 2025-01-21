package com.sales.service.dataLeads;

import com.sales.dto.OptionDTO;
import com.sales.dto.dataLeads.DataLeadsFormDTO;
import com.sales.dto.dataLeads.DataLeadsIndexDTO;
import com.sales.entity.DataLeads;
import com.sales.repository.DataLeadsRepository;
import com.sales.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class DataLeadsServiceImplementation implements DataLeadsService{
    private final DataLeadsRepository repository;
    private final UserRepository userRepository;
    private final Integer rowInPage = 10;

    public DataLeadsServiceImplementation(DataLeadsRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }


    @Override
    public List<DataLeadsIndexDTO> getAll(String filter, String search, int page) {
        Pageable pagination = PageRequest.of(page-1,rowInPage, Sort.by("dataLeadsId"));
        List<DataLeads> dataLeads = new LinkedList<>();
        if(filter.equalsIgnoreCase("Nomor Data Leads")){
            dataLeads = repository.getByIdDataLeads(search, pagination);
        }else if (filter.equalsIgnoreCase("Nomor Aplikasi")){
            dataLeads = repository.getByNomorAplikasi(search, pagination);
        }else if (filter.equalsIgnoreCase("Nama Debitur")){
            dataLeads = repository.getByDebitur(search, pagination);
        }else if (filter.equalsIgnoreCase("Tipe Aplikasi")){
            dataLeads = repository.getByTipeAplikasi(search, pagination);
        }else if (filter.equalsIgnoreCase("Keterangan")){
            dataLeads = repository.getByKeterangan(search, pagination);
        }else if (filter.equalsIgnoreCase("Status")){
            dataLeads = repository.getByStatus(search, pagination);
        }
        var gridDataLeads = new LinkedList<DataLeadsIndexDTO>();
        for (var datalead :dataLeads) {
            String dataleadId = datalead.getId();
            String nomorAplikasi = datalead.getNomorAplikasi();
            String namaDebitur = datalead.getDebiturDataLeads().getNamaDepan()+" "+datalead.getDebiturDataLeads().getNamaTengah()+" "+datalead.getDebiturDataLeads().getNamaAkhir();
            String tipeAplikasi = datalead.getTipeAplikasiDataLeads().getNamaTipeAplikasi();
            String keterangan = datalead.getKeteranganAplikasiDataLeads().getNamaKeteranganAplikasi();
            String status = datalead.getStatus() ? "Aktif":"Tidak Akrif";
            DataLeadsIndexDTO dataLeadsIndexDTO = new DataLeadsIndexDTO();
            dataLeadsIndexDTO.setNomorDataLeads(dataleadId);
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
        Boolean isexist = dataLeads.getId() == null ? false : true;

        if(isexist == false){
            dataLeads = new DataLeads();
        }

        dto.setId(dataLeads.getId());
        dto.setIdProduk(dataLeads.getIdProduk());
        dto.setTipeDebitur(dataLeads.getTipeDebitur());
        dto.setTipeAplikasi(dataLeads.getTipeAplikasiDataLeads().getNamaTipeAplikasi());
        dto.setNamaDepanDebitur(dataLeads.getDebiturDataLeads().getNamaDepan());
        dto.setNamaTengahDebitur(dataLeads.getDebiturDataLeads().getNamaTengah());
        dto.setNamaBelakangDebitur(dataLeads.getDebiturDataLeads().getNamaAkhir());
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
        dto.setReferensi(dataLeads.getDebiturReferensiDataLeads().getNamaDepan().concat(" ").concat(dataLeads.getDebiturReferensiDataLeads().getNamaTengah()).concat(" ").concat(dataLeads.getDebiturReferensiDataLeads().getNamaAkhir()));
        dto.setJenisUsaha(dataLeads.getJenisUsaha());
        dto.setKeteranganAplikasi(dataLeads.getKeteranganAplikasiDataLeads().getNamaKeteranganAplikasi());
        dto.setSurveyor(userRepository.getUserByCabangAndSurveyor(dataLeads.getIdCabang()).getNamaKaryawan());
        dto.setStatus(dataLeads.getStatus());
        dto.setPot(dataLeads.getPotDataLeads().getNamaPOT());
        dto.setTenor(dataLeads.getPotDataLeads().getTenor().toString());
        //Kurang Estimasi Nilai Faunding dan Data Unit
        //dan unit


        return dto;
    }

    @Override
    public int getTotal(String filter, String search) {
        return 0;
    }

    @Override
    public List<OptionDTO> getfilterAsItem() {
        return List.of();
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


}
