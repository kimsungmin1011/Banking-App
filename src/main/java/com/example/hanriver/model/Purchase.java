package com.example.hanriver.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user; // 구매한 사용자

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product; // 구매한 상품

    private BigDecimal amount; // 사용자가 입금한 금액

}
