package com.example.social_media.Services;

import com.example.social_media.Dto.CommentDto;
import com.example.social_media.Dto.CommentDtoIU;
import com.example.social_media.Entities.Comment;

import java.util.List;
import java.util.Optional;

public interface ICommentService {
    public List<CommentDtoIU> getAllComments(Optional<Long> userId, Optional<Long> postId);
    public CommentDto findCommentById(Long id);
    public CommentDtoIU addComment(CommentDtoIU commentDtoIU);
    public CommentDtoIU updateComment(Long id, CommentDtoIU commentDtoIU);
    public void deleteComment(Long id);
}
