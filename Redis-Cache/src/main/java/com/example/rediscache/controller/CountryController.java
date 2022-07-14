package com.example.rediscache.controller;

import com.example.rediscache.model.Customer;
import com.example.rediscache.service.ICountryService;
import com.example.rediscache.service.RedisCustomer;
import com.example.rediscache.service.RedisValueService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/customer")
public class CountryController {
    private final ICountryService countryService;
    private final RedisValueService redisValueService;
    private final RedisCustomer redisCustomer;

    @PostMapping(value = "/save")
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        return new ResponseEntity<>(countryService.save(customer), HttpStatus.OK);
    }

    @GetMapping(value = "/findById")
    public ResponseEntity<Customer> getById(@RequestParam String customerId) {
        return new ResponseEntity<>(countryService.findById(customerId), HttpStatus.OK);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Customer>> getAll() {
        return new ResponseEntity<>(countryService.findAllCustomer(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteId")
    public ResponseEntity<List<Customer>> deleteById(@RequestParam String customerId) {
        return new ResponseEntity<>(countryService.deleteAllById(customerId), HttpStatus.OK);
    }

    @GetMapping("lisCustomer/{key}")
    public Object getPerSon(@PathVariable("key") String key) {
        return redisValueService.getValue(key);
    }

}
