package com.my.chatgpttask.dto;

import lombok.Builder;
import lombok.Data;

// Task don`t require proper authentication process, and it wasn't implemented,
// this class as createUser() methods are only for testing purposes
@Data
@Builder
public class UserManageDto {
    private String username;
    private String password;
    private String email;
}
