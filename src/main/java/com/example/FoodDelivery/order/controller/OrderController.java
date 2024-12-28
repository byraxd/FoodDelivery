package com.example.FoodDelivery.order.controller;

import com.example.FoodDelivery.order.dto.OrderDto;
import com.example.FoodDelivery.order.model.Order;
import com.example.FoodDelivery.order.model.Status;
import com.example.FoodDelivery.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(orderService.getAllByCustomerId(customerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(orderService.create(orderDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.updateById(id, orderDto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable("id") Long id, @RequestParam("status") Status status) {
        return ResponseEntity.ok(orderService.updateStatusById(id, status));
    }
}
