package com.sales.controller;

import com.sales.dto.mitraAgen.MitraAgenFormDTO;
import com.sales.service.mitraAgen.MitraAgenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("mitraAgen")
public class MitraAgenController {
    private final MitraAgenService service;

    @Autowired
    public MitraAgenController(MitraAgenService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) String search,
            Model model
    ){
        model.addAttribute("mitraAgenGrid", service.getAll(page, filter, search));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(filter, search));
        model.addAttribute("search", search);
        model.addAttribute("filterItem", service.getFilterAsItem());
        return "sales/sales-mitraAgen";
    }

    @GetMapping("form")
    public String form(
            @RequestParam(required = false) String id,
            Model model
    ) {
        model.addAttribute("mitraAgenByIdGrid",service.getMitraAgenById(id));
        return "sales/sales-mitraAgen-form";
    }

    @PostMapping("form")
    public String form(
            @Valid @ModelAttribute("mitraAgen")
            MitraAgenFormDTO mitraAgenFormDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()){
            return "mitraAgen/form";
        } else {
            service.save(mitraAgenFormDTO);
            return "redirect:/mitraAgen";
        }
    }

    @GetMapping("detail")
    public String detail(
            @RequestParam(required = false) String id,
            Model model
    ){
        model.addAttribute("detailMitraAgenGrid", service.getDetailMitraAgenById(id));
        return "sales/sales-mitraAgen-detail";
    }

    @GetMapping("delete")
    public String delete(String id){
        service.delete(id);
        return "redirect:/mitraAgen";
    }
}
