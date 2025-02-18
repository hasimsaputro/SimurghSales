package com.sales.rest;

import com.sales.service.jabatan.JabatanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jabatan")
public class JabatanRestController {
    private final JabatanService service;

    @Autowired
    public JabatanRestController(JabatanService service) {
        this.service = service;
    }

    @GetMapping("/getSearchItems/{filter}")
    public ResponseEntity<Object> getSearchItems(@PathVariable String filter){
        try {
            var searchItems = service.getSearchFilter(filter);
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

}
