package com.fashion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FashionController {

    private final FashionService fashionService;

    public FashionController(FashionService fashionService) {
        this.fashionService = fashionService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("fashions", fashionService.viewAllFittings());
        return "index";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("fashion", new Fashion());
        return "add";
    }

    @PostMapping("/add")
    public String addSubmit(@ModelAttribute Fashion fashion) {
        fashionService.saveFitting(fashion);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        fashionService.deleteFittingById(id);
        return "redirect:/";
    }
}
