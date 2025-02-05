package com.sales.controller;

import com.sales.dto.cabang.CabangFormDTO;
import com.sales.dto.cabang.CabangProdukDTO;
import com.sales.service.cabang.CabangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("cabang")
public class CabangController {
    private final CabangService service;

    @Autowired
    public CabangController(CabangService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String search,
            Model model
    ){
        model.addAttribute("cabangGrid", service.getAll(page, filter, search));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(filter, search));
        model.addAttribute("filter", filter);
        model.addAttribute("search", search);
        model.addAttribute("filterItem", service.getFilterAsItem());
        return "master/cabang/master-cabang";
    }

    @GetMapping("detail")
    public String detail(
            @RequestParam(required = false) Integer id,
            Model model
    ){
        List<CabangProdukDTO> cabangProdukDTOS = service.getProdukByCabang(id);
        cabangProdukDTOS.sort(Comparator.comparing(CabangProdukDTO::getId));
        model.addAttribute("detailCabangGrid", service.getDetailCabangById(id));
        model.addAttribute("detailProdukByCabang",cabangProdukDTOS);
        return "master/cabang/master-cabang-detail";
    }

    @GetMapping("form")
    public String form(
            @RequestParam(defaultValue = "") Integer id,
            Model  model
    ){
        model.addAttribute("cabangByIdGrid", service.getCabangById(id));
        return "master/cabang/master-cabang-form";
    }

    @PostMapping("form")
    public String form(
            @Valid @ModelAttribute("cabang")
            CabangFormDTO cabangFormDTO,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return "master/cabang/master-cabang-form";
        } else {
            service.save(cabangFormDTO);
            return "redirect:/cabang";
        }
    }

    @GetMapping("delete")
    public String delete(Integer id){
        service.delete(id);
        return "redirect:/cabang";
    }

}
