package com.Blog.service.impl;

import com.Blog.entity.Category;
import com.Blog.payload.PostDto;
import com.Blog.entity.Post;
import com.Blog.exception.ResourceNotFoundException;
import com.Blog.payload.PostResponse;
import com.Blog.repository.CategoryRepository;
import com.Blog.repository.PostRepository;
import com.Blog.service.IPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    public PostServiceImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PostDto create(PostDto postDto) {

        Category category=categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                ()-> new ResourceNotFoundException("Category","id",postDto.getCategoryId()));

        Post post =mapToEntity(postDto);
        post.setCategory(category);

        Post newPost= getPostRepository().save(post);

        PostDto postResponse = mapToDto(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNo,pageSize, sort);

        Page<Post> list=getPostRepository().findAll(pageable);

        List<Post> postList=list.getContent();

        List<PostDto> content= postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(list.getNumber());
        postResponse.setPageSize(list.getSize());
        postResponse.setTotalElements(list.getTotalElements());
        postResponse.setTotalPage(list.getTotalPages());
        postResponse.setLast(list.isLast());

        return postResponse;
    }

    @Override
    public PostDto getById(long id) {

        Post post=getPostRepository().findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id));

        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {

        Post post=getPostRepository().findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id));;

        Category category= categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                ()-> new ResourceNotFoundException("category","id",postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post post1Update= getPostRepository().save(post);

        return mapToDto(post1Update);
    }

    @Override
    public void delete(long id) {

        Post post=getPostRepository().findById(id).orElseThrow(() -> new ResourceNotFoundException("post","id",id));;
        getPostRepository().delete(post);

    }

    @Override
    public List<PostDto> findByCategoryId(Long id) {

       Category category= getCategoryRepository().findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id",id));

       List<Post> listPost=getPostRepository().findPostByCategoryId(id);

        return listPost.stream().map((list)->mapToDto(list)).collect(Collectors.toList());
    }

    private PostDto mapToDto(Post post){
        PostDto postResponse = mapper.map(post,PostDto.class);
//        postResponse.setId(post.getId());
//        postResponse.setTitle(post.getTitle());
//        postResponse.setDescription(post.getDescription());
//        postResponse.setContent(post.getContent());

        return postResponse;
    }

    private Post mapToEntity(PostDto postDto){
        Post post =mapper.map(postDto,Post.class);
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }

    public PostRepository getPostRepository() {
        return postRepository;
    }

    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ModelMapper getMapper() {
        return mapper;
    }

    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
