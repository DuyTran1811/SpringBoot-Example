package com.example.rediscache.model;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "customers", schema = "test-redis")
public class Customer implements Serializable {
    @Id @Column(name = "customer_id")
    private String customerId;
    @Column(name = "address_line1", length = 100)
    private String addressLine1;
    @Column(name = "address_line2")
    private String addressLine2;
    @Column(name = "contact_first_name")
    private String contactFirstName;
    @Column(name = "contact_last_name")
    private String contactLastName;
    @Column(name = "credit_limit")
    private String creditLimit;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "sales_rep_employee_number")
    private String salesRepEmployeeNumber;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "phone")
    private String phone;
    @Column(name = "state")
    private String state;
}
