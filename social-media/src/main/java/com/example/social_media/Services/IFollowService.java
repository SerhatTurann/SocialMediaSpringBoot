package com.example.social_media.Services;

import com.example.social_media.Dto.UserDto;

import java.util.List;

public interface IFollowService {
    public void followUser(Long followerId, Long followedId);
    public void unfollowUser(Long followerId, Long followedId);
    public List<UserDto> getFollowers(Long userId);
    public List<UserDto> getFollowing(Long userId);
}
