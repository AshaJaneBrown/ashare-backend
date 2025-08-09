package com.ashare.service;

import com.ashare.dto.PostDTO;
import com.ashare.mapper.PostMapper;
import com.ashare.model.Post;
import com.ashare.model.User;
import com.ashare.repository.PostRepository;
import com.ashare.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.ashare.mapper.PostMapper.toDto;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostMapper::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public PostDTO createPost(String content) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("EMAIL for post " + email );
        User user = userRepository.findByEmail(email).orElseThrow();

        Post post = new Post();
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());
        post.setAuthor(user);

        return toDto(postRepository.save(post));
    }
}