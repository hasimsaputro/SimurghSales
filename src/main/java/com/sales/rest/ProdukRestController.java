package com.sales.rest;

import com.sales.dto.cabang.CabangIndexOptionDTO;
import com.sales.dto.cabang.CabangProdukGridDTO;
import com.sales.dto.mitraAgen.MitraAgenDetailDTO;
import com.sales.dto.produk.ProdukIndexDTO;
import com.sales.dto.produk.ProdukIndexOptionDTO;
import com.sales.service.produk.ProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/produk")
public class ProdukRestController {
    private final ProdukService service;

    @Autowired
    public ProdukRestController(ProdukService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<Object> index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "status") String filter,
            @RequestParam(defaultValue = "Aktif") String search
    ){
        try {
            CabangProdukGridDTO cabangProdukGridDTO = service.getAllRest(page, filter, search);
            return ResponseEntity.status(HttpStatus.OK).body(cabangProdukGridDTO);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("produks")
    public ResponseEntity<Object> getAll(){
        try {
            List<ProdukIndexDTO> produks = service.getAllProduks();
            return ResponseEntity.status(HttpStatus.OK).body(produks);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping(value = {"/getFilterItems"})
    public ResponseEntity<Object> getFilterItems(){
        try{
            var filterItem= service.getFilterAsItem();
            return ResponseEntity.status(HttpStatus.OK).body(filterItem);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        }
    }

    @GetMapping(value = {"/getSearchItems={filter}"})
    public ResponseEntity<Object> getSearchItems(
            @PathVariable String filter
    ){
        try{
            List<ProdukIndexOptionDTO> produkIndexOptionDTOS = service.getSearchItems(filter);
            return ResponseEntity.status(HttpStatus.OK).body(produkIndexOptionDTOS);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
