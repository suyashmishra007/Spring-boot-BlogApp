package com.springboot.blog.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private Date timeStamp;
    private String message;
    private String details;
}
