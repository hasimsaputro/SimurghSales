package com.sales.controller;

import com.sales.dto.merk.MerkFormDTO;
import com.sales.dto.tipe.TipeFormDTO;
import com.sales.service.merk.MerkService;
import com.sales.service.tipe.TipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("tipe")
public class TipeController {
    private final TipeService service;

    @Autowired
    public TipeController(TipeService service) {
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
        return "master/tipe/tipe";
    }
    @GetMapping("form")
    public String indexForm(@RequestParam(defaultValue ="")String tipeId,Model model){
        var tipe = service.getTipeById(tipeId);
        model.addAttribute("formTipe", tipe);
        return "master/tipe/tipe-form";
    }
    @PostMapping("form")
    public String indexForm(@Valid @ModelAttribute("formTipe") TipeFormDTO dto,
                            BindingResult validation){
        if(validation.hasErrors()){
            return "master/tipe/tipe-form";
        }else {
            service.saveTipe(dto);
            return "redirect:/tipe";
        }
    }

    @GetMapping("detail")
    public String detail(Model model,@RequestParam(defaultValue = "")String tipeId) {
        var tipe = service.getTipeByIdDetail(tipeId);
        model.addAttribute("tipe",tipe);
        return "master/tipe/tipe-detail";
    }
    @GetMapping("delete")
    public String indexDelete(@RequestParam(defaultValue = "") String tipeId){
        service.deleteTipe(tipeId);
        return "redirect:/tipe";
    }
}
