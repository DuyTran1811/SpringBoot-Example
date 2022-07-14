package com.example.rediscache.service;

import com.example.rediscache.model.Customer;

import java.util.List;

public interface ICountryService {
    Customer save(Customer customer);

    Customer findById(String customerId);

    List<Customer> findAllCustomer();

    List<Customer> deleteAllById(String customerId);

//    List<CountryEntity> delete(Integer id);
}
