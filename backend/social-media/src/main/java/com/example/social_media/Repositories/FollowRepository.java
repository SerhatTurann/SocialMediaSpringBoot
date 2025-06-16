package com.example.social_media.Repositories;

import com.example.social_media.Entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {
    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);
    Optional<Follow> findByFollowerIdAndFollowedId(Long followerId, Long followedId);

    List<Follow> findByFollowerId(Long followerId); // Following list
    List<Follow> findByFollowedId(Long followedId); // Follower list

    long countByFollowerId(Long followerId); // Kaç kişiyi takip ediyor
    long countByFollowedId(Long followedId); // Kaç kişi takip ediyor
}
