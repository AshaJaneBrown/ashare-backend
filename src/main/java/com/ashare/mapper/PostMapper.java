package com.ashare.mapper;

import com.ashare.dto.PostDTO;
import com.ashare.dto.UserDTO;
import com.ashare.model.Post;
import com.ashare.model.User;

public class PostMapper {

    public static PostDTO toDto(Post post) {
        User author = post.getAuthor();
        UserDTO authorDto = new UserDTO(
                author.getId(),
                author.getUsername(),
                author.getEmail(),
                author.getRole(),
                author.getBio()
        );

        return new PostDTO(
                post.getId(),
                post.getContent(),
                post.getCreatedAt(),
                authorDto
        );
    }
}