package com.example.social_media.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String bio;
    private String profilePhotoUrl;
}
