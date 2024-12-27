package com.example.FoodDelivery.food.controller;

import com.example.FoodDelivery.food.dto.FoodDto;
import com.example.FoodDelivery.food.model.Food;
import com.example.FoodDelivery.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodService foodService;
    private static final Logger log = Logger.getLogger(FoodController.class.getName());

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods(){
        log.info("Fetching all foods");

        return ResponseEntity.ok(foodService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id){
        log.info("Fetching food by id: " + id);

        return ResponseEntity.ok(foodService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody FoodDto foodDto){
        log.info("Creating food: " + foodDto);

        return new ResponseEntity<>(foodService.save(foodDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestBody FoodDto foodDto){
        log.info("Updating food by id: " + id);

        return ResponseEntity.ok(foodService.update(id, foodDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id){
        log.info("Deleting food by id: " + id);

        foodService.deleteById(id);

        return ResponseEntity.ok("Operation was done successfully");
    }
}
