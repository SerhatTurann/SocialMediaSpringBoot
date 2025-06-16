package com.example.social_media.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowDto {
    private Long id;
    private Long followedId;
    private Long followerId;
}
