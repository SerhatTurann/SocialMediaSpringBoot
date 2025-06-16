package com.example.social_media.Services.Impl;


import com.example.social_media.Dto.LikeDto;
import com.example.social_media.Dto.PostDto;
import com.example.social_media.Dto.PostDtoIU;
import com.example.social_media.Dto.UserDto;
import com.example.social_media.Entities.Like;
import com.example.social_media.Entities.Post;
import com.example.social_media.Entities.User;
import com.example.social_media.Repositories.PostRepository;
import com.example.social_media.Services.IPostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService implements IPostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;
    @Autowired
    private CommentService commentService;

    private List<PostDto> convertDtos(List<Post> posts){
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post:posts){
            PostDto postDto = new PostDto();
            BeanUtils.copyProperties(post,postDto);
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(post.getUser(),userDto);
            postDto.setUser(userDto);
            postDto.getLikes().addAll(likeService.getAllLikes(Optional.empty(), Optional.of(post.getId())));
            postDto.getComments().addAll(commentService.getAllComments(Optional.empty(), Optional.of(post.getId())));
            postDtos.add(postDto);
        }
        return postDtos;
    }


    @Override
    public List<PostDto> getAllPosts(Optional<Long> userId) {
        if (userId.isPresent())
            return convertDtos(postRepository.findByUserId(userId.get()));

        return convertDtos(postRepository.findAll());
    }

    @Override
    public PostDto findPostById(Long id) {
        Optional<Post> optional = postRepository.findById(id);
        if (optional.isPresent()){
            PostDto postDto = new PostDto();
            BeanUtils.copyProperties(optional.get(),postDto);
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(optional.get().getUser(),userDto);
            //postDto.setUser_id(optional.get().getUser().getId());
            postDto.setUser(userDto);
            return postDto;
        }
        return null;
    }

    @Override
    public PostDtoIU addPost(PostDtoIU postDtoIU) {
        UserDto userDto = userService.findUserById(postDtoIU.getUser_id());
        if (userDto!=null){
            Post post = new Post();
            BeanUtils.copyProperties(postDtoIU,post);
            User user = new User();
            BeanUtils.copyProperties(userDto,user);
            user.setId(postDtoIU.getUser_id());
            post.setUser(user);
            postRepository.save(post);
            return postDtoIU;
        }
        return null;
    }

    @Override
    public PostDtoIU updatePost(Long id, PostDtoIU postDtoIU) {
        Optional<Post> optional = postRepository.findById(id);
        if (optional.isPresent()){
            Post _post = optional.get();
            BeanUtils.copyProperties(postDtoIU,_post);
            postRepository.save(_post);
            return postDtoIU;
        }
        return null;
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
