package com.sales.controller;

import com.sales.dto.identitas.IdentitasFormDTO;
import com.sales.service.identitas.IdentitasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("identitas")
public class IdentitasController {
    private final IdentitasService service;

    @Autowired
    public IdentitasController(IdentitasService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String search,
            Model model
    ){
        model.addAttribute("identitasGrid", service.getAll(page, filter, search));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(filter, search));
        model.addAttribute("filter", filter);
        model.addAttribute("search", search);
        model.addAttribute("filterItem", service.getFilterAsItem());
        return "master/identitas/master-identitas";
    }

    @GetMapping("detail")
    public String detail(
            @RequestParam(required = false) Integer id,
            Model model
    ){
        model.addAttribute("detailIdentitasGrid", service.getDetailIdentitasById(id));
        return "master/identitas/master-identitas-detail";
    }

    @GetMapping("form")
    public String form(
            @RequestParam(defaultValue = "") Integer id,
            Model model
    ){
        model.addAttribute("identitasByIdGrid", service.getIdentitasById(id));
        return "master/identitas/master-identitas-form";
    }

    @PostMapping("form")
    public String form(
            @Valid @ModelAttribute("identitas")
                    IdentitasFormDTO identitasFormDTO,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return "master/identitas/master-identitas-form";
        } else {
            service.save(identitasFormDTO);
            return "redirect:/identitas";
        }
    }

    @GetMapping("delete")
    public String delete(Integer id){
        service.delete(id);
        return "redirect:/identitas";
    }
}
