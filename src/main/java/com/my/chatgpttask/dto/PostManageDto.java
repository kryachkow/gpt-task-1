package com.my.chatgpttask.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostManageDto {
    private String title;
    private String body;
    private Long authorId;
}
