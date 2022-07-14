package com.example.rediscache.service;

import com.example.rediscache.model.Customer;
import com.example.rediscache.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CustomerService implements ICountryService {
    private final RedisCustomer redisCustomer;
    private final RedisValueService redisValueService;
    private final CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(String customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    @Override
    public List<Customer> findAllCustomer() {
        List<Customer> customerList = customerRepository.findAllCountry();
        redisValueService.pushCache("customer", customerList);
        return customerList;
    }

    @Override
    public List<Customer> deleteAllById(String customerId) {
        customerRepository.deleteById(customerId);
        return customerRepository.findAll();
    }

}
