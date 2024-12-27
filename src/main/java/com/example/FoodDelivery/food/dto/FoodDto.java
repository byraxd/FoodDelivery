package com.example.FoodDelivery.food.dto;

import com.example.FoodDelivery.food.model.FoodType;

public record FoodDto(String name, FoodType foodType, String description, Double price, Boolean available) {

}
