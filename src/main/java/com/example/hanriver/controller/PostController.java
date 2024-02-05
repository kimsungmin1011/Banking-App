package com.example.hanriver.controller;

import com.example.hanriver.dto.PostCreateDTO;
import com.example.hanriver.model.Post;
import com.example.hanriver.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/posts") // 게시물 관련 경로
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시물 작성
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostCreateDTO postCreateDTO) {
        Post newPost = postService.createPost(postCreateDTO);
        return ResponseEntity.ok(newPost);
    }

    // 모든 게시물 조회
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.findAll();
        return ResponseEntity.ok(posts);
    }

    // ID로 특정 게시물 조회
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 게시물 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostCreateDTO postCreateDTO) {
        boolean isUpdated = postService.updatePost(id, postCreateDTO, postCreateDTO.getUserId());
        if (isUpdated) {
            return ResponseEntity.ok("Post updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Could not update post");
        }
    }


    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        Long userId = body.get("userId");
        boolean isDeleted = postService.deletePost(id, userId);
        if (isDeleted) {
            return ResponseEntity.ok("Post deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Could not delete post");
        }
    }
}
