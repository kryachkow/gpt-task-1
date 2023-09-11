package com.my.chatgpttask.service;

import com.my.chatgpttask.dto.PostManageDto;
import com.my.chatgpttask.dto.PostViewDto;

import java.util.List;

public interface PostService {
    List<PostViewDto> getAllPosts();
    PostViewDto getPostById(Long postId);
    List<PostViewDto> getPostsByUserId(Long userId);
    PostViewDto createPost(PostManageDto postManageDto);
    List<PostViewDto> getAllPostsLikedByUser(Long userId);
    PostViewDto addUserLike(Long userId, Long postId);
}
