package com.Blog.payload;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

@Data
public class CommentDto implements Serializable {
    private Long id;

    @NotEmpty(message = "Name should not be null empty")
    private String name;

    @NotEmpty(message = "Email should not be null empty")
    @Email
    private String email;

    @NotEmpty(message = "Body should not be null empty")
    @Size(min = 10,message = "Comment body must be minimum 10 character")
    private String body;


}