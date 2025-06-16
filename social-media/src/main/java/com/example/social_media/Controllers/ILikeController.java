package com.example.social_media.Controllers;

import com.example.social_media.Dto.LikeDto;
import com.example.social_media.Dto.LikeDtoIU;
import com.example.social_media.Entities.Like;
import com.example.social_media.Entities.LikeId;

import java.util.List;
import java.util.Optional;

public interface ILikeController {
    public List<LikeDtoIU> getAllLikes(Optional<Long> userId, Optional<Long> postId);
    public LikeDtoIU addLike(LikeDtoIU likeDtoIU);
    public LikeDtoIU updateLike(Long id, LikeDtoIU likeDtoIU);
    public void deleteLikeById(Long userId,Long postId);
    public void deleteLikeByUserIdAndPostId(Long userId,Long postId);
}
