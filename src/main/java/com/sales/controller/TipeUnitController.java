package com.sales.controller;

import com.sales.dto.tipeAplikasi.TipeAplikasiDTO;
import com.sales.dto.tipeUnit.TipeUnitFormDTO;
import com.sales.service.tipeUnit.TipeUnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("tipeUnit")
public class TipeUnitController {
    private final TipeUnitService service;


    public TipeUnitController(TipeUnitService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(@RequestParam(defaultValue = "1") Integer page,@RequestParam(required = false,defaultValue = "") String filter,@RequestParam(required = false) String search, @RequestParam(required = false) String name, @RequestParam(required = false) String id, @RequestParam(required = false) Boolean status, Model model){
        var grid = service.getAll(id, name, status, page, filter, search);
        var total = service.totalPage(id, name, status,  filter,  search);
        var listFilter = service.filter();
        model.addAttribute("listFilter",listFilter);
        model.addAttribute("filterSelected",filter);
        model.addAttribute("search",search);
        model.addAttribute("gridTipeUnit",grid);
        model.addAttribute("totalPages",total);
        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("status",status);
        model.addAttribute("currentPage",page);




        return "master/tipeUnit/tipeUnit";
    }

    @GetMapping("form")
    public String form( @RequestParam(required = false) String id, Model model){
        var tipeUnitDto = service.getUnitById(id);
        model.addAttribute("tipeUnitDto",tipeUnitDto);



        return "master/tipeUnit/tipeUnit-form";
    }

    @PostMapping("form")
    public String updateInsert(@ModelAttribute("tipeUnitDto") TipeUnitFormDTO tipeUnitFormDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "master/tipeUnit/tipeUnit-form";
        } else {
            service.updateInsert(tipeUnitFormDTO);
            return "redirect:/tipeUnit";
        }
    }

    @GetMapping("detail")
    public String detail( @RequestParam(required = false) String id, Model model){
        var tipeUnitDto = service.getUnitById(id);
        model.addAttribute("tipeUnitDto",tipeUnitDto);

        return "master/tipeUnit/tipeUnit-detail";
    }

    @GetMapping("delete")
    public String delete( @RequestParam(required = false) String id, Model model){
        service.delete(id);
        return "redirect:/tipeUnit";
    }


}
