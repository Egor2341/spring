package com.example.spring.repository;

import com.example.spring.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.spring.entity.Order;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Modifying
    @Transactional
    @Query(
            value = "EXPLAIN ANALYZE INSERT INTO orders (customer, total)" +
                    " VALUES (:customer, :total)",
            nativeQuery = true
    )
    List<Map<String, Object>> insert(Customer customer, int total);
}
