package com.my.chatgpttask.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserViewDto {
    private Long id;
    private String username;
    private String email;
    private List<Long> followersId;
    private List<Long> followingsId;
}
