package com.example.social_media.Repositories;

import com.example.social_media.Entities.Like;
import com.example.social_media.Entities.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {
    List<Like> findByUserIdAndPostId(Long userId, Long postId);

    List<Like> findByUserId(Long userId);

    List<Like> findByPostId(Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);
}
