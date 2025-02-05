package com.sales.rest;

import com.sales.dto.cabang.CabangFormDTO;
import com.sales.dto.cabang.CabangIndexDTO;
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

    @GetMapping(value = {"","/filter={filter}/search={search}"})
    public ResponseEntity<Object> index(@PathVariable(required = false) String filter,
                                        @PathVariable(required = false) String search,
                                        @PathVariable(required = false) Integer page){
        page = page == null? 1 : page;
        filter = filter == null? "" : filter;
        search = search == null? "" : search;
        try{
            List<CabangIndexDTO> cabang= service.getAll(page,filter,search);
            return ResponseEntity.status(HttpStatus.OK).body(cabang);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
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

    @GetMapping(value = {"/getFilterItems"})
    public ResponseEntity<Object> getFilterItems(){
        try{
            var filterItem= service.getfilterAsItem();
            return ResponseEntity.status(HttpStatus.OK).body(filterItem);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
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



}
