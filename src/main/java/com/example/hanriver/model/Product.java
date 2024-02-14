package com.example.hanriver.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    public enum AccountType {
        DEPOSIT, SAVINGS, CHECKING, HOUSING_SUBSCRIPTION
    }

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    // Getters and Setters
}
