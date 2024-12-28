package com.example.FoodDelivery.order.model;

import com.example.FoodDelivery.customer.model.Customer;
import com.example.FoodDelivery.food.model.Food;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer;

    @Column(nullable = false)
    private Instant orderDate;

    @Column(nullable = false)
    private String deliveryAddress;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Double totalPrice;

    @ManyToMany(mappedBy = "order")
    private List<Food> foodItems;

    @Version
    private Long version;
}
