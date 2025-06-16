package com.example.social_media.Controllers.Impl;

import com.example.social_media.Controllers.IPostController;
import com.example.social_media.Dto.PostDto;
import com.example.social_media.Dto.PostDtoIU;
import com.example.social_media.Entities.Post;
import com.example.social_media.Services.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController implements IPostController {

    @Autowired
    IPostService postService;

    @GetMapping()
    @Override
    public List<PostDto> getAllPosts(@RequestParam Optional<Long> userId) {
        return postService.getAllPosts(userId);
    }

    @GetMapping(path = "/{id}")
    @Override
    public PostDto findPostById(@PathVariable(name = "id") Long id) {
        return postService.findPostById(id);
    }

    @PostMapping(path = "/add")
    @Override
    public PostDtoIU addPost(@RequestBody PostDtoIU postDtoIU) {
        return postService.addPost(postDtoIU);
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public PostDtoIU updatePost(@PathVariable(name = "id") Long id, @RequestBody PostDtoIU postDtoIU) {
        return postService.updatePost(id,postDtoIU);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
    }
}
