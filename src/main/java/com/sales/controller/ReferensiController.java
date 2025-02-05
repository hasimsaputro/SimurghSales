package com.sales.controller;

import com.sales.dto.referensi.ReferensiFormDTO;
import com.sales.service.referensi.ReferensiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("referensi")
public class ReferensiController {
    private final ReferensiService service;


    public ReferensiController(ReferensiService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(@RequestParam(defaultValue = "1") Integer page,@RequestParam(required = false,defaultValue = "") String filter,@RequestParam(required = false) String search, @RequestParam(required = false) String name, @RequestParam(required = false) String id, @RequestParam(required = false) Boolean status, Model model){
        var grid = service.getAll(id, name, status, page, filter, search);
        var listFilter = service.filter();
        var gridDebitur = service.getAllDebitur();
        model.addAttribute("listFilter",listFilter);
        model.addAttribute("gridDebitur",gridDebitur);
        model.addAttribute("filterSelected",filter);
        model.addAttribute("search",search);
        model.addAttribute("gridReferensi",grid);
        model.addAttribute("totalPage",service.totalPage(id, name, status));
        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("status",status);




        return "userManagement/referensi/referensi";
    }

    @GetMapping("form")
    public String form( @RequestParam(required = false) String id, Model model){
        var referensiDto = service.getReferensiById(id);
        var gridDebitur = service.getAllDebitur();
        model.addAttribute("referensiDto",referensiDto);
        model.addAttribute("gridDebitur",gridDebitur);


        return "userManagement/referensi/referensi-form";
    }

    @PostMapping("form")
    public String updateInsert(@ModelAttribute("referensiDto") ReferensiFormDTO referensiDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "userManagement/referensi/referensi-form";
        } else {
            service.updateInsert(referensiDto);
            return "redirect:/referensi";
        }
    }

    @GetMapping("detail")
    public String detail( @RequestParam(required = false) String id, Model model){
        var referensiDto = service.getReferensiById(id);
        model.addAttribute("referensiDto",referensiDto);

        return "userManagement/referensi/referensi-detail";
    }

    @GetMapping("delete")
    public String delete( @RequestParam(required = false) String id, Model model){
        service.delete(id);
        return "redirect:/referensi";
    }


}
