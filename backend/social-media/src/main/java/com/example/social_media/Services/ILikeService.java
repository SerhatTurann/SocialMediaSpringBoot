package com.example.social_media.Services;

import com.example.social_media.Dto.LikeDto;
import com.example.social_media.Dto.LikeDtoIU;
import com.example.social_media.Entities.Like;
import com.example.social_media.Entities.LikeId;

import java.util.List;
import java.util.Optional;

public interface ILikeService {
    public List<LikeDtoIU> getAllLikes(Optional<Long> userId, Optional<Long> postId);
    public LikeDtoIU addLike(LikeDtoIU likeDtoIU);
    public LikeDtoIU updateLike(LikeId id, LikeDtoIU likeDtoIU);
    public void deleteLikeByUserIdAndPostId(Long userId, Long postId);
    public void deleteLikeById(LikeId id);
}
