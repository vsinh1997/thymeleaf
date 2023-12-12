package com.example.thymeleaf.service;

import com.example.thymeleaf.entity.Food;
import com.example.thymeleaf.repository.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    public List<Food> findAll() {
       return foodRepository.findAll();
    }

    public Food addFood(Food food) {
        Food newFood = Food.builder()
                .name(food.getName())
                .price(food.getPrice())
                .qty(food.getQty())
                .build();
        foodRepository.save(newFood);
        return newFood;
    }
}
