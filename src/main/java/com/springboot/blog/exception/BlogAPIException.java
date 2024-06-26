package com.springboot.blog.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BlogAPIException extends RuntimeException { // We throw this exception whenever we write some business logic or validate request parameter.
    private HttpStatus status;
    private String message;

    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}
