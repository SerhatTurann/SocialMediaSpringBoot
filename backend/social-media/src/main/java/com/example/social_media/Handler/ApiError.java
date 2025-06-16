package com.example.social_media.Handler;

import lombok.Data;

@Data
public class ApiError<E> {
    private Integer status;
    private Exception<E> exception;
}
