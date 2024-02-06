package com.example.hanriver.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.math.BigDecimal; // 잔고를 위한 BigDecimal 타입을 사용

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

    // 추가된 필드
    @Column(nullable = false, unique = true, length = 11)
    private String accountNumber; // 계좌 번호, 11자리로 고유해야 함

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.valueOf(1000000); // 초기 잔고 100만원

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Post> posts; // User와 Post 사이의 1:N 관계

    // Lombok @Data 애너테이션은 아래와 같은 메소드를 자동으로 생성합니다:
    // - 기본 게터(getter) 및 세터(setter)
    // - equals(), hashCode()
    // - toString()
}
