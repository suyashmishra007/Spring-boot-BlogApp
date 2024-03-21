package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    // TODO: Study Dependency Injection
    //    @Autowired => NOTE 1
    private PostRepository postRepository;
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // Convert DTO to Entity
        Post post = mapDTOtoEntity(postDto);

        Post newPost = postRepository.save(post);

        // Convert entity to DTO
        PostDto postResponse = mapEntityToDTO(newPost);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts= postRepository.findAll();
        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : posts) {
            postDtoList.add(mapEntityToDTO(post));
        }
        return postDtoList;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapEntityToDTO(post);
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapEntityToDTO(updatedPost);
    }

    @Override
    public String deletePostById(long id) {
        postRepository.deleteById(id);
        return "Post Deleted with id : " + id;
    }

    // Convert Entity to DTO.
    private PostDto mapEntityToDTO(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }
    // Convert Entity to DTO.
    private Post mapDTOtoEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}

/*
NOTE 1
Whenever we configure our class as a spring bean and this class has only one consturctor then we can omit the @Autowired annotation

From spirng 4.3 , if a class is configured as a spring bean and it has only one constructor, we can omit the @Autowired annotation
 */