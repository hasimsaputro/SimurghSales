package com.sales.service.user;

import com.sales.dto.OptionDTO;
import com.sales.dto.user.UserDTO;
import com.sales.dto.user.UserFormDTO;
import com.sales.entity.KeteranganAplikasi;
import com.sales.entity.User;
import com.sales.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
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

    public UserServiceImplementation(ReferensiRepository referensiRepository, CabangRepository cabangRepository, KelurahanRepository kelurahanRepository, DebiturRepository debiturRepository, TipeAplikasiRepository tipeAplikasiRepository, ProdukRepository produkRepository, KeteranganAplikasiRepository keteranganAplikasiRepository, ModelRepository modelRepositoryl, TipeRepository tipeRepository, MerkRepository merkRepository, KategoriRepository kategoriRepository, MitraAgenRepository mitraAgenRepository, DataLeadsRepository repository, UserRepository userRepository) {
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
    public List<UserDTO> getAll(String nik , String name,String email,String jabatan, Boolean status, Integer page, String filter, String search) {
        if (filter.isBlank()){
        }else {
            switch (filter){
                case "nik" :
                    try{
                        nik = (search);
                    }catch (Exception exception){
                        nik = null;
                    }

                    break;
                case  "namaKaryawan":
                    name = search;
                    break;
                case  "email":
                    email = search;
                    break;
                case  "namaJabatan":
                    jabatan = search;
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

        List<User> users = userRepository.getAllBySearch(pagination, nik, name,email,jabatan, status);
        var gridUser = new LinkedList<UserDTO>();
        for (var ket :users) {
            UserDTO dto = new UserDTO();
            dto.setNIK(ket.getNik());
            dto.setEmail(ket.getEmail());
            dto.setNamaJabatan(ket.getIdJabatan());
            dto.setNamaKaryawan(ket.getNamaKaryawan());
            dto.setStatus(ket.getStatus());
            gridUser.add(dto);
        }
        return gridUser;
    }

    @Override
    public Integer totalPage(String nik, String name,String email,String jabatan, Boolean status) {
        if (name != null){
            if(name.trim().isBlank()){
                name = null;
            }
        }

        Integer totalPage = (int) Math.ceil(((double)userRepository.countAllBySearch( nik, name,email,jabatan, status)) / rowInPage);

        return  totalPage;
    }

    @Override
    public UserFormDTO getUserByNik(String nik) {
        UserFormDTO dto = new UserFormDTO();
        User user = userRepository.getUserByNik(nik);

        if(user != null) {
            dto.setNIK(user.getNik());
            dto.setEmailEksternal(user.getEmailEksternal());
            dto.setIdJabatan(user.getIdJabatan());
            dto.setNamaCabang(user.getNamaKaryawan());
            dto.setEmail(user.getEmail());
            dto.setNomorHp(user.getNomorHp());
            dto.setStatus(user.getStatus());
            dto.setNamaJabatan(user.getJabatan().getNamaJabatan());
            dto.setEmailEksternal(user.getEmailEksternal());
        }


        return dto;
    }

    @Override
    public List<OptionDTO> filter() {
        OptionDTO dto1 = new OptionDTO();
        OptionDTO dto2 = new OptionDTO();
        OptionDTO dto3 = new OptionDTO();
        OptionDTO dto4 = new OptionDTO();
        OptionDTO dto5 = new OptionDTO();
        List<OptionDTO> dtoList = new LinkedList<>();
        dto1.setText("Nik");
        dto1.setValue("nik");
        dto2.setText("Nama Karyawan");
        dto2.setValue("namaKaryawan");
        dto3.setText("Email");
        dto3.setValue("email");
        dto4.setText("Nama Jabatan");
        dto4.setValue("namaJabatan");
        dto5.setText("Status");
        dto5.setValue("status");
        dtoList.add(dto1);
        dtoList.add(dto2);
        dtoList.add(dto3);
        dtoList.add(dto4);
        dtoList.add(dto5);
        return dtoList;
    }

    @Override
    public List<OptionDTO> getSearchFilter(String filter) {
        List<OptionDTO> searchItem = new LinkedList<>();
        List<KeteranganAplikasi> keteranganAplikasis = keteranganAplikasiRepository.findAll();
        if (filter.isBlank()){
        }else {
            switch (filter){
                case "id" :

                    for (var tipeAplikasi : keteranganAplikasis){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(tipeAplikasi.getId().toString());
                        dto.setText(tipeAplikasi.getId().toString());
                        searchItem.add(dto);
                    }
                    break;
                case  "namaTipeAplikasi":

                    for (var tipeAplikasi : keteranganAplikasis){
                        OptionDTO dto = new OptionDTO();
                        dto.setValue(tipeAplikasi.getNamaKeteranganAplikasi());
                        dto.setText(tipeAplikasi.getNamaKeteranganAplikasi());
                        searchItem.add(dto);
                    }
                    break;
                case "status" :

                    for (var tipeAplikasi : keteranganAplikasis){
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
    public void updateInsert(UserFormDTO userFormDTO) {

    }

    @Override
    public void delete(String nik) {

    }


}
