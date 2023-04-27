package com.udemy.controller;

import com.udemy.payload.PostDto;
import com.udemy.payload.PostResponse;
import com.udemy.service.IPostService;
import com.udemy.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;


@RequestMapping("/api")
@RestController
@Tag(
        name = "CRUD REST APIs for Resource"
)
public class PostController {

    @Autowired
    private IPostService postService;

    @Operation(
            summary = "Create Post Rest API",
            description = "Create Post Rest Api is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<PostDto> createNew(@Valid @RequestBody PostDto post){

        return new ResponseEntity<>(getPostService().create(post), HttpStatus.CREATED) ;
    }


    @GetMapping("/")
    public PostResponse getAll(@RequestParam (name = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                               @RequestParam (name = "pageSize", defaultValue =AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageSize,
                               @RequestParam (name = "sortBy", defaultValue =AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                               @RequestParam (name = "sortDir", defaultValue =AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir){

        return getPostService().getAll(pageNo,pageSize,sortBy,sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable(name = "id") long id){

        return ResponseEntity.ok(getPostService().getById(id));
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> update(@Valid @RequestBody PostDto postDto,@PathVariable(name = "id") long id){

        return new ResponseEntity<>(getPostService().updatePost(postDto,id),HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        getPostService().delete(id);
        return new ResponseEntity<>("Entity delete sucessfully",HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getByCategoryId(@PathVariable (name = "id") Long id){

        List<PostDto> list=getPostService().findByCategoryId(id);

        return ResponseEntity.ok(list);

    }

    public IPostService getPostService() {
        return postService;
    }

    public void setPostService(IPostService postService) {
        this.postService = postService;
    }
}
