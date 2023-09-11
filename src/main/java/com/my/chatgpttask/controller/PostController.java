package com.my.chatgpttask.controller;

import com.my.chatgpttask.dto.PostManageDto;
import com.my.chatgpttask.dto.PostViewDto;
import com.my.chatgpttask.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostViewDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostViewDto getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @GetMapping("/by-author/{authorId}")
    public List<PostViewDto> getPostByUserId(@PathVariable Long authorId) {
        return postService.getPostsByUserId(authorId);
    }

    @PostMapping
    public PostViewDto createPost(@RequestBody PostManageDto postManageDto) {
        return postService.createPost(postManageDto);
    }

    @GetMapping("/by-user-like/{userId}")
    public List<PostViewDto> getPostsLikedByUser(@PathVariable Long userId) {
        return postService.getAllPostsLikedByUser(userId);
    }

    @PostMapping("/by-user-like/{userId}/{postId}")
    public PostViewDto likePostByUser(@PathVariable Long userId, @PathVariable Long postId) {
        return postService.addUserLike(userId, postId);
    }
}
