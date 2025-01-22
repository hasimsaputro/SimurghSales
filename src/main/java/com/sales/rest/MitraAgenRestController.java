package com.sales.rest;

import com.sales.dto.mitraAgen.MitraAgenDetailDTO;
import com.sales.dto.mitraAgen.MitraAgenIndexOptionDTO;
import com.sales.service.mitraAgen.MitraAgenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mitraAgen")
public class MitraAgenRestController {
    private final MitraAgenService service;

    @Autowired
    public MitraAgenRestController(MitraAgenService service) {
        this.service = service;
    }

    @GetMapping(value = {"/getSearchItems={filter}"})
    public ResponseEntity<Object> getSearchItems(
            @PathVariable String filter
    ){
        try{
            List<MitraAgenIndexOptionDTO> mitraAgenIndexOptionDTOS = service.getSearchItems(filter);
            return ResponseEntity.status(HttpStatus.OK).body(mitraAgenIndexOptionDTOS);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("detail")
    public ResponseEntity<Object> detailMitraAgen(
            @RequestParam(required = false) String id
    ){
        try {
            MitraAgenDetailDTO mitraAgenDetailDTO = service.getDetailMitraAgenById(id);
            return ResponseEntity.status(HttpStatus.OK).body(mitraAgenDetailDTO);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


}
