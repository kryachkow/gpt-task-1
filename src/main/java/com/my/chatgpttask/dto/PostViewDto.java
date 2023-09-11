package com.my.chatgpttask.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PostViewDto {
    private String title;
    private String body;
    private Date createdAt;
    private Long authorId;
    private List<Long> likedByIds;

}
