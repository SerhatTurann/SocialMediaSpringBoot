package com.example.social_media.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDtoIU {
    private Long user_id;
    private String imageUrl;
    private String title;
    private String text;
}