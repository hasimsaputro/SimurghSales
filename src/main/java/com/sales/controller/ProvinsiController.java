package com.sales.controller;

import com.sales.dto.provinsi.ProvinsiFormDTO;
import com.sales.service.provinsi.ProvinsiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("provinsi")
public class ProvinsiController {
    private final ProvinsiService service;

    @Autowired
    public ProvinsiController(ProvinsiService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String search,
            Model model
    ){
        model.addAttribute("provinsiGrid", service.getAllProvinsi(page, filter, search));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(filter, search));
        model.addAttribute("filter", filter);
        model.addAttribute("search", search);
        model.addAttribute("filterItem", service.getFilterAsItem());

        return "master/provinsi/master-provinsi";
    }

    @GetMapping("form")
    public String form(
            @RequestParam(required = false) Integer id,
            Model model
    ){
        model.addAttribute("formProvinsiGrid", service.getProvinsiFormById(id));

        return "master/provinsi/master-provinsi-form";
    }

    @PostMapping("form")
    public String form(
            @Valid @ModelAttribute("provinsi")
            ProvinsiFormDTO provinsiFormDTO,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return "master/provinsi/master-provinsi-form";
        } else {
            service.save(provinsiFormDTO);
            return "redirect:/provinsi";
        }
    }

    @GetMapping("detail")
    public String detail(
            @RequestParam(required = false) Integer id,
            Model model
    ){
        model.addAttribute("detailProvinsiGrid", service.getProvinsiDetailById(id));
        return "master/provinsi/master-provinsi-detail";
    }

    @GetMapping("delete")
    public String delete(
        @RequestParam(required = true) Integer id
    ){
        service.delete(id);
        return "redirect:/provinsi";
    }
}
