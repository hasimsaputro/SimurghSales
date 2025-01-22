package com.sales.service.mitraAgen;

import com.sales.dto.mitraAgen.MitraAgenDetailDTO;
import com.sales.dto.mitraAgen.MitraAgenFormDTO;
import com.sales.dto.mitraAgen.MitraAgenIndexDTO;
import com.sales.dto.mitraAgen.MitraAgenIndexOptionDTO;
import com.sales.entity.MitraAgen;
import com.sales.helper.DateHelper;
import com.sales.repository.MitraAgenRepository;
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
    private final int rowInPage = 1;

    @Autowired
    public MitraAgenServiceImplementation(MitraAgenRepository repository) {
        this.repository = repository;
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
                    page = repository.getTotalpagesByStatus(search);
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
                    mitraAgenList = repository.getMitraAgenByStatus(pageable, search);
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
                mitraAgenFormDTO.setIdProduk(mitraAgen.getIdProduk());
                mitraAgenFormDTO.setIdCabang(mitraAgen.getIdCabang());
                // Data Pribadi
                mitraAgenFormDTO.setIdIdentitas(mitraAgen.getIdIdentitas());
                mitraAgenFormDTO.setNomorIdentitas(mitraAgen.getNomorIdentitas());
                mitraAgenFormDTO.setNama(mitraAgen.getNamaMitraAgen());
                mitraAgenFormDTO.setJenisKelamin(mitraAgen.getJenisKelamin());
                mitraAgenFormDTO.setNpwp(mitraAgen.getNpwp());
                mitraAgenFormDTO.setAlamatIdentitas(mitraAgen.getAlamatIdentitas());
                mitraAgenFormDTO.setIdKelurahanIdentitas(mitraAgen.getIdKelurahanIdentitas());
                mitraAgenFormDTO.setAlamatDomisili(mitraAgen.getAlamatDomisili());
                mitraAgenFormDTO.setIdKelurahanDomisili(mitraAgen.getIdKelurahanDomisili());
                mitraAgenFormDTO.setTempatLahir(mitraAgen.getTempatLahir());
                mitraAgenFormDTO.setTanggalLahir(mitraAgen.getTanggalLahir());
                mitraAgenFormDTO.setNomorTelepon(mitraAgen.getNomorTelepon());
                mitraAgenFormDTO.setNomorHandPhone(mitraAgen.getNomorHandphone());
                // Bank
                mitraAgenFormDTO.setIdBank(mitraAgen.getIdBank());
                mitraAgenFormDTO.setNomorRekening(mitraAgen.getNomorRekening());
                mitraAgenFormDTO.setNamaRekening(mitraAgen.getNamaRekening());
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
                String nomorIdentitas = String.format("%s - %s", mitraAgen.getIdIdentitas(), mitraAgen.getNomorIdentitas());
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
        if (mitraAgen.getId().isEmpty() && mitraAgen.getId() == null){
            generateId(mitraAgenFormDTO.getIdTipeMaster());
        } else {
            mitraAgen.setId(mitraAgenFormDTO.getId());
        }
        mitraAgen.setIdTipeMaster(mitraAgenFormDTO.getIdTipeMaster());
        mitraAgen.setIdProduk(mitraAgenFormDTO.getIdProduk());
        mitraAgen.setIdCabang(mitraAgenFormDTO.getIdCabang());
        mitraAgen.setIdIdentitas(mitraAgenFormDTO.getIdIdentitas());
        mitraAgen.setNomorIdentitas(mitraAgenFormDTO.getNomorIdentitas());
        mitraAgen.setNamaMitraAgen(mitraAgenFormDTO.getNama());
        mitraAgen.setJenisKelamin(mitraAgenFormDTO.getJenisKelamin());
        mitraAgen.setNpwp(mitraAgenFormDTO.getNpwp());
        mitraAgen.setAlamatIdentitas(mitraAgenFormDTO.getAlamatIdentitas());
        mitraAgen.setIdKelurahanIdentitas(mitraAgenFormDTO.getIdKelurahanIdentitas());
        mitraAgen.setAlamatDomisili(mitraAgenFormDTO.getAlamatDomisili());
        mitraAgen.setIdKelurahanDomisili(mitraAgenFormDTO.getIdKelurahanDomisili());
        mitraAgen.setTempatLahir(mitraAgenFormDTO.getTempatLahir());
        mitraAgen.setTanggalLahir(mitraAgenFormDTO.getTanggalLahir());
        mitraAgen.setNomorTelepon(mitraAgenFormDTO.getNomorTelepon());
        mitraAgen.setNomorHandphone(mitraAgenFormDTO.getNomorHandPhone());
        mitraAgen.setIdBank(mitraAgenFormDTO.getIdBank());
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
}
