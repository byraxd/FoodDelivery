package com.example.FoodDelivery.order.repository;

import com.example.FoodDelivery.order.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAll();

    List<Order> findAllByCustomerId(Long customerId);
}
