package com.example.hanriver.controller;

import com.example.hanriver.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/transfer") // 이 컨트롤러의 모든 엔드포인트는 "/transfer" 경로로 시작
public class TransferController {

    @Autowired
    private TransferService transferService;

    /**
     * 사용자가 다른 사용자에게 돈을 전송할 때 호출됩니다.
     * @param payload 클라이언트로부터 받은 요청 데이터. 송금자 계좌번호, 수신자 계좌번호, 전송 금액 포함
     * @return 전송 성공 여부에 따라 상태 코드와 메시지를 포함하는 ResponseEntity 객체 반환
     */
    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody Map<String, Object> payload) {
        String fromAccountNumber = (String) payload.get("fromAccountNumber"); // 송금자 계좌번호
        String toAccountNumber = (String) payload.get("toAccountNumber"); // 수신자 계좌번호
        BigDecimal amount = new BigDecimal((String) payload.get("amount")); // 전송할 금액

        boolean success = transferService.transferAmount(fromAccountNumber, toAccountNumber, amount);

        if (success) {
            return ResponseEntity.ok().body("Transfer successful");
        } else {
            return ResponseEntity.badRequest().body("Transfer failed");
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestParam String accountNumber) {
        BigDecimal balance = transferService.findBalanceByAccountNumber(accountNumber);
        if (balance != null) {
            return ResponseEntity.ok().body(Map.of("accountNumber", accountNumber, "balance", balance));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
