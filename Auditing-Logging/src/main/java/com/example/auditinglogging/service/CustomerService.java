package com.example.auditinglogging.service;

import com.example.auditinglogging.model.Customer;
import com.example.auditinglogging.repository.ICustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {
    private final ICustomerRepository repository;

    public CustomerService(ICustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer addNew(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Customer getUserId(Integer idUser) {
        return repository.getByIds(idUser);
    }
}
