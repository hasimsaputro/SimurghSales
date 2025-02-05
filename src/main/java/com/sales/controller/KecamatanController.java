package com.sales.controller;

import com.sales.dto.kabupaten.KabupatenFormDTO;
import com.sales.dto.kecamatan.KecamatanFormDTO;
import com.sales.service.kecamatan.KecamatanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("kecamatan")
public class KecamatanController {
    private final KecamatanService service;

    @Autowired
    public KecamatanController(KecamatanService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String search,
            Model model
    ){
        model.addAttribute("kecamatanGrid", service.getAllKecamatan(page, filter, search));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(filter, search));
        model.addAttribute("filter", filter);
        model.addAttribute("search", search);
        model.addAttribute("filterItem", service.getFilterAsItem());

        return "master/kecamatan/master-kecamatan";
    }

    @GetMapping("form")
    public String form(
            @RequestParam(required = false) Integer id,
            Model model
    ){
        model.addAttribute("formKecamatanGrid", service.getKecamatanFormById(id));

        return "master/kecamatan/master-kecamatan-form";
    }

    @PostMapping("form")
    public String form(
            @Valid @ModelAttribute("kecamatan")
                    KecamatanFormDTO kecamatanFormDTO,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return "master/kecamatan/master-kecamatan-form";
        } else {
            service.save(kecamatanFormDTO);
            return "redirect:/kecamatan";
        }
    }

    @GetMapping("detail")
    public String detail(
            @RequestParam(required = false) Integer id,
            Model model
    ){
        model.addAttribute("detailKecamatanGrid", service.getKecamatanDetailById(id));
        return "master/kecamatan/master-kecamatan-detail";
    }

    @GetMapping("delete")
    public String delete(
            @RequestParam(required = true) Integer id
    ){
        service.delete(id);
        return "redirect:/kecamatan";
    }
}
