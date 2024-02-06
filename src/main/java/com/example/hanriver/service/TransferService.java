package com.example.hanriver.service;

import com.example.hanriver.model.User;
import com.example.hanriver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 사용자 간의 금액을 전송합니다.
     * @param fromAccountNumber 송금자의 계좌번호
     * @param toAccountNumber 수신자의 계좌번호
     * @param amount 전송할 금액
     * @return 전송 성공 여부. 송금자가 존재하지 않거나, 수신자가 존재하지 않거나, 송금액이 송금자의 잔고를 초과하는 경우 false 반환
     */
    @Transactional // 이 메소드를 하나의 트랜잭션으로 처리
    public boolean transferAmount(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        User fromUser = userRepository.findByAccountNumber(fromAccountNumber).orElse(null);
        User toUser = userRepository.findByAccountNumber(toAccountNumber).orElse(null);

        // 검증 로직: 송금자 또는 수신자가 존재하지 않거나, 송금액이 송금자의 잔고보다 많은 경우
        if (fromUser == null || toUser == null || fromUser.getBalance().compareTo(amount) < 0) {
            return false;
        }

        // 잔고 업데이트: 송금자의 잔고에서 금액 차감, 수신자의 잔고에 금액 추가
        fromUser.setBalance(fromUser.getBalance().subtract(amount));
        toUser.setBalance(toUser.getBalance().add(amount));

        // 변경된 사용자 정보 저장
        userRepository.save(fromUser);
        userRepository.save(toUser);

        return true;
    }

    // UserService.java 내에 추가
    public BigDecimal findBalanceByAccountNumber(String accountNumber) {
        User user = userRepository.findByAccountNumber(accountNumber).orElse(null);
        return user != null ? user.getBalance() : BigDecimal.ZERO;
    }
}
