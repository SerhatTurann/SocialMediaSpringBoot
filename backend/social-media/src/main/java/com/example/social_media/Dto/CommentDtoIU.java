package com.example.social_media.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDtoIU {
    private Long id;
    private Long userId;
    private Long postId;
    private String text;
}
