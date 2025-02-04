package com.sales.rest;

import com.sales.dto.mitraAgen.*;
import com.sales.service.mitraAgen.MitraAgenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @GetMapping("form/{id}")
    public ResponseEntity<Object> insertUpdate(
            @PathVariable String id
    ){
        try {
            MitraAgenFormDTO mitraAgenFormDTO = service.getMitraAgenById(id);
            return ResponseEntity.status(HttpStatus.OK).body(mitraAgenFormDTO);
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("form")
    public ResponseEntity<Object> insertUpdate(
            @Valid @RequestBody MitraAgenFormDTO mitraAgenFormDTO,
            BindingResult bindingResult
    ){
        try {
            if (bindingResult.hasErrors()){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(bindingResult.getAllErrors());
            }else {
                service.save(mitraAgenFormDTO);
                return ResponseEntity.status(HttpStatus.OK).body("Success");
            }
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping(value = {"tipeMaster-options"})
    public ResponseEntity<Object> getTipeMasterOptions(){
        try{
            List<TipeMasterOptionDTO> tipeMasterOptionDTOS = service.getTipeMasterOption();
            return ResponseEntity.status(HttpStatus.OK).body(tipeMasterOptionDTOS);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


    @GetMapping(value = {"produk-options"})
    public ResponseEntity<Object> getProdukOptions(
            @RequestParam int page,
            @RequestParam int size
    ){
        try{
            List<ProdukOptionDTO> produkOptionDTOS = service.getProdukOption(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(produkOptionDTOS);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
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

    @GetMapping("identitas-options")
    public ResponseEntity<Object> getIdentitasOptions(){
        try{
            List<IdentitasOptionDTO> identitasOptionDTOS = service.getIdentitasOption();
            return ResponseEntity.status(HttpStatus.OK).body(identitasOptionDTOS);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("bank-options")
    public ResponseEntity<Object> getBankOptions(){
        try{
            List<BankOptionDTO> bankOptionDTOS = service.getBankOption();
            return ResponseEntity.status(HttpStatus.OK).body(bankOptionDTOS);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


}
