package com.example.hanriver.service;

import com.example.hanriver.model.User;
import com.example.hanriver.repository.CartRepository;
import com.example.hanriver.model.Cart;
import com.example.hanriver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private CartRepository cartRepository;

    // 사용자 등록 및 카트 생성
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // 비밀번호 인코딩
        user.setAccountNumber(generateUniqueAccountNumber()); // 계좌 번호 생성 및 설정
        user.setBalance(new BigDecimal("1000000")); // 초기 잔고 설정

        User savedUser = userRepository.save(user);

        // 새 카트 생성 및 할당
        Cart newCart = new Cart();
        newCart.setUser(savedUser);
        cartRepository.save(newCart);

        return savedUser;
    }

    // 사용자명으로 사용자 찾기
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 사용자 ID로 사용자 찾기
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public boolean verifyLogin(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }

    // 사용자명 변경
    public boolean updateUsername(Long id, String newUsername) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(newUsername);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // 계좌 번호 생성 및 유니크 확인
    private String generateUniqueAccountNumber() {
        Random random = new Random();
        String accountNumber;
        do {
            accountNumber = random.ints(48, 58) // 0-9 숫자 범위
                    .limit(11) // 11자리 숫자
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        } while (userRepository.existsByAccountNumber(accountNumber)); // 생성된 계좌 번호가 유니크한지 확인
        return accountNumber;
    }

}
