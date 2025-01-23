package com.sales.rest;

import com.sales.service.dataLeads.DataLeadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("kategori")
    public ResponseEntity<Object> getOptioonKategori(){
        try{
            var optionKategori= service.getOptionKategori();
            return ResponseEntity.status(HttpStatus.OK).body(optionKategori);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

   @GetMapping("merk")
    public ResponseEntity<Object> getOptioonMerk(Integer kategoriId){
        try{
            var optionMerk= service.getOptionMerek(kategoriId);
            return ResponseEntity.status(HttpStatus.OK).body(optionMerk);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping("model")
    public ResponseEntity<Object> getOptioonModel(Integer idKategori, String idMerk, String tipeId){
        try{
            var optionModel= service.getOptionModel(idKategori,idMerk,tipeId);
            return ResponseEntity.status(HttpStatus.OK).body(optionModel);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping("tipe")
    public ResponseEntity<Object> getOptioonTipe(Integer idKategori, String idMerk){
        try{
            var optionTipe= service.getOptionTipe( idKategori,  idMerk);
            return ResponseEntity.status(HttpStatus.OK).body(optionTipe);
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
