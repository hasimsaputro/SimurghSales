package com.sales.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("DataLeads")
public class DataLeadsController {
    @GetMapping("")
    public String index(Model model){
        return "sales/data-leads";
    }

    @GetMapping("insert")
    public String insert(Model model) { return "sales/data-leads-form";}
    @GetMapping("detail")
    public String detail(Model model) { return "sales/data-leads-detail";}
}
