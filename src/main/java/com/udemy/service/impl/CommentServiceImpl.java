package com.udemy.service.impl;

import com.udemy.entity.Comment;
import com.udemy.entity.Post;
import com.udemy.exception.BlogApiException;
import com.udemy.exception.ResourceNotFoundException;
import com.udemy.payload.CommentDto;
import com.udemy.payload.PostDto;
import com.udemy.repository.CommentRepository;
import com.udemy.repository.PostRepository;
import com.udemy.service.IConmmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements IConmmentService {


    private ModelMapper mapper;

    public CommentServiceImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment=mapToEntity(commentDto);

        Post post=getPostRepository().findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));

        comment.setPost(post);

        Comment newComment=getCommentRepository().save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> findByPostId(long postId) {

        List<Comment> commentList=getCommentRepository().findByPostId(postId);
        return commentList.stream().map( comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto findById(long postId, long commentId) {

        Post post=getPostRepository().findById(postId).orElseThrow(() -> new ResourceNotFoundException("post","id",postId));

        Comment comment=getCommentRepository().findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment","id",commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {

        Post post=getPostRepository().findById(postId).orElseThrow(() -> new ResourceNotFoundException("post","id",postId));

        Comment comment=getCommentRepository().findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment","id",commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        Comment UpComment=getCommentRepository().save(comment);

        return mapToDto(UpComment);
    }

    @Override
    public void deleteComent(long postId, long commentId) {
        Post post=getPostRepository().findById(postId).orElseThrow(() -> new ResourceNotFoundException("post","id",postId));

        Comment comment=getCommentRepository().findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment","id",commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        getCommentRepository().delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment,CommentDto.class);
//        commentDto.setId(comment.getId());
//        commentDto.setBody(comment.getBody());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment =mapper.map(commentDto,Comment.class);
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
        return comment;
    }

    public PostRepository getPostRepository() {
        return postRepository;
    }

    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public CommentRepository getCommentRepository() {
        return commentRepository;
    }

    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
