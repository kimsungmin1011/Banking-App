package com.example.hanriver.service;

import com.example.hanriver.dto.PostCreateDTO;
import com.example.hanriver.model.Post;
import com.example.hanriver.model.User;
import com.example.hanriver.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(PostCreateDTO postCreateDTO) {
        // 새로운 User 객체 생성 및 설정
        User user = new User();
        user.setId(postCreateDTO.getUserId());
        user.setUsername(postCreateDTO.getUsername());

        // 새로운 Post 객체 생성 및 설정
        Post newPost = new Post();
        newPost.setContent(postCreateDTO.getContent());
        newPost.setUser(user); // 게시글에 사용자 연결

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
