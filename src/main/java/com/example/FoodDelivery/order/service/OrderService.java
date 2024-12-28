package com.example.FoodDelivery.order.service;

import com.example.FoodDelivery.order.dto.OrderDto;
import com.example.FoodDelivery.order.model.Order;
import com.example.FoodDelivery.order.model.Status;

import java.util.List;

public interface OrderService {
    Order create(OrderDto orderDto);

    Order updateById(Long id, OrderDto orderDto);

    Order getById(Long id);

    List<Order> getAllByCustomerId(Long customerId);

    Order updateStatusById(Long id, Status status);
}
