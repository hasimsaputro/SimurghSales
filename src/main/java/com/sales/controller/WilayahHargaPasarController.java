package com.sales.controller;

import com.sales.dto.wilayahHargaPasar.CabangWilayahDTO;
import com.sales.service.wilayahHargaPasar.WilayahHargaPasarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("wilayahHargaPasar")
public class WilayahHargaPasarController {
    private final WilayahHargaPasarService service;

    @Autowired
    public WilayahHargaPasarController(WilayahHargaPasarService service) {
        this.service = service;
    }

    @GetMapping("")
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String search,
            Model model
    ){
        model.addAttribute("wilayahHargaPasarGrid", service.getAllWilayahHargaPasar(page, filter, search));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(filter, search));
        model.addAttribute("filter", filter);
        model.addAttribute("search", search);
        model.addAttribute("filterItem", service.getFilterAsItem());

        return "master/wilayahHargaPasar/master-wilayahHargaPasar";
    }

    @GetMapping("form")
    public String indexForm(@RequestParam(defaultValue ="")String wilayahId,Model model){
        var wilayah = service.getWilayahById(wilayahId);
        model.addAttribute("formWilayah", wilayah);
        return "master/wilayahHargaPasar/wilayah-harga-pasar-form";
    }
    @GetMapping("detail")
    public String detail(Model model,@RequestParam(defaultValue = "")String wilayahId) {
        List<CabangWilayahDTO> cabangWilayah = service.getCabangByWilayahId(wilayahId);
        cabangWilayah.sort(Comparator.comparing(CabangWilayahDTO::getNamaCabang));
        model.addAttribute("wilayahHargaPasar", service.getWilayahDetailById(wilayahId));
        model.addAttribute("detailCabangByWilayah",cabangWilayah);
        return "master/wilayahHargaPasar/wilayah-harga-pasar-detail";
    }


    @GetMapping("delete")
    public String indexDelete(@RequestParam(defaultValue = "") String wilayahId){
        service.deleteWilayah(wilayahId);
        return "redirect://wilayahHargaPasar";
    }
}
