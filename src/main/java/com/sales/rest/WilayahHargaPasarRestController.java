package com.sales.rest;

import com.sales.dto.FilterIndexOptionDTO;
import com.sales.service.wilayahHargaPasar.WilayahHargaPasarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
