package com.my.chatgpttask.service;

import com.my.chatgpttask.dto.UserManageDto;
import com.my.chatgpttask.dto.UserViewDto;

import java.util.List;

public interface UserService {
    List<UserViewDto> getAllUsers();

    UserViewDto getUserById(Long userId);

    UserViewDto createUser(UserManageDto userRequest);

    List<UserViewDto> getAllFollowersByUserId(Long userId);

    List<UserViewDto> getAllFollowingUsersByUserId(Long userId);

    UserViewDto followUser(Long userId, Long userToFollowId);
}
