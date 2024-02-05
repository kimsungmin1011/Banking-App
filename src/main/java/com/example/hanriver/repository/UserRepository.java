package com.example.hanriver.repository;

import com.example.hanriver.model.User; // User 클래스의 패키지를 확인하세요
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 사용자명을 통해 사용자 찾기
    Optional<User> findByUsername(String username);

    // 필요에 따라 여기에 추가적인 사용자 관련 메서드를 정의할 수 있습니다.
}
