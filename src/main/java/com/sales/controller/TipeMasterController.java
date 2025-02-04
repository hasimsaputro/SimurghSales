package com.sales.controller;

import com.sales.dto.tipeMaster.TipeMasterFormDTO;
import com.sales.service.tipeMaster.TipeMasterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("tipeMaster")
public class TipeMasterController {
    private final TipeMasterService service;

    @Autowired
    public TipeMasterController(TipeMasterService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String search,
            Model model
    ){
        model.addAttribute("tipeMasterGrid", service.getAll(page, filter, search));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(filter, search));
        model.addAttribute("filter", filter);
        model.addAttribute("search", search);
        model.addAttribute("filterItem", service.getFilterAsItem());
        return "master/master-tipeMaster";
    }

    @GetMapping("detail")
    public String detail(
            @RequestParam(required = false) Integer id,
            Model model
    ){
        model.addAttribute("detailTipeMasterGrid", service.getDetailTipeMasterById(id));
        return "master/master-tipeMaster-detail";
    }

    @GetMapping("form")
    public String form(
            @RequestParam(defaultValue = "") Integer id,
            Model model
    ){
        model.addAttribute("tipeMasterByIdGrid", service.getTipeMasterById(id));
        return "master/master-tipeMaster-form";
    }

    @PostMapping("form")
    public String form(
            @Valid @ModelAttribute("tipeMaster")
                    TipeMasterFormDTO tipeMasterFormDTO,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return "master/master-tipeMaster-form";
        } else {
            service.save(tipeMasterFormDTO);
            return "redirect:/tipeMaster";
        }
    }

    @GetMapping("delete")
    public String delete(Integer id){
        service.delete(id);
        return "redirect:/tipeMaster";
    }
}
