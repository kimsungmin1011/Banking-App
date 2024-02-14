package com.example.hanriver.dto;

import lombok.Data;

import java.math.BigDecimal; // BigDecimal 임포트

@Data
public class CartItemDTO {
    private Long id;
    private Long productId;
    private int quantity;
    private BigDecimal amount; // amount 필드 추가
}
