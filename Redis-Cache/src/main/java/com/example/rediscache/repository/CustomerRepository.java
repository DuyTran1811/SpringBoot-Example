package com.example.rediscache.repository;

import com.example.rediscache.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query("SELECT c FROM Customer c WHERE c.customerId = :customerId")
    Optional<Customer> findById(String customerId);

    @Query("SELECT c FROM Customer c")
    List<Customer> findAllCountry();
}
