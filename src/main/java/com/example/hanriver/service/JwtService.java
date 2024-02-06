package com.example.hanriver.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;

@Service
public class JwtService {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512); // HS512에 적합한 강력한 키 생성

    private final long EXPIRATION_TIME = 86400000; // 토큰 만료 시간 (24시간)

    public String createToken(Long id, String username, String accountNumber, BigDecimal balance) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username) // 사용자 이름 설정
                .setId(id.toString()) // 사용자 ID 추가
                .claim("accountNumber", accountNumber) // 계좌 번호 클레임 추가
                .claim("balance", balance) // 잔고 클레임 추가
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    // 추가적인 메소드
}
