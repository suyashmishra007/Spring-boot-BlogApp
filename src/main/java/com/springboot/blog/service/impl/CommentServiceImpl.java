package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    // We Configured as a spring bean and whenever it has one consturctor , then we can omit adding @Autowired
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long id, CommentDto commentDto) {
        // map commentDto to Entity
        Comment comment = mapDTOtoEntity(commentDto);

        // Retrieve post by id.
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        // set post to comment entity
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        // map Entity to commentDto
        return mapEntityToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        // Convert list of comments Entites to CommentsDtos.
        return comments.stream().map(this::mapEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        // TODO:
        //         if(!comment.getPost().getId().equals(post.getId())){
        //             throw  new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does't belong to post ");
        //         }

        return mapEntityToDTO(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        // TODO:
        //         if(!comment.getPost().getId().equals(post.getId())){
        //             throw  new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does't belong to post ");
        //         }

        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        Comment updatedComment = commentRepository.save(comment);

        return mapEntityToDTO(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // TODO:
        //         if(!comment.getPost().getId().equals(post.getId())){
        //             throw  new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does't belong to post ");
        //         }

        commentRepository.delete(comment);
    }

    // Convert Entity to DTO.
    private Comment mapDTOtoEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto,Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }

    private CommentDto mapEntityToDTO(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment,CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}