package com.sales.rest;

import com.sales.dto.OptionDTO;
import com.sales.dto.cabang.CabangFormDTO;
import com.sales.dto.cabang.CabangProdukDTO;
import com.sales.dto.pot.PotFormDTO;
import com.sales.service.pot.PotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pot")
public class PotRestController {
    private final PotService service;

    @Autowired
    public PotRestController(PotService service) {
        this.service = service;
    }

    @GetMapping(value = {"/getSearchCabangItems={filter}"})
    public ResponseEntity<Object> getSearchItems(@PathVariable String filter){
        try{
            var searchItems= service.getSearchCabangItems(filter);
            return ResponseEntity.status(HttpStatus.OK).body(searchItems);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
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

    @GetMapping("/cabang={idPot}")
    public ResponseEntity<List<OptionDTO>> getCabangByPotId(@PathVariable Integer idPot) {
        try {
            List<OptionDTO> cabangPOT = service.getCabangByPotId(idPot);
            if (cabangPOT != null && !cabangPOT.isEmpty()) {
                return new ResponseEntity<>(cabangPOT, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(
            @Valid @RequestBody PotFormDTO potFormDTO,
            BindingResult bindingResult
    ){
        try{
            if(bindingResult.hasErrors()){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(bindingResult.getAllErrors());
            }else{
                service.savePOT(potFormDTO);
                return ResponseEntity.status(HttpStatus.OK).body(potFormDTO);
            }
        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
