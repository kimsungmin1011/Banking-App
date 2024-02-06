package com.example.hanriver.repository;

import com.example.hanriver.model.User; // User 클래스의 패키지를 확인하세요
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 사용자명을 통해 사용자 찾기
    Optional<User> findByUsername(String username);
    // 계좌 번호가 이미 존재하는지 확인하는 메소드
    boolean existsByAccountNumber(String accountNumber);
    // 계좌번호 조회
    Optional<User> findByAccountNumber(String accountNumber);
}
