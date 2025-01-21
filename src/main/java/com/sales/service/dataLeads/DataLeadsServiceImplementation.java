package com.sales.service.dataLeads;

import com.sales.dto.dataLeads.DataLeadsFormDTO;
import com.sales.dto.dataLeads.DataLeadsIndexDTO;
import com.sales.entity.DataLeads;
import com.sales.repository.DataLeadsRepository;
import com.sales.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataLeadsServiceImplementation implements DataLeadsService{
    private final DataLeadsRepository repository;
    private final UserRepository userRepository;

    public DataLeadsServiceImplementation(DataLeadsRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }


    @Override
    public List<DataLeadsIndexDTO> getAll(String fullName, String search, int page) {
        return null;
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
        dto.setTipeAplikasi(dataLeads.getTipeAplikasi().getNamaTipeAplikasi());
        dto.setNamaDepanDebitur(dataLeads.getDebitur().getNamaDepan());
        dto.setNamaTengahDebitur(dataLeads.getDebitur().getNamaTengah());
        dto.setNamaBelakangDebitur(dataLeads.getDebitur().getNamaAkhir());
        dto.setJenisKelamin(dataLeads.getDebitur().getJenisKelamin());
        dto.setAlamatIdentitas(dataLeads.getDebitur().getAlamatIdentitas());
        dto.setKelurahan(dataLeads.getDebitur().getKelurahan().getNamaKelurahan());
        dto.setKodePos(dataLeads.getDebitur().getKelurahan().getKodePos());
        dto.setKecamatan(dataLeads.getDebitur().getKelurahan().getKecamatan().getNamaKecamatan());
        dto.setKotaKabupaten(dataLeads.getDebitur().getKelurahan().getKecamatan().getKabupaten().getNamaKabupatenKota());
        dto.setProvinsi(dataLeads.getDebitur().getKelurahan().getKecamatan().getKabupaten().getProvinsi().getNamaProvinsi());
        dto.setAlamatDomisili(dataLeads.getDebitur().getAlamatDomisili());
        dto.setKelurahanDomisili(dataLeads.getDebitur().getKelurahan().getNamaKelurahan());
        dto.setKodePosDomisili(dataLeads.getDebitur().getKelurahanDomisili().getKodePos());
        dto.setKecamatanDomisili(dataLeads.getDebitur().getKelurahanDomisili().getKecamatan().getNamaKecamatan());
        dto.setKotaKabupatenDomisili(dataLeads.getDebitur().getKelurahanDomisili().getKecamatan().getKabupaten().getNamaKabupatenKota());
        dto.setProvinsiDomisili(dataLeads.getDebitur().getKelurahanDomisili().getKecamatan().getProvinsi().getNamaProvinsi());
        //Kurang Nama Cabang Tujuan
        dto.setNomorHandphone1(dataLeads.getDebitur().getNomorHp1());
        dto.setNomorHandphone2(dataLeads.getDebitur().getNomorHP2());
        dto.setNomorTelepon(dataLeads.getDebitur().getNomorTelepon());
        dto.setSumberDataAplikasi(dataLeads.getMitraAgenDataLeads().getNamaMitraAgen());
        dto.setReferensi(dataLeads.getDebiturReferensiDataLeads().getNamaDepan().concat(" ").concat(dataLeads.getDebiturReferensiDataLeads().getNamaTengah()).concat(" ").concat(dataLeads.getDebiturReferensiDataLeads().getNamaAkhir()));
        dto.setJenisUsaha(dataLeads.getJenisUsaha());
        dto.setKeteranganAplikasi(dataLeads.getKeteranganAplikasiDataLeads().getNamaKeteranganAplikasi());
        dto.setSurveyor(userRepository.getUserByCabangAndSurveyor(dataLeads.getIdCabang()).getNamaKaryawan());
        dto.setStatus(dataLeads.getStatus());
        dto.setPot(dataLeads.getPotDataLeads().getNamaPOT());
        dto.setTenor(dataLeads.getPotDataLeads().getTenor().toString());
        //Kurang Estimasi Nilai Faunding dan Data Unit


        return dto;
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
