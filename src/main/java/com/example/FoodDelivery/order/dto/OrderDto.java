package com.example.FoodDelivery.order.dto;

import java.util.List;

public record OrderDto(Long customerId, String deliveryAddress, List<Long> foodIds) {
}
