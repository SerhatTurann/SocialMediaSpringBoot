package com.example.social_media.Dto;

import com.example.social_media.Entities.Like;
import com.example.social_media.Entities.Post;
import com.example.social_media.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private UserDto user;
    private String title;
    private String text;
    private String imageUrl;
    private List<LikeDtoIU> likes = new ArrayList<>();
    private List<CommentDtoIU> comments = new ArrayList<>();
}
