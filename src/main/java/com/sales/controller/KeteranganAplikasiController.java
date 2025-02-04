package com.sales.controller;

import com.sales.dto.master.KeteranganAplikasiFormDTO;

import com.sales.service.master.KeteranganAplikasiService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("keteranganAplikasi")
public class KeteranganAplikasiController {
    private final KeteranganAplikasiService service;


    public KeteranganAplikasiController(KeteranganAplikasiService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(@RequestParam(defaultValue = "1") Integer page,@RequestParam(required = false,defaultValue = "") String filter,@RequestParam(required = false) String search, @RequestParam(required = false) String name, @RequestParam(required = false) Integer id, @RequestParam(required = false) Boolean status, Model model){
        var grid = service.getAll(id, name, status, page, filter, search);
        var listFilter = service.filter();
        model.addAttribute("listFilter",listFilter);
        model.addAttribute("filterSelected",filter);
        model.addAttribute("search",search);
        model.addAttribute("gridKeteranganAplikasi",grid);
        model.addAttribute("totalPages",service.totalPage(id, name, status));
        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("status",status);
        model.addAttribute("currentPage",page);




        return "sales/keterangan-aplikasi";
    }

    @GetMapping("form")
    public String form( @RequestParam(required = false) Integer id, Model model){
        var dto = service.getKeteranganAplikasiById(id);
        model.addAttribute("dto",dto);



        return "sales/keterangan-aplikasi-form";
    }

    @PostMapping("form")
    public String updateInsert(@ModelAttribute("dto") KeteranganAplikasiFormDTO keteranganAplikasiFormDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sales/keterangan-aplikasi-form";
        } else {
            service.updateInsert(keteranganAplikasiFormDTO);
            return "redirect:/keteranganAplikasi";
        }
    }

    @GetMapping("detail")
    public String detail( @RequestParam(required = false) Integer id, Model model){
        var dto = service.getKeteranganAplikasiById(id);
        model.addAttribute("dto",dto);

        return "sales/keterangan-aplikasi-detail";
    }

    @GetMapping("delete")
    public String delete( @RequestParam(required = false) Integer id, Model model){
        service.delete(id);
        return "redirect:/keteranganAplikasi";
    }


}
