package com.Blog.service;

import com.Blog.payload.PostDto;
import com.Blog.payload.PostResponse;

import java.util.List;

public interface IPostService {

    PostDto create(PostDto post);

    PostResponse getAll(int pageNo, int pageSize, String sortBy,String sortDir);

    PostDto getById(long id);

    PostDto updatePost(PostDto postDto,long id);

    void delete(long id);

    List<PostDto> findByCategoryId(Long id);
}
