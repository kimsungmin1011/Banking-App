package com.example.hanriver.dto;

import com.example.hanriver.model.Product.AccountType; // Product 클래스 내부의 AccountType 열거형을 import합니다.
import lombok.Data;

@Data
public class ProductCreateDTO {
    private String name;
    private String description;
    private AccountType accountType; // price 대신 AccountType 필드를 추가합니다.
    private Long userId; // 상품을 등록하는 사용자의 ID (필요에 따라 사용)
    // Lombok @Data는 기본 게터(getter) 및 세터(setter)를 자동으로 생성합니다.
}
