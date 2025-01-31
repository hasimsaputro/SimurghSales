package com.sales.controller;

import com.sales.dto.master.TipeAplikasiDTO;
import com.sales.service.master.TipeAplikasiService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("tipeAplikasi")
public class TipeAplikasiController {
    private final TipeAplikasiService service;


    public TipeAplikasiController(TipeAplikasiService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(@RequestParam(defaultValue = "1") Integer page,@RequestParam(required = false,defaultValue = "") String filter,@RequestParam(required = false) String search, @RequestParam(required = false) String name, @RequestParam(required = false) Integer id, @RequestParam(required = false) Boolean status, Model model){
        var grid = service.getAll(id, name, status, page, filter, search);
        var listFilter = service.filter();
        model.addAttribute("listFilter",listFilter);
        model.addAttribute("filterSelected",filter);
        model.addAttribute("search",search);
        model.addAttribute("gridTipeAplikasi",grid);
        model.addAttribute("totalPage",service.totalPage(id, name, status));
        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("status",status);




        return "sales/tipe-aplikasi";
    }

    @GetMapping("form")
    public String form( @RequestParam(required = false) Integer id, Model model){
        var tipeAplikasiDto = service.getTipeAplikasiById(id);
        model.addAttribute("tipeAplikasiDto",tipeAplikasiDto);



        return "sales/tipeaplikasi-form";
    }

    @PostMapping("form")
    public String updateInsert(@ModelAttribute("tipeAplikasiDto") TipeAplikasiDTO tipeAplikasiDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sales/tipeaplikasi-form";
        } else {
            service.updateInsert(tipeAplikasiDTO);
            return "redirect:/tipeAplikasi";
        }
    }

    @GetMapping("detail")
    public String detail( @RequestParam(required = false) Integer id, Model model){
        var tipeAplikasiDto = service.getTipeAplikasiById(id);
        model.addAttribute("tipeAplikasiDto",tipeAplikasiDto);

        return "sales/tipeaplikasi-detail";
    }


}
