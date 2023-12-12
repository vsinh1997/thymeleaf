package com.example.thymeleaf.controller;

import com.example.thymeleaf.entity.Food;
import com.example.thymeleaf.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/foods")
@AllArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/home")
    public String getAllFoods(Model model) {
        List<Food> foods = foodService.findAll();
        if (foods.isEmpty()) {
            model.addAttribute("message", "List of foods is empty");
        }
        model.addAttribute("foods", foods);
        return "food/home";
    }

    @GetMapping("/add")
    public String addFoodForm(Model model) {
        model.addAttribute("food", new Food());
        return "food/add";
    }
    @PostMapping("/add")
    public String addFood(@ModelAttribute Food food, Model model) {
       try {
           foodService.addFood(food);
           model.addAttribute("message", "Successfully");
           return "redirect:/foods/home";
       }
       catch ( Exception e) {
           model.addAttribute("error", e.getMessage());
           return "food/add";
       }
    }


}
