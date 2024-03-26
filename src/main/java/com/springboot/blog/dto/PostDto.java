package com.springboot.blog.dto;
import lombok.Data;
import java.util.Set;
@Data
public class PostDto {
    private long id ;
    private  String title, description , content;
    private Set<CommentDto> comments;
}

