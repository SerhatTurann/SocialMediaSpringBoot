package com.example.social_media.Services.Impl;

import com.example.social_media.Dto.UserDto;
import com.example.social_media.Entities.Follow;
import com.example.social_media.Entities.User;
import com.example.social_media.Repositories.FollowRepository;
import com.example.social_media.Services.IFollowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowService implements IFollowService {
    @Autowired
    FollowRepository followRepository;
    @Autowired
    UserService userService;

    @Override
    public void followUser(Long followerId, Long followedId) {
        if (followerId.equals(followedId)) {
            throw new IllegalArgumentException("Kendinizi takip edemezsiniz.");
        }
        if (!followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)) {
            Follow follow = new Follow();
            UserDto userDto = userService.findUserById(followerId);
            User followerUser = new User();
            BeanUtils.copyProperties(userDto,followerUser);
            follow.setFollower(followerUser);
            UserDto followedUserDto = userService.findUserById(followedId);
            User followedUser = new User();
            BeanUtils.copyProperties(followedUserDto,followedUser);
            follow.setFollowed(followedUser);
            followRepository.save(follow);
        }
    }


    @Override
    public void unfollowUser(Long followerId, Long followedId) {
        followRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                .ifPresent(followRepository::delete);
    }


    @Override
    public List<UserDto> getFollowers(Long userId) {
        List<UserDto> userDtoList = new ArrayList<>();
        List<Follow> followList = followRepository.findByFollowedId(userId);
        for (Follow follow : followList){
           User user = follow.getFollower();
           UserDto userDto = new UserDto();
           BeanUtils.copyProperties(user,userDto);
           userDtoList.add(userDto);
        }
    return userDtoList;
    }

    @Override
    public List<UserDto> getFollowing(Long userId) {
        List<UserDto> userDtoList = new ArrayList<>();
        List<Follow> followList = followRepository.findByFollowerId(userId);
        for (Follow follow : followList){
            User user = follow.getFollowed();
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user,userDto);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }
}
