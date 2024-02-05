package com.example.hanriver.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "users") // MySQL 테이블 이름과 일치
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    // 필요에 따라 추가 필드를 정의할 수 있습니다.
    // 예: 이메일, 전화번호, 활성화 상태 등

    // Lombok @Data 애너테이션은 아래와 같은 메소드를 자동으로 생성합니다:
    // - 기본 게터(getter) 및 세터(setter)
    // - equals(), hashCode()
    // - toString()
}
