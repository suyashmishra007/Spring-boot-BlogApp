package com.springboot.blog.dto;

import lombok.Data;

@Data
public class CommentDto {
    private long id;
    private String name, email, body;
}
