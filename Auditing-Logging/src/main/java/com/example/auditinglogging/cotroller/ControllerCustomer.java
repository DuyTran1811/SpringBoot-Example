package com.example.auditinglogging.cotroller;

import com.example.auditinglogging.model.Customer;
import com.example.auditinglogging.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class ControllerCustomer {
    private final CustomerService customerService;

    public ControllerCustomer(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/add/user")
    public ResponseEntity<Customer> addUser(@RequestBody Customer user) {
        return new ResponseEntity<>(customerService.addNew(user), HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<Customer> getUserByUserId(@PathVariable(value = "userId") int userId) {
        return new ResponseEntity<>(customerService.getUserId(userId), HttpStatus.OK);
    }
}
