package com.example.spring.controller;

import com.example.spring.entity.Customer;
import com.example.spring.entity.Order;
import com.example.spring.service.CustomerService;
import com.example.spring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/welcome")
    public String homePage() {
        return "Home Page";
    }

    @PostMapping("/registration")
    public List<Map<String, Object>> registration(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PostMapping("/order/new")
    public List<Map<String, Object>> newOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @GetMapping("/customers")
    public List<Map<String, Object>> allCustomers() {
        return customerService.getAll();
    }

    @GetMapping("/customers/email")
    public List<Map<String, Object>> emailCustomer(@RequestParam String email) {
        return customerService.getByEmail(email);
    }

    @GetMapping("/customers/fullname")
    public List<Map<String, Object>> fullnameCustomer(@RequestParam String firstName, @RequestParam String lastName) {
        return customerService.getByFullName(firstName, lastName);
    }

    @DeleteMapping("/customers")
    public List<Map<String, Object>> deleteCustomer(@RequestParam String email) {
        return customerService.deleteByEmail(email);
    }

    @PutMapping("/customers")
    public List<Map<String, Object>> updateCustomer(@RequestParam String firstName,
                                                    @RequestParam String lastName,
                                                    @RequestParam String email,
                                                    @RequestParam String newEmail) {
        return customerService.updateByEmail(firstName, lastName, email, newEmail);
    }

    @GetMapping("/customers/orders/all")
    public List<Map<String, Object>> getCustomersWithOrder() {
        return customerService.getEmailCustomerWithOrder();
    }

    @GetMapping("/customers/orders")
    public List<Map<String, Object>> getCustomersWithOrderMoreThan(@RequestParam int total) {
        return customerService.getCustomerEmailsWithOrderMoreThan(total);
    }

    @GetMapping("/customers/orders/avg")
    public List<Map<String, Object>> getCustomersWithOrderMoreThanAvg() {
        return customerService.getCustomerEmailsWithOrderMoreAvg();
    }
}