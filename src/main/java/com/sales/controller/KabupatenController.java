package com.sales.controller;

import com.sales.dto.kabupaten.KabupatenFormDTO;
import com.sales.service.kabupaten.KabupatenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("kabupaten")
public class KabupatenController {
    private final KabupatenService service;

    @Autowired
    public KabupatenController(KabupatenService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String search,
            Model model
    ){
        model.addAttribute("kabupatenGrid", service.getAllKabupaten(page, filter, search));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(filter, search));
        model.addAttribute("filter", filter);
        model.addAttribute("search", search);
        model.addAttribute("filterItem", service.getFilterAsItem());

        return "master/kabupaten/master-kabupaten";
    }

    @GetMapping("form")
    public String form(
            @RequestParam(required = false) Integer id,
            Model model
    ){
        model.addAttribute("formKabupatenGrid", service.getKabupatenFormById(id));

        return "master/kabupaten/master-kabupaten-form";
    }

    @PostMapping("form")
    public String form(
            @Valid @ModelAttribute("kabupaten")
                    KabupatenFormDTO kabupatenFormDTO,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return "master/kabupaten/master-kabupaten-form";
        } else {
            service.save(kabupatenFormDTO);
            return "redirect:/kabupaten";
        }
    }

    @GetMapping("detail")
    public String detail(
            @RequestParam(required = false) Integer id,
            Model model
    ){
        model.addAttribute("detailKabupatenGrid", service.getKabupatenDetailById(id));
        return "master/kabupaten/master-kabupaten-detail";
    }

    @GetMapping("delete")
    public String delete(
            @RequestParam(required = true) Integer id
    ){
        service.delete(id);
        return "redirect:/kabupaten";
    }
}
