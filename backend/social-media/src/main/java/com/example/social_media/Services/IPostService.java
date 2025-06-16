package com.example.social_media.Services;

import com.example.social_media.Dto.PostDto;
import com.example.social_media.Dto.PostDtoIU;
import com.example.social_media.Entities.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    public List<PostDto> getAllPosts(Optional<Long> userId);
    public PostDto findPostById(Long id);
    public PostDtoIU addPost(PostDtoIU postDtoIU);
    public PostDtoIU updatePost(Long id, PostDtoIU postDtoIU);
    public void deletePost(Long id);

}
