package com.example.spring.repository;

import com.example.spring.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Modifying
    @Transactional
    @Query(
            value = "EXPLAIN ANALYZE INSERT INTO customers (first_name, last_name, email)" +
                    " VALUES (:firstName, :lastName, :email)",
            nativeQuery = true
    )
    List<Map<String, Object>> insert(String firstName, String lastName, String email);

    @Transactional
    @Query(
            value = "EXPLAIN ANALYZE SELECT first_name, last_name, email " +
                    "FROM customers " +
                    "ORDER BY email " +
                    "LIMIT 10;",
            nativeQuery = true
    )
    List<Map<String, Object>> selectAll();

    @Transactional
    @Query(
            value = "EXPLAIN ANALYZE SELECT * FROM customers WHERE email = :email",
            nativeQuery = true
    )
    List<Map<String, Object>> selectByEmail(String email);

    @Transactional
    @Query(
            value = "EXPLAIN ANALYZE SELECT * FROM customers WHERE first_name = :firstName AND last_name = :lastName",
            nativeQuery = true
    )
    List<Map<String, Object>> selectByFullName(String firstName, String lastName);

    @Modifying
    @Transactional
    @Query(
            value = "EXPLAIN ANALYZE DELETE FROM customers WHERE email = :email",
            nativeQuery = true
    )
    List<Map<String, Object>> deleteByEmail(String email);

    @Modifying
    @Transactional
    @Query(
            value = "EXPLAIN ANALYZE UPDATE customers " +
                    "SET first_name = :firstName, last_name = :lastName, email = :newEmail " +
                    "WHERE email = :email",
            nativeQuery = true
    )
    List<Map<String, Object>> updateByEmail(String firstName, String lastName, String email, String newEmail);



    @Transactional
    @Query(
            value = "EXPLAIN ANALYZE " +
                    "SELECT c.email " +
                    "FROM customers c " +
                    "WHERE EXISTS (SELECT 1 FROM orders o WHERE o.customer = c.id)",
            nativeQuery = true
    )
    List<Map<String, Object>> selectEmailCustomerWithOrder();


    @Transactional
    @Query(value = "EXPLAIN ANALYZE SELECT DISTINCT c.email " +
            "FROM customers c " +
            "INNER JOIN orders o ON c.id = o.customer " +
            "WHERE o.total > :total;",
            nativeQuery = true
    )
    List<Map<String, Object>> selectUserEmailsWithOrderMoreThan(int total);


    @Transactional
    @Query(value = "EXPLAIN ANALYZE WITH avg_total AS (SELECT AVG(total) AS avg_total FROM orders) " +
            "SELECT customer " +
            "FROM orders, avg_total " +
            "WHERE total > avg_total.avg_total",
        nativeQuery = true
    )
    List<Map<String, Object>> selectUserEmailsWithOrderMoreAvg();


}
