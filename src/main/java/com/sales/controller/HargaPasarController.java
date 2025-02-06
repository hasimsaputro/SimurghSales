package com.sales.controller;

import com.sales.dto.hargaPasar.HargaPasarFormDTO;
import com.sales.dto.model.ModelFormDTO;
import com.sales.service.hargaPasar.HargaPasarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("form")
    public String indexForm(@RequestParam(defaultValue ="0")Integer hargaId,Model model){
        var hargaPasar = service.getHargaPasarById(hargaId);
        model.addAttribute("formHargaPasar", hargaPasar);
        return "master/hargaPasar/master-hargaPasar-form";
    }

    @PostMapping("form")
    public String indexForm(@Valid @ModelAttribute("formHargaPasar") HargaPasarFormDTO dto,
                            BindingResult validation){
        if(validation.hasErrors()){
            return "master/hargaPasar/master-hargaPasar-form";
        }else {
            service.saveHargaPasar(dto);
            return "redirect:/hargaPasar";
        }
    }

    @GetMapping("detail")
    public String detail(Model model,@RequestParam(defaultValue = "0")Integer hargaId) {
        var hargaPasar = service.getDetailHargaPasarById(hargaId);
        model.addAttribute("hargaPasar",hargaPasar);
        return "master/hargaPasar/master-hargaPasar-detail";
    }

    @GetMapping("delete")
    public String indexDelete(@RequestParam(defaultValue = "0") Integer hargaId){
        service.deleteHargaPasar(hargaId);
        return "redirect:/hargaPasar";
    }

}
