package com.sales.controller;

import com.sales.dto.produk.ProdukFormDTO;
import com.sales.service.produk.ProdukService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("produk")
public class ProdukController {
    private final ProdukService service;

    @Autowired
    public ProdukController(ProdukService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String search,
            Model model
    ){
        model.addAttribute("produkGrid", service.getAll(page, filter, search));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(filter, search));
        model.addAttribute("filter", filter);
        model.addAttribute("search", search);
        model.addAttribute("filterItem", service.getFilterAsItem());
        return "master/produk/master-produk";
    }

    @GetMapping("detail")
    public String detail(
            @RequestParam(required = false) Integer id,
            Model model
    ){
        model.addAttribute("detailProdukGrid", service.getDetailProdukById(id));
        return "master/produk/master-produk-detail";
    }

    @GetMapping("form")
    public String form(
            @RequestParam(defaultValue = "") Integer id,
            Model  model
    ){
        model.addAttribute("produkByIdGrid", service.getProdukById(id));
        return "master/produk/master-produk-form";
    }

    @PostMapping("form")
    public String form(
            @Valid @ModelAttribute("produk")
                    ProdukFormDTO produkFormDTO,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return "master/produk/master-produk-form";
        } else {
            service.save(produkFormDTO);
            return "redirect:/produk";
        }
    }

    @GetMapping("delete")
    public String delete(Integer id){
        service.delete(id);
        return "redirect:/produk";
    }


}
