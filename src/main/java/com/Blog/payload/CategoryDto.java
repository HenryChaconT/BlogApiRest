package com.Blog.payload;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements Serializable {
    private Long id;
    private String name;
    private String description;
}