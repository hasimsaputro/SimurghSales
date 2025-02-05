package com.sales.controller;

import com.sales.dto.OptionDTO;
import com.sales.dto.cabang.CabangProdukDTO;
import com.sales.dto.pot.CabangPotDTO;
import com.sales.dto.pot.PotFormDTO;
import com.sales.service.pot.PotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("pot")
public class POTController {
    private final PotService service;

    @Autowired
    public POTController(PotService service) {
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
        return "master/pot/pot";
    }
    @GetMapping("form")
    public String indexForm(@RequestParam(defaultValue ="0")Integer potId,Model model){
        var pot = service.getPotById(potId);
        model.addAttribute("formPOT", pot);
        return "master/pot/pot-form";
    }
    @GetMapping("detail")
    public String detail(Model model,@RequestParam(defaultValue = "0")Integer potId) {
        List<CabangPotDTO> cabangPot = service.getCabangByPotId(potId);
        cabangPot.sort(Comparator.comparing(CabangPotDTO::getNamaCabang));
        model.addAttribute("pot", service.getPotByIdDetail(potId));
        model.addAttribute("detailCabangByPot",cabangPot);
        return "master/pot/pot-detail";
    }
//
    @GetMapping("delete")
    public String indexDelete(@RequestParam(defaultValue = "0") Integer potId){
        service.deletePot(potId);
        return "redirect://pot";
    }

//    @GetMapping("cabang")
//    public String indexCabangFormPOT(@RequestParam(defaultValue ="0")Integer potId,
//                        @RequestParam(defaultValue = "") String filter,
//                        @RequestParam(defaultValue = "") String search,
//                        @RequestParam(defaultValue = "1") int page, Model model){
//
//        var grid = service.getAllCabang(filter,search,page);
//        int total = service.getTotal(filter,search);
//        model.addAttribute("grid", grid);
//        model.addAttribute("totalPages",total);
//        model.addAttribute("currentPage",page);
//        model.addAttribute("filterItem",service.getfilterAsItem());
//        model.addAttribute("search",search);
//        return "master/pot/pot";
//    }
}
