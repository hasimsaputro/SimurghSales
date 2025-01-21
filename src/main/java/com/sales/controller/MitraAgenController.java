package com.sales.controller;

import com.sales.service.mitraAgen.MitraAgenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("mitraAgen")
public class MitraAgenController {
    private final MitraAgenService service;

    public MitraAgenController(MitraAgenService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) Integer tipe,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer kelurahan,
            @RequestParam(required = false) Integer cabang,
            @RequestParam(required = false) Boolean status,
            Model model
    ){
        model.addAttribute("mitraAgenGrid", service.getAll(page, id, tipe, name, kelurahan, cabang, status));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(id, tipe, name, kelurahan,cabang,status));
        return "sales/sales-mitraAgen";
    }

    @GetMapping("form")
    public String form(Model model) { return "sales/sales-mitraAgen-form";}

    @GetMapping("detail")
    public String detail(
            @RequestParam(required = false) String id,
            Model model
    ){
        model.addAttribute("detailMitraAgenGrid", service.getDetailMitraAgenById(id));
        return "sales/sales-mitraAgen-detail";
    }
}
