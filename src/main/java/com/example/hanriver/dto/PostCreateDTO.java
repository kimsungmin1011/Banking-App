package com.example.hanriver.dto;

import lombok.Data;

@Data
public class PostCreateDTO {
    private String content;
    private Long userId;
    private String username;

    // Lombok @Data는 기본 게터(getter) 및 세터(setter)를 자동으로 생성합니다.
}
