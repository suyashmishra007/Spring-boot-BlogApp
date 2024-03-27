package com.springboot.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    @NotEmpty(message = "Name should not be null")
    private String name;

    @NotEmpty(message = "Email should not be null")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10 , message = "Body should have at-least 10 chars.")
    private String body;
}
