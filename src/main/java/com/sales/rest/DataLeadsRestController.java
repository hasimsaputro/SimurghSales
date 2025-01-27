package com.sales.rest;

import com.sales.dto.dataLeads.EstimasiNilaiFundingDTO;
import com.sales.service.dataLeads.DataLeadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dataLeads")
public class DataLeadsRestController {
    private final DataLeadsService service;

    @Autowired
    public DataLeadsRestController(DataLeadsService service) {
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

    @GetMapping(value = {"/getKelurahan"})
    public ResponseEntity<Object> getKelurahanItems(){
        try{
            var searchItems= service.getKelurahanItems();
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping(value = {"/getPOT"})
    public ResponseEntity<Object> getPotItems(){
        try{
            var searchItems= service.getPotItems();
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping(value = {"/getPOTData={idPOT}"})
    public ResponseEntity<Object> getPotData(@PathVariable Integer idPOT){
        try{
            var searchItems= service.getPotData(idPOT);
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @PostMapping(value = {"/estimasiNilai"})
    public ResponseEntity<Object> getEstimasiNilaiFunding(@RequestBody EstimasiNilaiFundingDTO dto){
        try{
            var estimasiNilaiFunding= service.getEstimasiNilaiFunding(dto);
            return ResponseEntity.status(HttpStatus.OK).body(estimasiNilaiFunding);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping("keteranganAplikasi")
    public ResponseEntity<Object> getOptioonKeteranganAplikasi(){
        try{
            var optionKeteranganAplikasi= service.getOptionKeteranganAplikasi();
            return ResponseEntity.status(HttpStatus.OK).body(optionKeteranganAplikasi);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping("referensi")
    public ResponseEntity<Object> getOptioonReferensi(){
        try{
            var optionReferensi= service.getOptionReferensi();
            return ResponseEntity.status(HttpStatus.OK).body(optionReferensi);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping("sumberDataAplikasi")
    public ResponseEntity<Object> getSumberDataApliakasi(){
        try{
            var optionSumberDataAplikasi= service.getOptionSumberDataAplikasi();
            return ResponseEntity.status(HttpStatus.OK).body(optionSumberDataAplikasi);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

}
