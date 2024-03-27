package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment/{postId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                    @Valid  @RequestBody CommentDto commentDto
    ) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId") long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @GetMapping("/post/{postId}/comment/{id}")
    public ResponseEntity<CommentDto> getComment(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id") long cId
    ) {
        return ResponseEntity.ok(commentService.getCommentById(postId, cId));
    }


    @PutMapping("/post/{postId}/comment/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id") long cId,
            @Valid @RequestBody CommentDto commentDto
    ) {
        return ResponseEntity.ok(commentService.updateComment(postId, cId, commentDto));
    }

    @DeleteMapping("/post/{postId}/comment/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "id") long cId
    ) {
        commentService.deleteComment(postId, cId);
        return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.OK);
    }
}
