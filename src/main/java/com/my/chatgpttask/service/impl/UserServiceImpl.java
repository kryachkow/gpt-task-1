package com.my.chatgpttask.service.impl;

import com.my.chatgpttask.convertor.UserConvertor;
import com.my.chatgpttask.dto.UserManageDto;
import com.my.chatgpttask.dto.UserViewDto;
import com.my.chatgpttask.entity.User;
import com.my.chatgpttask.exception.FollowingAlreadyExistsException;
import com.my.chatgpttask.exception.FollowingYourselfForbiddenException;
import com.my.chatgpttask.repository.UserRepository;
import com.my.chatgpttask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConvertor userConvertor;

    @Override
    @Transactional
    public List<UserViewDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userConvertor::convertUserToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserViewDto getUserById(Long userId) {
        return userConvertor.convertUserToDto(userRepository.getUserById(userId));
    }

    @Override
    @Transactional
    public UserViewDto createUser(UserManageDto userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setUsername(userRequest.getUsername());
        return userConvertor.convertUserToDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public List<UserViewDto> getAllFollowersByUserId(Long userId) {
        return Objects.requireNonNull(userRepository.getUserById(userId))
                .getFollowers()
                .stream()
                .map(userConvertor::convertUserToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<UserViewDto> getAllFollowingUsersByUserId(Long userId) {
        return Objects.requireNonNull(userRepository.getUserById(userId))
                .getFollowing()
                .stream()
                .map(userConvertor::convertUserToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserViewDto followUser(Long userId, Long userToFollowId) {
        User user = Objects.requireNonNull(userRepository.getUserById(userId));
        User userToFollow = Objects.requireNonNull(userRepository.getUserById(userToFollowId));
        checkForbiddenFollowing(user, userToFollow);
        user.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(user);
        return userConvertor.convertUserToDto(user);
    }

    private void checkForbiddenFollowing(User user, User followedUser) {
        if (followedUser.getFollowers().stream().anyMatch(u -> u.getId().equals(user.getId()))) {
            throw new FollowingAlreadyExistsException("Following already exists");
        }
        if (Objects.equals(user.getId(), followedUser.getId())) {
            throw new FollowingYourselfForbiddenException("You cannot follow yourself");
        }
    }
}
