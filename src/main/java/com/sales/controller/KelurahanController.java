package com.sales.controller;

import com.sales.dto.kelurahan.KelurahanFormDTO;
import com.sales.service.kelurahan.KelurahanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("kelurahan")
public class KelurahanController {
    private final KelurahanService service;

    @Autowired
    public KelurahanController(KelurahanService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String search,
            Model model
    ){
        model.addAttribute("kelurahanGrid", service.getAllKelurahan(page, filter, search));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(filter, search));
        model.addAttribute("filter", filter);
        model.addAttribute("search", search);
        model.addAttribute("filterItem", service.getFilterAsItem());

        return "master/kelurahan/master-kelurahan";
    }

    @GetMapping("form")
    public String form(
            @RequestParam(required = false) Integer id,
            Model model
    ){
        model.addAttribute("formKelurahanGrid", service.getKelurahanFormById(id));

        return "master/kelurahan/master-kelurahan-form";
    }

    @PostMapping("form")
    public String form(
            @Valid @ModelAttribute("kelurahan")
                    KelurahanFormDTO kelurahanFormDTO,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return "master/kelurahan/master-kelurahan-form";
        } else {
            service.save(kelurahanFormDTO);
            return "redirect:/kelurahan";
        }
    }

    @GetMapping("detail")
    public String detail(
            @RequestParam(required = false) Integer id,
            Model model
    ){
        model.addAttribute("detailKelurahanGrid", service.getKelurahanDetailById(id));
        return "master/kelurahan/master-kelurahan-detail";
    }

    @GetMapping("delete")
    public String delete(
            @RequestParam(required = true) Integer id
    ){
        service.delete(id);
        return "redirect:/kelurahan";
    }
}
