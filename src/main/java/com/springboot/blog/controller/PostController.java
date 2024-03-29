package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    private PostService postService;
    @Autowired // NOTE 1
    public PostController(PostService postService, ModelMapper modelMapper) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/post")
    public ResponseEntity<PostDto>  createPost(@Valid @RequestBody PostDto postDto) { // NOTE 2 ;
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo ,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE , required = false) int pageSize,
            @RequestParam(value = "sortBy" , defaultValue = AppConstants.DEFAULT_SORTBY , required = false) String sortBy,
            @RequestParam(value = "sortDir" , defaultValue = AppConstants.DEFAULT_SORTDIR , required = false) String sortDir
    ) {
        return new ResponseEntity<>(postService.getAllPosts(pageNo,pageSize,sortBy,sortDir), HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/post/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.updatePostById(postDto,id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.deletePostById(id),HttpStatus.OK);
    }
}

/*
NOTE 1
Whenever we configure our class as a spring bean and this class has only one consturctor then we can omit the @Autowired annotation

From spring 4.3 , if a class is configured as a spring bean and it has only one constructor, we can omit the @Autowired annotation


NOTE 2
RequestBody convert a JSON into a java object that a postDTO.
 */