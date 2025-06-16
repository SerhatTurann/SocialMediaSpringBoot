package com.example.social_media.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table
@Data
@IdClass(LikeId.class)
public class Like {
    @Id
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @Id
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;
}
