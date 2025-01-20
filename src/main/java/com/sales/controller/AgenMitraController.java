package com.sales.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("agenmitra")
public class AgenMitraController {
    @GetMapping("")
    public String index(Model model){
        return "sales/sales-agen";
    }

    @GetMapping("form")
    public String form(Model model) { return "sales/sales-agen-form";}

    @GetMapping("detail")
    public String detail(Model model) { return "sales/sales-agen-detail";}
}
