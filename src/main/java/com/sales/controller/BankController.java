package com.sales.controller;

import com.sales.dto.bank.BankFormDTO;
import com.sales.service.bank.BankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("bank")
public class BankController {
    private final BankService service;

    @Autowired
    public BankController(BankService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String search,
            Model model
    ){
        model.addAttribute("bankGrid", service.getAll(page, filter, search));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(filter, search));
        model.addAttribute("filter", filter);
        model.addAttribute("search", search);
        model.addAttribute("filterItem", service.getFilterAsItem());
        return "master/master-bank";
    }

    @GetMapping("detail")
    public String detail(
            @RequestParam(required = false) Integer id,
            Model model
    ){
        model.addAttribute("detailBankGrid", service.getDetailBankById(id));
        return "master/master-bank-detail";
    }

    @GetMapping("form")
    public String form(
            @RequestParam(defaultValue = "") Integer id,
            Model  model
    ){
        model.addAttribute("bankByIdGrid", service.getBankById(id));
        return "master/master-bank-form";
    }

    @PostMapping("form")
    public String form(
            @Valid @ModelAttribute("bank")
                    BankFormDTO bankFormDTO,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return "master/master-bank-form";
        } else {
            service.save(bankFormDTO);
            return "redirect:/bank";
        }
    }

    @GetMapping("delete")
    public String delete(Integer id){
        service.delete(id);
        return "redirect:/bank";
    }
}
