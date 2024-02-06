package com.example.hanriver.controller;

import com.example.hanriver.model.User;
import com.example.hanriver.service.UserService;
import com.example.hanriver.service.JwtService;
import com.example.hanriver.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.hanriver.dto.UsernameUpdateDTO; // UsernameUpdateDTO 임포트
import java.util.Optional;

@RestController
@RequestMapping("/users") // 기본 URL 경로
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    // 사용자 등록
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // 사용자명으로 사용자 찾기
    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 사용자 ID로 사용자 찾기
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 현재 인증된 사용자의 프로필 정보 가져오기
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Optional<User> user = userService.findByUsername(currentUsername);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 로그인
    @PostMapping("/reallogin")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        if (userService.verifyLogin(loginRequest.getUsername(), loginRequest.getPassword())) {
            User user = userService.findByUsername(loginRequest.getUsername()).get();
            // JWT 토큰 생성 시 계좌번호와 잔고 정보를 포함
            String token = jwtService.createToken(user.getId(), user.getUsername(), user.getAccountNumber(), user.getBalance());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }


    // 사용자명 변경 메소드 수정
    @PutMapping("/{id}/updateUsername")
    public ResponseEntity<?> updateUsername(@PathVariable Long id, @RequestBody UsernameUpdateDTO dto) {
        boolean isUpdated = userService.updateUsername(id, dto.getNewUsername());
        if (isUpdated) {
            return ResponseEntity.ok("Username updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Could not update username");
        }
    }
}
