package com.sales.rest;

import com.sales.dto.cabang.CabangFormDTO;
import com.sales.dto.cabang.CabangIndexOptionDTO;
import com.sales.dto.cabang.CabangProdukDTO;
import com.sales.entity.Produk;
import com.sales.service.cabang.CabangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/cabang")
public class CabangRestController {
    private final CabangService service;

    @Autowired
    public CabangRestController(CabangService service) {
        this.service = service;
    }

    @GetMapping(value = {"/getSearchItems={filter}"})
    public ResponseEntity<Object> getSearchItems(
            @PathVariable String filter
    ){
        try{
            List<CabangIndexOptionDTO> cabangIndexOptionDTOS = service.getSearchItems(filter);
            return ResponseEntity.status(HttpStatus.OK).body(cabangIndexOptionDTOS);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(
            @Valid @RequestBody CabangFormDTO cabangFormDTO,
            BindingResult bindingResult
    ){
        try{
            if(bindingResult.hasErrors()){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(bindingResult.getAllErrors());
            }else{
                service.save(cabangFormDTO);
                return ResponseEntity.status(HttpStatus.OK).body(cabangFormDTO);
            }
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/produk/{id}")
    public ResponseEntity<List<CabangProdukDTO>> getProdukByCabang(@PathVariable Integer id) {
        try {
            List<CabangProdukDTO> produkSet = service.getProdukByCabang(id);
            if (produkSet != null && !produkSet.isEmpty()) {
                return new ResponseEntity<>(produkSet, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("kelurahan-options")
    public ResponseEntity<Object> getKelurahan(){
        try{
            var optionKelurahan= service.getKelurahanOption();
            return ResponseEntity.status(HttpStatus.OK).body(optionKelurahan);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }



}
