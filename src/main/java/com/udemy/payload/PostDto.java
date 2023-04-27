package com.udemy.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
@Schema(
        description = "PostDto Model Information"
)
public class PostDto implements Serializable {
    private long id;

    @Schema(
            description = "Blog Post title"
    )
    @NotEmpty
    @Size(min = 2,message = "Post title should have at least 2 character")
    private String title;

    @NotEmpty
    @Size(min = 10,message = "Post description should have at least 10 character")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDto> comment;


    private Long categoryId;
}