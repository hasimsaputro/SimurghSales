package com.sales.rest;

import com.sales.dto.pot.CabangPotDTO;
import com.sales.dto.pot.PotFormDTO;
import com.sales.dto.wilayahHargaPasar.CabangWilayahDTO;
import com.sales.dto.wilayahHargaPasar.WilayahHargaPasarFormDTO;
import com.sales.service.pot.PotService;
import com.sales.service.wilayahHargaPasar.WilayahHargaPasarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/wilayahHargaPasar")
public class WilayahHargaPasarRestController {
    private final WilayahHargaPasarService service;

    @Autowired
    public WilayahHargaPasarRestController(WilayahHargaPasarService service) {
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

    @GetMapping(value = {"/getFilterItems"})
    public ResponseEntity<Object> getFilterItems(){
        try{
            var filterItem= service.getFilterCabang();
            return ResponseEntity.status(HttpStatus.OK).body(filterItem);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping("/cabang/{idWilayah}")
    public ResponseEntity<List<CabangWilayahDTO>> getCabangByPotId(@PathVariable String idWilayah) {
        try {
            List<CabangWilayahDTO> cabangWilayah = service.getCabangByWilayahId(idWilayah);
            if (cabangWilayah != null && !cabangWilayah.isEmpty()) {
                return new ResponseEntity<>(cabangWilayah, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(
            @Valid @RequestBody WilayahHargaPasarFormDTO wilayahHargaPasarFormDTO,
            BindingResult bindingResult
    ){
        System.out.println("Received cabangList: " + wilayahHargaPasarFormDTO.getCabangList());
        try{
            if(bindingResult.hasErrors()){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(bindingResult.getAllErrors());
            }else{
                service.saveWilayah(wilayahHargaPasarFormDTO);
                return ResponseEntity.status(HttpStatus.OK).body(wilayahHargaPasarFormDTO);
            }
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
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
}
