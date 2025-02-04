package com.sales.controller;

import com.sales.dto.master.JabatanFormDTO;
import com.sales.dto.master.KeteranganAplikasiFormDTO;
import com.sales.service.master.JabatanService;
import com.sales.service.master.KeteranganAplikasiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("jabatan")
public class JabatanController {
    private final JabatanService service;


    public JabatanController(JabatanService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(@RequestParam(defaultValue = "1") Integer page,@RequestParam(required = false,defaultValue = "") String filter,@RequestParam(required = false) String search, @RequestParam(required = false) String name, @RequestParam(required = false) String id, @RequestParam(required = false) Boolean status, Model model){
        var grid = service.getAll(id, name, status, page, filter, search);
        var listFilter = service.filter();
        model.addAttribute("listFilter",listFilter);
        model.addAttribute("filterSelected",filter);
        model.addAttribute("search",search);
        model.addAttribute("gridJabatan",grid);
        model.addAttribute("totalPages",service.totalPage(id, name, status));
        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("status",status);
        model.addAttribute("currentPage",page);




        return "sales/jabatan";
    }

    @GetMapping("form")
    public String form( @RequestParam(required = false) String  id, Model model){
        var dto = service.getJabatanById(id);
        model.addAttribute("dto",dto);



        return "sales/jabatan-form";
    }

    @PostMapping("form")
    public String updateInsert(@ModelAttribute("dto") JabatanFormDTO jabatanFormDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sales/jabatan-form";
        } else {
            service.updateInsert(jabatanFormDTO);
            return "redirect:/jabatan";
        }
    }

    @GetMapping("detail")
    public String detail( @RequestParam(required = false) String  id, Model model){
        var dto = service.getJabatanById(id);
        model.addAttribute("dto",dto);

        return "sales/jabatan-detail";
    }

    @GetMapping("delete")
    public String delete( @RequestParam(required = false) String id, Model model){
        service.delete(id);
        return "redirect:/jabatan";
    }


}
