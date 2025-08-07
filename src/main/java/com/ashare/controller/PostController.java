package com.ashare.controller;

import com.ashare.dto.PostDTO;
import com.ashare.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAll() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO dto) {
        return ResponseEntity.ok(postService.createPost(dto.getContent()));
    }
}
