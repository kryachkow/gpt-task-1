package com.my.chatgpttask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FollowingAlreadyExistsException extends RuntimeException{
    public FollowingAlreadyExistsException(String message) {
        super(message);
    }
}
