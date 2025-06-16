package com.example.social_media.Handler;

import lombok.Data;

import java.util.Date;

@Data
public class Exception<E> {
    private String hostName;
    private String path;
    private Date createTime;
    private E message;
}
