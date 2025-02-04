package com.sales.controller;

import com.sales.dto.merk.MerkFormDTO;
import com.sales.service.merk.MerkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("merk")
public class MerkController {
    private final MerkService service;

    @Autowired
    public MerkController(MerkService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(@RequestParam(defaultValue = "") String filter,
                        @RequestParam(defaultValue = "") String search,
                        @RequestParam(defaultValue = "1") int page, Model model){

        var grid = service.getAll(filter,search,page);
        int total = service.getTotal(filter,search);
        model.addAttribute("grid", grid);
        model.addAttribute("totalPages",total);
        model.addAttribute("currentPage",page);
        model.addAttribute("filterItem",service.getfilterAsItem());
        model.addAttribute("search",search);
        return "master/merk/merk";
    }
    @GetMapping("form")
    public String indexForm(@RequestParam(defaultValue ="")String merkId,Model model){
        var merk = service.getMerkById(merkId);
        model.addAttribute("formMerk", merk);
        return "master/merk/merk-form";
    }
    @PostMapping("form")
    public String indexForm(@Valid @ModelAttribute("formMerk") MerkFormDTO dto,
                            BindingResult validation){
        if(validation.hasErrors()){
            return "master/merk/merk-form";
        }else {
            service.saveMerk(dto);
            return "redirect:/merk";
        }
    }

    @GetMapping("detail")
    public String detail(Model model,@RequestParam(defaultValue = "")String merkId) {
        var merk = service.getMerkByIdDetail(merkId);
        model.addAttribute("merk",merk);
        return "master/merk/merk-detail";
    }
    @GetMapping("delete")
    public String indexDelete(@RequestParam(defaultValue = "") String merkId){
        service.deleteMerk(merkId);
        return "redirect:/merk";
    }
}
