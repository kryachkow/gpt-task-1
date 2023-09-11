package com.my.chatgpttask.convertor;

import com.my.chatgpttask.dto.PostViewDto;
import com.my.chatgpttask.entity.Post;
import com.my.chatgpttask.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PostConvertor {
    public PostViewDto convertPostToDto(Post post) {
        return PostViewDto.builder()
                .title(post.getTitle())
                .body(post.getBody())
                .authorId(post.getAuthor().getId())
                .createdAt(post.getCreatedAt())
                .likedByIds(post.getLikedBy().stream().map(User::getId).collect(Collectors.toList()))
                .build();
    }
}
