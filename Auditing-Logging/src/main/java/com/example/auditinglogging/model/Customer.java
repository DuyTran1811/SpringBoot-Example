package com.example.auditinglogging.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;

import static com.example.auditinglogging.model.Customer.TABLE_NAME;
@Data
@Entity
@Table(name = TABLE_NAME)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor @NoArgsConstructor
public class Customer extends Auditable<String> implements Serializable {
    protected static final String TABLE_NAME = "customer";
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fullName;
    private String gender;
    private String address;
    private String email;
    private byte age;
}
