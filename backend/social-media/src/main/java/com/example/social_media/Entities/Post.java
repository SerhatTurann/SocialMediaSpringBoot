package com.example.social_media.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    private String title;
    private String text;
    private String imageUrl;
    @OneToMany(mappedBy = "post")
    private List<Like> likes;
    @OneToMany
    private List<Comment> comments;

}
