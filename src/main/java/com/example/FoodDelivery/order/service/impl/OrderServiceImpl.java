package com.example.FoodDelivery.order.service.impl;

import com.example.FoodDelivery.customer.model.Customer;
import com.example.FoodDelivery.customer.repository.CustomerRepository;
import com.example.FoodDelivery.food.model.Food;
import com.example.FoodDelivery.food.repository.FoodRepository;
import com.example.FoodDelivery.order.dto.OrderDto;
import com.example.FoodDelivery.order.model.Order;
import com.example.FoodDelivery.order.model.Status;
import com.example.FoodDelivery.order.repository.OrderRepository;
import com.example.FoodDelivery.order.service.OrderService;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final FoodRepository foodRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, FoodRepository foodRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.foodRepository = foodRepository;
    }

    @Override
    @Transactional
    public Order create(OrderDto orderDto) {
        log.info("Trying to create order");
        validateOrderDto(orderDto);

        Customer customer = customerRepository.findById(orderDto.customerId()).orElseThrow(
                () -> new NoSuchElementException("Customer not found")
        );

        List<Food> foodItems = foodRepository.findByIdIn(orderDto.foodIds());

        Double price = foodItems.stream().mapToDouble(Food::getPrice).sum();

        Order order = Order
                .builder()
                .customer(customer)
                .orderDate(Instant.now())
                .deliveryAddress(orderDto.deliveryAddress())
                .status(Status.PLACED)
                .totalPrice(price)
                .foodItems(foodItems)
                .build();

        orderRepository.save(order);
        log.info("Order was successfully created: {}", order);

        return order;
    }

    @Override
    @Transactional
    public Order updateById(Long id, OrderDto orderDto) {
        log.info("Trying to update order by id: {}", id);

        validateId(id);
        validateOrderDto(orderDto);
        try {
            Order order = orderRepository.findById(id).orElseThrow(
                    () -> new NoSuchElementException("Order not found")
            );

            Customer customer = customerRepository.findById(orderDto.customerId()).orElseThrow(
                    () -> new NoSuchElementException("Customer not found")
            );

            List<Food> foodItems = foodRepository.findByIdIn(orderDto.foodIds());

            order.setCustomer(customer);
            order.setDeliveryAddress(orderDto.deliveryAddress());
            order.setFoodItems(foodItems);

            orderRepository.save(order);
            log.info("Order: {}, was successfully updated by id: {}", order, id);

            return order;
        }catch (OptimisticLockException e) {
            throw new OptimisticLockException("Another user updated this record already", e);
        }
    }

    @Override
    public Order getById(Long id) {
        log.info("Trying to get order by id: {}", id);

        validateId(id);

        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Order not found")
        );

        log.info("Order: {}, was successfully found by id: {}", order, id);

        return order;
    }

    @Override
    public List<Order> getAllByCustomerId(Long customerId) {
        log.info("Trying to get orders by customer id: {}", customerId);

        List<Order> orders = orderRepository.findAllByCustomerId(customerId);

        log.info("Successfully founded all orders: {}", orders);

        return orders;
    }

    @Override
    public Order updateStatusById(Long id, Status status) {
        log.info("Trying to update order status by id: {}", id);

        validateId(id);

        try {
            Order order = orderRepository.findById(id).orElseThrow(
                    () -> new NoSuchElementException("Order not found")
            );

            order.setStatus(status);

            orderRepository.save(order);
            log.info("Order: {}, status was successfully updated by id: {}", order, id);

            return order;
        }catch (OptimisticLockException e){
            throw new OptimisticLockException("Another user updated this record already", e);
        }
    }

    private void validateOrderDto(OrderDto orderDto) {
        if (orderDto == null) {
            log.error("Invalid orderDTO provided: {}", orderDto);
            throw new IllegalArgumentException("Order DTO cannot be null");
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            log.error("Invalid id provided: {}", id);
            throw new IllegalArgumentException("Order ID cannot be null or negative");
        }
    }
}
