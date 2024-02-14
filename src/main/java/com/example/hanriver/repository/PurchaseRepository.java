package com.example.hanriver.repository;

import com.example.hanriver.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    // 여기에 필요한 추가적인 쿼리 메소드를 정의할 수 있습니다.
    // 예를 들어, 특정 사용자의 모든 구매 내역을 조회하는 메소드 등을 추가할 수 있습니다.
}
