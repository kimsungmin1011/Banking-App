package com.example.hanriver.service;

import com.example.hanriver.model.User;
import com.example.hanriver.repository.CartRepository;
import com.example.hanriver.model.Cart;
import com.example.hanriver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
