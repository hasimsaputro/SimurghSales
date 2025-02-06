package com.sales.rest;

import com.sales.entity.HargaPasar;
import com.sales.service.hargaPasar.HargaPasarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hargaPasar")
public class HargaPasarRestController {
    private final HargaPasarService service;

    @Autowired
    public HargaPasarRestController(HargaPasarService service) {
        this.service = service;
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
    @GetMapping(value = {"/getWilayah"})
    public ResponseEntity<Object> getWilayahItems(){
        try{
            var searchItems= service.getWilayahItems();
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping(value = {"/getMerk"})
    public ResponseEntity<Object> getMerkItems(){
        try{
            var searchItems= service.getMerkItems();
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping(value = {"/getTipe"})
    public ResponseEntity<Object> getTipeItems(){
        try{
            var searchItems= service.getTipeItems();
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping(value = {"/getModel"})
    public ResponseEntity<Object> getModelItems(){
        try{
            var searchItems= service.getModelItems();
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping(value = {"/getTipeJenis={idTipe}"})
    public ResponseEntity<Object> getJenis(@PathVariable String idTipe){
        try{
            var searchItems= service.getTipeJenis(idTipe);
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }
    @GetMapping(value = {"/getTipeUnit"})
    public ResponseEntity<Object> getTipeUnitItems(){
        try{
            var searchItems= service.getTipeUnitItems();
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }
}
