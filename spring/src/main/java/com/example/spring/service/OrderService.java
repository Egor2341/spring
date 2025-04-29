package com.example.spring.service;

import com.example.spring.entity.Customer;
import com.example.spring.entity.Order;
import com.example.spring.repository.CustomerRepository;
import com.example.spring.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Map<String, Object>> saveOrder (Order order) {
        return orderRepository.insert(order.getCustomer(), order.getTotal());
    }



}