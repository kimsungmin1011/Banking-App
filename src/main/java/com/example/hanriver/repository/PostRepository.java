package com.example.hanriver.repository;

import com.example.hanriver.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본적인 CRUD 메서드들은 JpaRepository에 의해 제공됩니다.

    // 필요에 따라 추가적인 쿼리 메서드를 정의할 수 있습니다.
    // 예: 사용자 ID별로 게시글 찾기
    // List<Post> findByUserId(Long userId);
}
