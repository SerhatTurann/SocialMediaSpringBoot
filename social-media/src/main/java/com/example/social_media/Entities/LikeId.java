package com.example.social_media.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

import java.io.Serializable;
import java.util.Objects;

public class LikeId implements Serializable {
    private Long user;
    private Long post;

    public LikeId() {
    }

    public LikeId(Long user, Long post) {
        this.user = user;
        this.post = post;
    }

    // equals() ve hashCode() zorunlu
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeId)) return false;
        LikeId likeId = (LikeId) o;
        return Objects.equals(user, likeId.user) &&
                Objects.equals(post, likeId.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, post);
    }
}

