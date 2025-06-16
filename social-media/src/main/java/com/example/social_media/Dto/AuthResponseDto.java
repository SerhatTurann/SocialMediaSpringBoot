package com.example.social_media.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private String username;
    private Long userId;

    /*
    public AuthResponseDto() {}

    public AuthResponseDto(String token, String username) {
        this.token = token;
        this.username = username;
    }*/
}
