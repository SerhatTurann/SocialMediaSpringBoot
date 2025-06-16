package com.example.social_media.Controllers.Impl;

import com.example.social_media.Controllers.IFollowController;
import com.example.social_media.Dto.UserDto;
import com.example.social_media.Services.IFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follows")
public class FollowController implements IFollowController {
    @Autowired
    IFollowService followService;


    @PostMapping("/follow")
    @Override
    public void followUser(@RequestParam Long followerId,@RequestParam Long followedId) {
        followService.followUser(followerId,followedId);
    }

    @DeleteMapping("/unfollow")
    @Override
    public void unfollowUser(@RequestParam Long followerId,@RequestParam Long followedId) {
        followService.unfollowUser(followerId,followedId);

    }

    @GetMapping("/followers/{followerId}")
    @Override
    public List<UserDto> getFollowers(@PathVariable(name = "followerId") Long userId) {
        return followService.getFollowers(userId);
    }

    @GetMapping("/following/{followedId}")
    @Override
    public List<UserDto> getFollowing(@PathVariable(name = "followedId") Long userId) {
        return followService.getFollowing(userId);
    }
}
