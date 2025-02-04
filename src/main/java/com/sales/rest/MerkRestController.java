package com.sales.rest;

import com.sales.dto.dataLeads.EstimasiNilaiFundingDTO;
import com.sales.service.dataLeads.DataLeadsService;
import com.sales.service.merk.MerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/merk")
public class MerkRestController {
    private final MerkService service;

    @Autowired
    public MerkRestController(MerkService service) {
        this.service = service;
    }

    @GetMapping(value = {"/getSearchItems={filter}"})
    public ResponseEntity<Object> getSearchItems(@PathVariable String filter){
        try{
            var searchItems= service.getSearchItems(filter);
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping(value = {"/getKategori"})
    public ResponseEntity<Object> getKategoriItems(){
        try{
            var searchItems= service.getKategoriItems();
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping(value = {"/getNegara"})
    public ResponseEntity<Object> getNegaraItems(){
        try{
            var searchItems= service.getNegaraItems();
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

}
