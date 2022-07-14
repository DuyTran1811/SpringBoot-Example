package com.example.auditinglogging.repository;

import com.example.auditinglogging.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c WHERE c.id =:idUser")
    Customer getByIds(Integer idUser);
}
