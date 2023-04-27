package com.Blog.service;

import com.Blog.payload.CommentDto;

import java.util.List;

public interface IConmmentService {

    CommentDto createComment(long postId, CommentDto commentDto );

    List<CommentDto> findByPostId(long postId);

    CommentDto findById(long postId, long commentId);

    CommentDto updateComment(long postId, long commentId,CommentDto commentDto);

    void deleteComent(long postId, long commentId);
}
