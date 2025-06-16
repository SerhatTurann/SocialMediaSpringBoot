package com.example.social_media.Services.Impl;

import com.example.social_media.Dto.*;
import com.example.social_media.Entities.Like;
import com.example.social_media.Entities.LikeId;
import com.example.social_media.Entities.Post;
import com.example.social_media.Entities.User;
import com.example.social_media.Repositories.LikeRepository;
import com.example.social_media.Services.ILikeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LikeService implements ILikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserService userService;
    @Lazy
    @Autowired
    private PostService postService;

    private List<LikeDtoIU> convertDtos(List<Like> likes){
        List<LikeDtoIU> likeDtos = new ArrayList<>();
        for (Like like:likes){
            LikeDtoIU likeDto = new LikeDtoIU();
            likeDto.setPostId(like.getPost().getId());
            likeDto.setUserId(like.getUser().getId());
            likeDtos.add(likeDto);
        }
        return likeDtos;
    }

    @Override
    public List<LikeDtoIU> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent()&& postId.isPresent()){
            return convertDtos(likeRepository.findByUserIdAndPostId(userId.get(),postId.get()));
        } else if (userId.isPresent()) {return convertDtos(likeRepository.findByUserId(userId.get()));

        } else if (postId.isPresent()) {return convertDtos(likeRepository.findByPostId(postId.get()));

        }else{return convertDtos(likeRepository.findAll());
        }
    }



    @Override
    public LikeDtoIU addLike(LikeDtoIU likeDtoIU) {

        Like like = new Like();
        PostDto postDto = postService.findPostById(likeDtoIU.getPostId());
        UserDto userDto = userService.findUserById(likeDtoIU.getUserId());
        if (postDto!=null&&userDto!=null){
            BeanUtils.copyProperties(likeDtoIU,like);
            Post post = new Post();
            BeanUtils.copyProperties(postDto,post);
            post.setId(likeDtoIU.getPostId());
            like.setPost(post);
            User user = new User();
            BeanUtils.copyProperties(userDto,user);
            user.setId(likeDtoIU.getUserId());
            like.setUser(user);
            likeRepository.save(like);
            return likeDtoIU;
        }
        return null;
    }

    @Override
    public LikeDtoIU updateLike(LikeId id, LikeDtoIU likeDtoIU) {
        Optional<Like> optional = likeRepository.findById(id);
        if (optional.isPresent()){
            Like like = optional.get();
            BeanUtils.copyProperties(likeDtoIU,like);

            likeRepository.save(like);
            return likeDtoIU;
        }
        return null;
    }

    @Override
    public void deleteLikeByUserIdAndPostId(Long userId, Long postId) {
        UserDto user=userService.findUserById(userId);
        PostDto post = postService.findPostById(postId);
        if (user!=null&&post!=null) likeRepository.deleteByUserIdAndPostId(userId,postId);
        System.out.println("İşlem yapılamadı.");
    }


    @Override
    public void deleteLikeById(LikeId id) {
        likeRepository.deleteById(id);
    }
}
