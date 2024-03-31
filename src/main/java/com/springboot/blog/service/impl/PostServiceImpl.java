package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    // TODO: Study Dependency Injection
    //    @Autowired => NOTE 1
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper , CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Category","id", postDto.getCategoryId()));
        // Convert DTO to Entity
        Post post = mapDTOtoEntity(postDto);
        post.setCategory(category);

        Post newPost = postRepository.save(post);

        // Convert entity to DTO
        PostDto postResponse = mapEntityToDTO(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo , int pageSize , String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // Pagination : Create Pageable instance
        Pageable pageable = (Pageable) PageRequest.of(pageNo,pageSize,sort);

        Page<Post> posts= postRepository.findAll(pageable);

        // Get Content for page object.
        List<Post> listOfPost= posts.getContent();

        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : listOfPost) {
            postDtoList.add(mapEntityToDTO(post));
        }

        return getPostResponse(postDtoList, posts);
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapEntityToDTO(post);
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Category","id", postDto.getCategoryId()));

        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        Post updatedPost = postRepository.save(post);
        return mapEntityToDTO(updatedPost);
    }

    @Override
    public String deletePostById(long id) {
        postRepository.deleteById(id);
        return "Post Deleted with id : " + id;
    }

    @Override
    public List<PostDto> getPostByCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));

        List<Post> posts = postRepository.findByCategoryId(id);
        return posts.stream().map(post -> mapEntityToDTO(post)).collect(Collectors.toList());
    }

    // Convert Entity to DTO.
    private PostDto mapEntityToDTO(Post post){
        PostDto postDto = modelMapper.map(post,PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }
    // Convert Entity to DTO.
    private Post mapDTOtoEntity(PostDto postDto){
        Post post = modelMapper.map(postDto,Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
    private PostResponse getPostResponse(List<PostDto> postDtoList, Page<Post> posts) {
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }
}

/*
NOTE 1
Whenever we configure our class as a spring bean and this class has only one consturctor then we can omit the @Autowired annotation

From spirng 4.3 , if a class is configured as a spring bean and it has only one constructor, we can omit the @Autowired annotation
 */