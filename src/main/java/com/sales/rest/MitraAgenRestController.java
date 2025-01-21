package com.sales.rest;

import com.sales.dto.mitraAgen.MitraAgenDetailDTO;
import com.sales.service.mitraAgen.MitraAgenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mitraAgen")
public class MitraAgenRestController {
    private final MitraAgenService service;

    @Autowired
    public MitraAgenRestController(MitraAgenService service) {
        this.service = service;
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
