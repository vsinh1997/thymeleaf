package com.example.thymeleaf.service;

import com.example.thymeleaf.model.Food;
import org.springframework.stereotype.Service;

@Service
public class FoodService {
    public Food[] getFoods() {
        return new Food[]{
                new Food("burrito", 7.2, 100),
                new Food("pizza", 12.0, 50),
                new Food("soda", 3.1, 99)
        };
    }
}
