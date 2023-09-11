package com.my.chatgpttask.service.impl;

import com.my.chatgpttask.convertor.PostConvertor;
import com.my.chatgpttask.dto.PostManageDto;
import com.my.chatgpttask.dto.PostViewDto;
import com.my.chatgpttask.entity.Post;
import com.my.chatgpttask.entity.User;
import com.my.chatgpttask.exception.LikeAlreadyExistsException;
import com.my.chatgpttask.repository.PostRepository;
import com.my.chatgpttask.repository.UserRepository;
import com.my.chatgpttask.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConvertor postConvertor;

    @Override
    @Transactional
    public List<PostViewDto> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postConvertor::convertPostToDto)
                .sorted(Comparator.comparing(PostViewDto::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostViewDto getPostById(Long postId) {
        return postConvertor.convertPostToDto(postRepository.getPostById(postId));
    }

    @Override
    @Transactional
    public List<PostViewDto> getPostsByUserId(Long userId) {
        return postRepository
                .getPostsByAuthorId(userId)
                .stream()
                .map(postConvertor::convertPostToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostViewDto createPost(PostManageDto postManageDto) {
        User user = Objects.requireNonNull(userRepository.getUserById(postManageDto.getAuthorId()));
        Post post = new Post();
        post.setTitle(postManageDto.getTitle());
        post.setBody(postManageDto.getBody());
        post.setAuthor(user);
        post.setCreatedAt(new Date());
        return postConvertor.convertPostToDto(postRepository.save(post));
    }

    @Override
    @Transactional
    public List<PostViewDto> getAllPostsLikedByUser(Long userId) {
        User user = Objects.requireNonNull(userRepository.getUserById(userId));
        return user
                .getLikedPosts()
                .stream()
                .map(postConvertor::convertPostToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PostViewDto addUserLike(Long userId, Long postId) {
        User user = Objects.requireNonNull(userRepository.getUserById(userId));
        Post post = Objects.requireNonNull(postRepository.getPostById(postId));
        throwExceptionIfLikeAlreadyExists(user, postId);
        user.getLikedPosts().add(post);
        post.getLikedBy().add(user);
        return postConvertor.convertPostToDto(post);
    }

    private void throwExceptionIfLikeAlreadyExists(User user, Long postId) {
        if(user.getLikedPosts().stream().anyMatch(p -> p.getId().equals(postId))) {
            throw new LikeAlreadyExistsException("Like already exists");
        }
    }


}
