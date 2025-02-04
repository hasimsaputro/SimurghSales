package com.sales.controller;

import com.sales.dto.model.ModelFormDTO;
import com.sales.service.model.ModelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("model")
public class ModelController {
    private final ModelService service;

    @Autowired
    public ModelController(ModelService service) {
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
        return "master/model/model";
    }
    @GetMapping("form")
    public String indexForm(@RequestParam(defaultValue ="")String modelId,Model model){
        var models = service.getModelById(modelId);
        model.addAttribute("formModel", models);
        return "master/model/model-form";
    }

    @PostMapping("form")
    public String indexForm(@Valid @ModelAttribute("formModel") ModelFormDTO dto,
                            BindingResult validation){
        if(validation.hasErrors()){
            return "master/model/model-form";
        }else {
            service.saveModel(dto);
            return "redirect:/model";
        }
    }

    @GetMapping("detail")
    public String detail(Model model,@RequestParam(defaultValue = "")String modelId) {
        var models = service.getModelByIdDetail(modelId);
        model.addAttribute("model",models);
        return "master/model/model-detail";
    }
    @GetMapping("delete")
    public String indexDelete(@RequestParam(defaultValue = "") String modelId){
        service.deleteModel(modelId);
        return "redirect:/model";
    }
}
