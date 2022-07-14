package com.example.auditinglogging.service;

import com.example.auditinglogging.model.Customer;

import java.util.List;

public interface ICustomerService {
    Customer addNew(Customer customer);

    Customer getUserId(Integer idUser);
}
