package com.example.social_media.Dto;

import com.example.social_media.Entities.Post;
import com.example.social_media.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
    private User user;
    private Post post;
}
