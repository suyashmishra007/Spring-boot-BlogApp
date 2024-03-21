package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) // This will cause Sprint boot to respond with the specified HTTP Status , whenever this exception is thrown from your compiler.
public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;
    private String fieldName;

    private long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : '%s' ", resourceName,fieldName,fieldValue)); // Post not found with id : 1
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFieldValue() {
        return fieldValue;
    }
}
/*
The APIs will throw a ResourceNotFoundException whenever a Post with a given id is not found in the database.

We use @ResponseStatus annotation in the above exception class. This is cause Spring boot to respond with the specified HTTP status code whenever this exception is thrown from your controller.
 */
