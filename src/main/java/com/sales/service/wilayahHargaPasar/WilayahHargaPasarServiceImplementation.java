package com.sales.service.wilayahHargaPasar;

import com.sales.dto.OptionDTO;
import com.sales.dto.wilayahHargaPasar.CabangWilayahDTO;
import com.sales.dto.wilayahHargaPasar.WilayahHargaPasarDetailDTO;
import com.sales.dto.wilayahHargaPasar.WilayahHargaPasarFormDTO;
import com.sales.entity.Cabang;
import com.sales.entity.POT;
import com.sales.entity.WilayahHargaPasar;
import com.sales.repository.CabangRepository;
import com.sales.repository.KategoriRepository;
import com.sales.repository.WilayahHargaPasarRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class WilayahHargaPasarServiceImplementation implements WilayahHargaPasarService{
    private final WilayahHargaPasarRepository repository;
    private final CabangRepository cabangRepository;
    private final KategoriRepository kategoriRepository;

    public WilayahHargaPasarServiceImplementation(WilayahHargaPasarRepository wilayahHargaPasarRepository, CabangRepository cabangRepository,
                                                  KategoriRepository kategoriRepository) {
        this.repository = wilayahHargaPasarRepository;
        this.cabangRepository = cabangRepository;
        this.kategoriRepository = kategoriRepository;
    }

    @Override
    public WilayahHargaPasarFormDTO getWilayahById(String wilayahId) {
        WilayahHargaPasarFormDTO wilayahHargaPasarFormDTO = new WilayahHargaPasarFormDTO();
        wilayahId = wilayahId == null ? "" : wilayahId;
        if(!wilayahId.isBlank()) {
            var wilayah = repository.findById(wilayahId).orElseThrow();
            wilayahHargaPasarFormDTO.setId(wilayah.getId());
            wilayahHargaPasarFormDTO.setNamaWilayah(wilayah.getNamaWilayah());
            wilayahHargaPasarFormDTO.setIdKategori(wilayah.getIdKategori());
            wilayahHargaPasarFormDTO.setNamaKategori(wilayah.getKategoriWilayah().getNamaKategori());
            wilayahHargaPasarFormDTO.setStatus(wilayah.getStatus());

        }
        return wilayahHargaPasarFormDTO;
    }

    @Override
    public List<CabangWilayahDTO> getCabangByWilayahId(String wilayahId) {
        var wilayah = repository.findById(wilayahId).orElseThrow();
        List<CabangWilayahDTO> cabangWilayahDTOList = new LinkedList<>();
        for (Cabang cabangList: wilayah.getCabangSet()){
            CabangWilayahDTO cabang = new CabangWilayahDTO();
            cabang.setId(String.valueOf(cabangList.getId()));
            cabang.setNamaCabang(cabangList.getNamaCabang());
            cabangWilayahDTOList.add(cabang);
        }
        return cabangWilayahDTOList;
    }

    @Override
    public WilayahHargaPasarDetailDTO getWilayahDetailById(String wilayahId) {
        var wilayahHargaPasar = repository.findById(wilayahId).orElseThrow();
        WilayahHargaPasarDetailDTO dto = new WilayahHargaPasarDetailDTO();
        dto.setId(wilayahHargaPasar.getId());
        dto.setNamaWilayah(wilayahHargaPasar.getNamaWilayah());
        dto.setNamaKategori(wilayahHargaPasar.getKategoriWilayah().getNamaKategori());
        dto.setStatus(wilayahHargaPasar.getStatus()?"Aktif":"Tidak Aktif");
        return dto;
    }

    @Override
    public void deleteWilayah(String wilayahId) {
        var wilayahHargaPasar = repository.findById(wilayahId).orElseThrow();
        wilayahHargaPasar.setDeleteDate(LocalDate.now());
        repository.save(wilayahHargaPasar);
    }

    @Override
    public List<OptionDTO> getfilterAsItem() {
        return List.of(
                new OptionDTO("Kategori", "kategori"),
                new OptionDTO("Kode Wilayah Harga", "id"),
                new OptionDTO("Nama Wilayah Harga", "namaWilayah"),
                new OptionDTO("Status", "status")
        );
    }

    @Override
    public void saveWilayah(WilayahHargaPasarFormDTO dto) {
        WilayahHargaPasar wilayahHargaPasar = new WilayahHargaPasar();
        wilayahHargaPasar.setId(dto.getId());
        wilayahHargaPasar.setNamaWilayah(dto.getNamaWilayah());
        wilayahHargaPasar.setIdKategori(dto.getIdKategori());
        wilayahHargaPasar.setStatus(dto.getStatus());
        Set<Cabang> cabangSet = new HashSet<>();
        var cabangList = dto.getCabangList();
        for (Integer kodeCabang : cabangList) {
            Cabang cabang = cabangRepository.findById(kodeCabang).orElseThrow();
            cabangSet.add(cabang);
            wilayahHargaPasar.setCabangSet(cabangSet);
        }
        repository.save(wilayahHargaPasar);
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
    public List<OptionDTO> getFilterCabang() {
        return List.of();
    }
}
