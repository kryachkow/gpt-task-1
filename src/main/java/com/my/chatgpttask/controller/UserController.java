package com.my.chatgpttask.controller;

import com.my.chatgpttask.dto.UserManageDto;
import com.my.chatgpttask.dto.UserViewDto;
import com.my.chatgpttask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserViewDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserViewDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/followers/{userId}")
    public List<UserViewDto> getFollowersByUserId(@PathVariable Long userId) {
        return userService.getAllFollowersByUserId(userId);
    }

    @GetMapping("/followings/{userId}")
    public List<UserViewDto> getFollowingsByUserId(@PathVariable Long userId) {
        return userService.getAllFollowingUsersByUserId(userId);
    }

    // Task don`t require proper authentication process, and it wasn't implemented,
    // this method are only for testing purposes
    @PostMapping
    public UserViewDto createUser(@RequestBody UserManageDto userManageDto) {
        return userService.createUser(userManageDto);
    }

    @PostMapping("/followers/{userId}/{userToFollowId}")
    public UserViewDto likePostByUser(@PathVariable Long userId, @PathVariable Long userToFollowId) {
        return userService.followUser(userId, userToFollowId);
    }
}
