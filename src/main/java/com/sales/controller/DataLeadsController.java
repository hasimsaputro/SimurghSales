package com.sales.controller;

import com.sales.dto.dataLeads.DataLeadsFormDTO;
import com.sales.service.dataLeads.DataLeadsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("dataleads")
public class DataLeadsController {
    private final DataLeadsService service;

    @Autowired
    public DataLeadsController(DataLeadsService service) {
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
        return "sales/data-leads";
    }

    @GetMapping("form")
    public String form(Model model,@RequestParam(defaultValue = "")String dataLeadsId) {
        var dataLeadsById = service.getDataLeadsById(dataLeadsId);
        model.addAttribute("dataLeadsById",dataLeadsById);
        return "sales/data-leads-form";
    }

    @PostMapping("form")
    public String form(@Valid @ModelAttribute("dataLeadsById") DataLeadsFormDTO dataLeadsFormDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "sales/data-leads-form";
        }else {
            service.updateInsertDataLeads(dataLeadsFormDTO);
            return "redirect:/dataleads";
        }

    }

    @GetMapping("detail")
    public String detail(Model model,@RequestParam(defaultValue = "")String dataLeadsId) {
        var dataLeadsById = service.getDataLeadsByIdDetail(dataLeadsId);
        model.addAttribute("dataLeadsById",dataLeadsById);
        return "sales/data-leads-detailtest";
    }
}
