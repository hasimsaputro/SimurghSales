package com.sales.controller;

import com.sales.service.hargaPasar.HargaPasarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("hargaPasar")
public class HargaPasarController {
    private final HargaPasarService service;

    @Autowired
    public HargaPasarController(HargaPasarService service) {
        this.service = service;
    }
    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String search,
            Model model
    ){
        model.addAttribute("hargaPasarGrid", service.getAll(page, filter, search));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(filter, search));
        model.addAttribute("filter", filter);
        model.addAttribute("search", search);
        model.addAttribute("filterItem", service.getFilterAsItem());
        return "master/hargaPasar/master-hargaPasar";
    }

}
