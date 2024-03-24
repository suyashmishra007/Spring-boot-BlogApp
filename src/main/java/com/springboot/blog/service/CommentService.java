package com.springboot.blog.service;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId , CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(long postId,  long commentId);

    CommentDto updateComment(long postId , long comment , CommentDto commentDto);

    void deleteComment(long postId, long commentId);
}
