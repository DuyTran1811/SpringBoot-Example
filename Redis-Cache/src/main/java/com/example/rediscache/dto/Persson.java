package com.example.rediscache.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Persson implements Serializable {
    private String id;
    private String name;
    private int age;
}
