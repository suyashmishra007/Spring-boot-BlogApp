package com.springboot.blog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long id;
    // title should not be null or empty , it should have atleast 2 characters.
    @NotEmpty
    @Size(min = 2, message = "Post title should have atleast 2 characters.")
    private String title;

    // description should not be not null or empty.
    // description should have at least 10 characters
    @NotEmpty
    @Size(min = 10 , message = "Post description should have atleast 2 characters.")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}

