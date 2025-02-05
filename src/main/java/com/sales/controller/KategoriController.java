package com.sales.controller;

import com.sales.dto.kategori.KategoriFormDTO;
import com.sales.dto.pot.PotFormDTO;
import com.sales.service.kategori.KategoriService;
import com.sales.service.pot.PotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("kategori")
public class KategoriController {
    private final KategoriService service;

    @Autowired
    public KategoriController(KategoriService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(@RequestParam(defaultValue = "") String filter,
                        @RequestParam(defaultValue = "") String search,
                        @RequestParam(defaultValue = "1") int page, Model model){

        var grid = service.getAll(filter,search,page);
        int total = service.getTotal(filter,search);
        model.addAttribute("grid", grid);
        model.addAttribute("totalPages",total);
        model.addAttribute("currentPage",page);
        model.addAttribute("filterItem",service.getfilterAsItem());
        model.addAttribute("search",search);
        return "master/kategori/kategori";
    }
    @GetMapping("form")
    public String indexForm(@RequestParam(defaultValue ="0")Integer kategoriId,Model model){
        var kategori = service.getKategoriById(kategoriId);
        model.addAttribute("formKategori", kategori);
        return "master/kategori/kategori-form";
    }

    @PostMapping("form")
    public String indexForm(@Valid @ModelAttribute("formKategori") KategoriFormDTO dto,
                            BindingResult validation,
                            Model model){
        if(validation.hasErrors()){
            return "master/kategori/kategori-form";
        }else {
            service.saveKategori(dto);
            return "redirect:/kategori";
        }
    }

    @GetMapping("detail")
    public String detail(Model model,@RequestParam(defaultValue = "0")Integer kategoriId) {
        var kategori = service.getKategoriByIdDetail(kategoriId);
        model.addAttribute("kategori",kategori);
        return "master/kategori/kategori-detail";
    }
//
    @GetMapping("delete")
    public String indexDelete(@RequestParam(defaultValue = "0") Integer kategoriId){
        service.deleteKategori(kategoriId);
        return "redirect:/kategori";
    }

}
