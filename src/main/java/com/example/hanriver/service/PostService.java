package com.example.hanriver.service;

import com.example.hanriver.dto.PostCreateDTO;
import com.example.hanriver.model.Post;
import com.example.hanriver.model.User;
import com.example.hanriver.repository.PostRepository;
import com.example.hanriver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository; // UserRepository 추가

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository; // UserRepository 의존성 주입
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(PostCreateDTO postCreateDTO) {
        Optional<User> userOptional = userRepository.findById(postCreateDTO.getUserId());
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found with id: " + postCreateDTO.getUserId()); // 적절한 예외 처리
        }
        User user = userOptional.get();

        Post newPost = new Post();
        newPost.setContent(postCreateDTO.getContent());
        newPost.setUser(user); // 조회된 User 객체를 Post에 연결

        return postRepository.save(newPost);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public boolean updatePost(Long id, PostCreateDTO postCreateDTO, Long userId) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent() && existingPost.get().getUser().getId().equals(userId)) {
            Post postToUpdate = existingPost.get();
            postToUpdate.setContent(postCreateDTO.getContent());
            postRepository.save(postToUpdate);
            return true;
        }
        return false;
    }

    public boolean deletePost(Long id, Long userId) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent() && existingPost.get().getUser().getId().equals(userId)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 추가적인 로직 구현
}
