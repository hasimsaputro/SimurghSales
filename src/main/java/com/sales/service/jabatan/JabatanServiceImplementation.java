package com.sales.service.jabatan;

import com.sales.dto.OptionDTO;
import com.sales.dto.jabatan.JabatanDTO;
import com.sales.dto.jabatan.JabatanFormDTO;
import com.sales.entity.Jabatan;
import com.sales.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Service
public class JabatanServiceImplementation implements JabatanService {
    private final JabatanRepository jabatanRepository;
    private final ReferensiRepository referensiRepository;
    private final CabangRepository cabangRepository;
    private final KelurahanRepository kelurahanRepository;
    private final DebiturRepository debiturRepository;
    private final TipeAplikasiRepository tipeAplikasiRepository;
    private final ProdukRepository produkRepository;
    private final KeteranganAplikasiRepository keteranganAplikasiRepository;
    private final ModelRepository modelRepositoryl;
    private final TipeRepository tipeRepository;
    private final MerkRepository merkRepository;
    private final KategoriRepository kategoriRepository;
    private final MitraAgenRepository mitraAgenRepository;
    private final DataLeadsRepository repository;
    private final UserRepository userRepository;
    private final Integer rowInPage = 5;

    public JabatanServiceImplementation(JabatanRepository jabatanRepository, ReferensiRepository referensiRepository, CabangRepository cabangRepository, KelurahanRepository kelurahanRepository, DebiturRepository debiturRepository, TipeAplikasiRepository tipeAplikasiRepository, ProdukRepository produkRepository, KeteranganAplikasiRepository keteranganAplikasiRepository, ModelRepository modelRepositoryl, TipeRepository tipeRepository, MerkRepository merkRepository, KategoriRepository kategoriRepository, MitraAgenRepository mitraAgenRepository, DataLeadsRepository repository, UserRepository userRepository) {
        this.jabatanRepository = jabatanRepository;
        this.referensiRepository = referensiRepository;
        this.cabangRepository = cabangRepository;
        this.kelurahanRepository = kelurahanRepository;
        this.debiturRepository = debiturRepository;
        this.tipeAplikasiRepository = tipeAplikasiRepository;
        this.produkRepository = produkRepository;
        this.keteranganAplikasiRepository = keteranganAplikasiRepository;
        this.modelRepositoryl = modelRepositoryl;
        this.tipeRepository = tipeRepository;
        this.merkRepository = merkRepository;
        this.kategoriRepository = kategoriRepository;
        this.mitraAgenRepository = mitraAgenRepository;
        this.repository = repository;
        this.userRepository = userRepository;
    }


    @Override
    public List<JabatanDTO> getAll(String id , String name, Boolean status, Integer page, String filter, String search) {
        if (filter.isBlank()){
        }else {
            switch (filter){
                case "id" :
                    try{
                        id = search;
                    }catch (Exception exception){
                        id = null;
                    }

                    break;
                case  "nama":
                    name = search;
                    break;
                case "status" :
                    try{
                        status = Boolean.getBoolean(search);
                    }catch (Exception exception){
                        status = null;
                    }

                    break;
            }
        }


        Pageable pagination = PageRequest.of(page-1,rowInPage, Sort.by("id"));
        if(name != null){
            if(name.trim().isBlank()){
                name = null;
            }
        }

        List<Jabatan> jabatanList = jabatanRepository.getAllBySearch(pagination, id, name, status);
        var gridTipeApl = new LinkedList<JabatanDTO>();
        for (var ket :jabatanList) {
            JabatanDTO dto = new JabatanDTO();
            dto.setId(ket.getId());
            dto.setName(ket.getNamaJabatan());
            dto.setStatus(ket.getStatus());
            gridTipeApl.add(dto);
        }
        return gridTipeApl;
    }

    @Override
    public Integer totalPage(String id, String name, Boolean status) {
        if (name != null){
            if(name.trim().isBlank()){
                name = null;
            }
        }

        Integer totalPage = (int) Math.ceil(((double)jabatanRepository.countAllBySearch( id, name, status)) / rowInPage);

        return  totalPage;
    }

    @Override
    public JabatanDTO getJabatanById(String id) {
        JabatanDTO dto = new JabatanDTO();
        Jabatan jabatan = jabatanRepository.getKeteranganAplikasiById(id);

        if(jabatan != null){
            dto.setStatus(jabatan.getStatus());
            dto.setName(jabatan.getNamaJabatan());
            dto.setId(jabatan.getId());
        }

        return dto;
    }

    @Override
    public List<OptionDTO> filter() {
        OptionDTO dto1 = new OptionDTO();
        OptionDTO dto2 = new OptionDTO();
        OptionDTO dto3 = new OptionDTO();
        List<OptionDTO> dtoList = new LinkedList<>();
        dto1.setText("Id");
        dto1.setValue("id");
        dto2.setText("Nama Jabatan");
        dto2.setValue("nama");
        dto3.setText("Status");
        dto3.setValue("status");
        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);
        return dtoList;
    }

    @Override
    public List<OptionDTO> getSearchFilter(String filter) {
        List<OptionDTO> searchItem = new LinkedList<>();
        List<Jabatan> jabatanList = jabatanRepository.findAll();
        if (filter.isBlank()){
        }else {
            switch (filter){
                case "id" :

                    for (var tipeAplikasi : jabatanList){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(tipeAplikasi.getId());
                        dto.setText(tipeAplikasi.getId());
                        searchItem.add(dto);
                    }
                    break;
                case  "nama":

                    for (var tipeAplikasi : jabatanList){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(tipeAplikasi.getNamaJabatan());
                        dto.setText(tipeAplikasi.getNamaJabatan());
                        searchItem.add(dto);
                    }
                    break;
                case "status" :

                    for (var tipeAplikasi : jabatanList){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(tipeAplikasi.getStatus().toString());
                        dto.setText(tipeAplikasi.getStatus().toString());
                        searchItem.add(dto);
                    }
                    break;
            }
        }
        return searchItem;
    }

    @Override
    public void updateInsert(JabatanFormDTO jabatanFormDTO) {
        Jabatan jabatan = new Jabatan();
        jabatan.setNamaJabatan(jabatanFormDTO.getName());
        jabatan.setId(jabatanFormDTO.getId().toUpperCase(Locale.ROOT));
        jabatan.setStatus(jabatanFormDTO.getStatus());
        jabatanRepository.save(jabatan);
    }
    public static String generateId(String namaJabatan) {
        List<String> words = Arrays.asList(namaJabatan.split(" "));

        if (words.size() == 1) {
            String word = words.get(0);
            int length = word.length();
            int mid = length / 2;

            if (length % 2 == 1) { // Jika jumlah huruf ganjil
                return "" + word.charAt(0) + word.charAt(mid) + word.charAt(length - 1);
            } else { // Jika jumlah huruf genap
                return "" + word.charAt(0) + word.charAt(mid) + word.charAt(length - 1);
            }
        }
        else if (words.size() == 2) {
            return "" + words.get(0).charAt(0) + words.get(1).charAt(0) + words.get(1).charAt(words.get(1).length() - 1);
        }
        else { // Jika jumlah kata >= 3
            return "" + words.get(0).charAt(0) + words.get(1).charAt(0) + words.get(2).charAt(0);
        }
    }

    @Override
    public void delete(String id) {
        Jabatan jabatan = jabatanRepository.getKeteranganAplikasiById(id);

        jabatanRepository.delete(jabatan);
    }


}
