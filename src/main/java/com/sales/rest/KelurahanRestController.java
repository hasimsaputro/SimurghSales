package com.sales.rest;

import com.sales.dto.FilterIndexOptionDTO;
import com.sales.dto.FilterOptionDTO;
import com.sales.service.kelurahan.KelurahanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/kelurahan")
public class KelurahanRestController {
    private final KelurahanService service;

    @Autowired
    public KelurahanRestController(KelurahanService service) {
        this.service = service;
    }

    @GetMapping(value = {"/getSearchItems={filter}"})
    public ResponseEntity<Object> getSearchItems(
            @PathVariable String filter
    ){
        try{
            List<FilterIndexOptionDTO> filterIndexOptionDTOS = service.getSearchItems(filter);
            return ResponseEntity.status(HttpStatus.OK).body(filterIndexOptionDTOS);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("provinsi-options")
    public ResponseEntity<Object> getProvinsi(){
        try{
            List<FilterOptionDTO> optionProvinsi= service.getProvinsiOption();
            return ResponseEntity.status(HttpStatus.OK).body(optionProvinsi);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping("kabupaten-options")
    public ResponseEntity<Object> getKabupaten(){
        try{
            List<FilterOptionDTO> optionKabupaten= service.getKabupatenOption();
            return ResponseEntity.status(HttpStatus.OK).body(optionKabupaten);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping("kecamatan-options")
    public ResponseEntity<Object> getKecamatan(){
        try{
            List<FilterOptionDTO> optionKecamatan= service.getKecamatanOption();
            return ResponseEntity.status(HttpStatus.OK).body(optionKecamatan);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }
}
