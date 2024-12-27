package com.example.FoodDelivery.food.service.impl;

import com.example.FoodDelivery.food.dto.FoodDto;
import com.example.FoodDelivery.food.model.Food;
import com.example.FoodDelivery.food.repository.FoodRepository;
import com.example.FoodDelivery.food.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private static final Logger log = LoggerFactory.getLogger(FoodServiceImpl.class);

    @Autowired
    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public List<Food> getAll() {
        log.info("Trying to get all foods");

        return foodRepository.findAll();
    }

    @Override
    public Food getById(Long id) {
        log.info("Trying to get food data by id: {}", id);

        validateId(id);

        Food food = foodRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Food not found with following id: " + id));

        log.info("Successfully founded food by id: {}", id);
        return food;
    }

    @Override
    @Transactional
    public Food save(FoodDto foodDto) {
        log.info("Trying to save food: {}", foodDto);

        validateFoodDto(foodDto);

        Food food = Food
                .builder()
                .name(foodDto.name())
                .foodType(foodDto.foodType())
                .description(foodDto.description())
                .price(foodDto.price())
                .available(foodDto.available())
                .build();

        foodRepository.save(food);
        log.info("Saved food with id: {}", food.getId());

        return food;
    }

    @Override
    @Transactional
    public Food update(Long id, FoodDto foodDto) {
        log.info("Trying to update food: {}", foodDto);

        validateId(id);
        validateFoodDto(foodDto);

        Food food = foodRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Food not found with following id: " + id));

        food.setName(foodDto.name());
        food.setFoodType(foodDto.foodType());
        food.setDescription(foodDto.description());
        food.setPrice(foodDto.price());
        food.setAvailable(foodDto.available());

        foodRepository.save(food);
        log.info("Updated food with id {}: {}", food.getId(), food);

        return food;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Trying to delete food: {}", id);

        validateId(id);

        Food food = foodRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Food not found with following id: " + id));

        foodRepository.delete(food);
        log.info("Deleted food with id {}: {}", id, food);
    }

    private void validateFoodDto(FoodDto foodDto) {
        if(foodDto == null){
            log.error("Invalid foodDto provided: {}", foodDto);
            throw new IllegalArgumentException("FoodDto must not be null");
        }
    }

    private void validateId(Long id) {
        if(id == null || id <= 0){
            log.error("Invalid id: {}, provided", id);
            throw new IllegalArgumentException("Id must be a positive, not null value");
        }
    }
}
