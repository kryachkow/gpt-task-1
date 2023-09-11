package com.my.chatgpttask.convertor;

import com.my.chatgpttask.dto.UserViewDto;
import com.my.chatgpttask.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConvertor {
    public UserViewDto convertUserToDto(User user) {
        return UserViewDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .followersId(user.getFollowers().stream().map(User::getId).collect(Collectors.toList()))
                .followingsId(user.getFollowing().stream().map(User::getId).collect(Collectors.toList()))
                .build();
    }
}
