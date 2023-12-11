package com.example.thymeleaf.controller;

import com.example.thymeleaf.model.Food;
import com.example.thymeleaf.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/food")
@AllArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/home")
    public String home(Model model) {
        Food[] foods = foodService.getFoods();
        model.addAttribute("foods", foods);
        return "home";
    }
}
