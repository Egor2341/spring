package com.example.spring.service;

import com.example.spring.entity.Customer;
import com.example.spring.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Map<String, Object>> saveCustomer(Customer customer) {
        return customerRepository.insert(customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }

    public List<Map<String, Object>> getAll() {
        return customerRepository.selectAll();
    }

    public List<Map<String, Object>> getByEmail(String email) {
        return customerRepository.selectByEmail(email);
    }

    public List<Map<String, Object>> getByFullName(String firstName, String lastName) {
        return customerRepository.selectByFullName(firstName, lastName);
    }

    public List<Map<String, Object>> deleteByEmail(String email) {
        return customerRepository.deleteByEmail(email);
    }

    public List<Map<String, Object>> updateByEmail(String firstName, String lastName, String email, String newEmail) {
        return customerRepository.updateByEmail(firstName, lastName, email, newEmail);
    }

    public List<Map<String, Object>> getEmailCustomerWithOrder() {
        return customerRepository.selectEmailCustomerWithOrder();
    }

    public List<Map<String, Object>> getCustomerEmailsWithOrderMoreThan(int total) {
        return customerRepository.selectUserEmailsWithOrderMoreThan(total);
    }

    public List<Map<String, Object>> getCustomerEmailsWithOrderMoreAvg() {
        return customerRepository.selectUserEmailsWithOrderMoreAvg();
    }

}