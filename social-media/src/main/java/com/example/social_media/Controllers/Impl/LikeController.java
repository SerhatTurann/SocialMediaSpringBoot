package com.example.social_media.Controllers.Impl;

import com.example.social_media.Controllers.ILikeController;
import com.example.social_media.Dto.LikeDto;
import com.example.social_media.Dto.LikeDtoIU;
import com.example.social_media.Entities.Like;
import com.example.social_media.Entities.LikeId;
import com.example.social_media.Services.ILikeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/likes")
public class LikeController implements ILikeController {

    @Autowired
    ILikeService likeService;

    @GetMapping("")
    @Override
    public List<LikeDtoIU> getAllLikes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
        return likeService.getAllLikes(userId,postId);
    }

    @PostMapping(path = "/add")
    @Override
    public LikeDtoIU addLike(@RequestBody LikeDtoIU likeDtoIU) {
        return likeService.addLike(likeDtoIU);
    }

    @PutMapping(path = "/update")
    @Override
    public LikeDtoIU updateLike(@PathVariable(name = "id") Long id, @RequestBody LikeDtoIU likeDtoIU) {
        return likeService.updateLike(new LikeId(likeDtoIU.getUserId(),likeDtoIU.getPostId()),likeDtoIU);
    }


    @DeleteMapping(path = "/deleteById")
    @Override
    public void deleteLikeById(@RequestParam Long userId, @RequestParam Long postId) {
        likeService.deleteLikeById(new LikeId(userId,postId));
    }

    @Transactional
    @DeleteMapping(path = "/delete")
    @Override
    public void deleteLikeByUserIdAndPostId(@RequestParam Long userId, @RequestParam Long postId) {
        likeService.deleteLikeByUserIdAndPostId(userId,postId);
    }
}
