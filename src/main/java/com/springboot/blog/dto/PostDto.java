package com.springboot.blog.dto;
import lombok.Data;
@Data
public class PostDto {
    private long id ;
    private  String title, description , content;
}

