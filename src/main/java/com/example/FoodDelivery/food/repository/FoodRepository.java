package com.example.FoodDelivery.food.repository;

import com.example.FoodDelivery.food.model.Food;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends CrudRepository<Food, Long> {
    List<Food> findAll();

    List<Food> findByIdIn(List<Long> foodIds);
}
