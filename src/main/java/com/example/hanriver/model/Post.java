package com.example.hanriver.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Data // Lombok 어노테이션
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user; // 게시글 작성자

    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 시간

    // Lombok @Data는 기본 생성자와 getter, setter, toString, equals, hashCode 메서드를 자동으로 생성해줍니다.
}