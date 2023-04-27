package com.Blog.controller;

import com.Blog.payload.CommentDto;
import com.Blog.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @PostMapping("/{postId}/comment")
    public ResponseEntity<CommentDto> createNew(@PathVariable(value = "postId") long postId,@Valid @RequestBody CommentDto commentDto){

        return new ResponseEntity<>(getCommentService().createComment(postId,commentDto), HttpStatus.CREATED) ;
    }

    @GetMapping("/{postId}/comment")
    public List<CommentDto> findByPostId(@PathVariable(value = "postId") Long postId){
        return getCommentService().findByPostId(postId);
    }

    @GetMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> findCommentById(@PathVariable(value = "postId")long postid,
                                                      @PathVariable(value = "commentId")long commentId){

        CommentDto commentDto=getCommentService().findById(postid,commentId);

        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @PutMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto>updateComent(@PathVariable(value = "postId")long postid,
                                                  @PathVariable(value = "commentId")long commentId,
                                                  @Valid @RequestBody CommentDto commentDto){

        CommentDto UpCommentDto=getCommentService().updateComment(postid,commentId,commentDto);

        return new ResponseEntity<>(UpCommentDto,HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteComent(@PathVariable(value = "postId")long postid,
                                               @PathVariable(value = "commentId")long commentId){

        getCommentService().deleteComent(postid,commentId);

        return new ResponseEntity<>("Comment deleted sucefull",HttpStatus.OK);
    }

    public CommentServiceImpl getCommentService() {
        return commentService;
    }

    public void setCommentService(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }
}
