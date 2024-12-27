package com.example.FoodDelivery.food.service;

import com.example.FoodDelivery.food.dto.FoodDto;
import com.example.FoodDelivery.food.model.Food;

import java.util.List;

public interface FoodService {
    List<Food> getAll();

    Food getById(Long id);

    Food save(FoodDto foodDto);

    Food update(Long id, FoodDto foodDto);

    void deleteById(Long id);
}
