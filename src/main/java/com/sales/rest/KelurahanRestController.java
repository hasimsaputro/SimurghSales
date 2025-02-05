package com.sales.rest;

import com.sales.dto.FilterIndexOptionDTO;
import com.sales.dto.FilterOptionDTO;
import com.sales.service.kelurahan.KelurahanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> getKabupaten(
            @RequestParam(required = false) Integer parent
    ){
        try {
            List<FilterOptionDTO> optionKabupaten = service.getKabupatenOption(parent);
            return ResponseEntity.status(HttpStatus.OK).body(optionKabupaten);
        } catch (ConfigDataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data tidak ditemukan");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Terjadi kesalahan pada server");
        }
    }

    @GetMapping("kecamatan-options")
    public ResponseEntity<Object> getKecamatan(
            @RequestParam(required = false) Integer parent
    ){
        try{
            List<FilterOptionDTO> optionKecamatan= service.getKecamatanOption(parent);
            return ResponseEntity.status(HttpStatus.OK).body(optionKecamatan);
        } catch (ConfigDataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data tidak ditemukan");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Terjadi kesalahan pada server");
        }
    }
}
