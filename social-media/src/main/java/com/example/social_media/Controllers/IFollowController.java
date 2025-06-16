package com.example.social_media.Controllers;

import com.example.social_media.Dto.UserDto;

import java.util.List;

public interface IFollowController {
    public void followUser(Long followerId, Long followedId);
    public void unfollowUser(Long followerId, Long followedId);
    public List<UserDto> getFollowers(Long userId);
    public List<UserDto> getFollowing(Long userId);
}
