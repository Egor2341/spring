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
            value = "EXPLAIN ANALYZE SELECT * FROM customers",
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



    // SELECT u.email FROM User u WHERE EXISTS (SELECT 1 FROM Order o WHERE o.user = u)
    @Transactional
    @Query(
            value = "EXPLAIN ANALYZE SELECT c.email, o.total FROM customers c JOIN orders o ON c.id = o.customer",
            nativeQuery = true
    )
    List<Map<String, Object>> selectEmailCustomerWithOrder();


    //SELECT DISTINCT c.name
    //FROM customers c
    //INNER JOIN orders o ON c.id = o.customer_id
    //WHERE o.total > 1000;
    @Transactional
    @Query(value = "EXPLAIN ANALYZE SELECT email " +
            "FROM customers " +
            "WHERE id IN (SELECT customer FROM orders WHERE total > :total)",
            nativeQuery = true
    )
    List<Map<String, Object>> selectUserEmailsWithOrderMoreThan(int total);


    //WITH avg_total AS (
    //    SELECT AVG(total) AS avg_total
    //    FROM orders
    //)
    //SELECT customer_id
    //FROM orders, avg_total
    //WHERE total > avg_total.avg_total;
    @Transactional
    @Query(value = "EXPLAIN ANALYZE SELECT customer " +
            "FROM orders " +
            "WHERE total > (SELECT AVG(total) FROM orders)",
        nativeQuery = true
    )
    List<Map<String, Object>> selectUserEmailsWithOrderMoreAvg();


}
