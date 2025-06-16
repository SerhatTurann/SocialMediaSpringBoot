package com.example.social_media.Services.Impl;

import com.example.social_media.Dto.CommentDto;
import com.example.social_media.Dto.CommentDtoIU;
import com.example.social_media.Dto.PostDto;
import com.example.social_media.Dto.UserDto;
import com.example.social_media.Entities.Comment;
import com.example.social_media.Entities.Post;
import com.example.social_media.Entities.User;
import com.example.social_media.Exception.BaseException;
import com.example.social_media.Exception.ErrorMessage;
import com.example.social_media.Exception.MessageType;
import com.example.social_media.Repositories.CommentRepository;
import com.example.social_media.Services.ICommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements ICommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserService userService;
    @Lazy
    @Autowired
    PostService postService;

    private List<CommentDtoIU> convertDtos(List<Comment> comments){
        List<CommentDtoIU> commentDtos = new ArrayList<>();
        for (Comment comment:comments){
            CommentDtoIU commentDto = new CommentDtoIU();
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setPostId(comment.getPost().getId());
            commentDto.setUserId(comment.getUser().getId());
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }

    @Override
    public List<CommentDtoIU> getAllComments( Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent()&& postId.isPresent()){
            return convertDtos(commentRepository.findByUserIdAndPostId(userId.get(),postId.get()));
        } else if (userId.isPresent()) {return convertDtos(commentRepository.findByUserId(userId.get()));

        } else if (postId.isPresent()) {return convertDtos(commentRepository.findByPostId(postId.get()));

        }else{return convertDtos(commentRepository.findAll());
        }
    }

    @Override
    public CommentDto findCommentById(Long id) {
        Optional<Comment> optional = commentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString()));
        }
        CommentDto commentDto = new CommentDto();
        BeanUtils.copyProperties(optional.get(),commentDto);
        return commentDto;
    }

    @Override
    public CommentDtoIU addComment(CommentDtoIU commentDtoIU) {
        Comment comment = new Comment();
        PostDto postDto = postService.findPostById(commentDtoIU.getPostId());
        UserDto userDto = userService.findUserById(commentDtoIU.getUserId());
        if(postDto==null||userDto==null){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,commentDtoIU.getId().toString()));
        }
        BeanUtils.copyProperties(commentDtoIU,comment);
        Post post = new Post();
        BeanUtils.copyProperties(postDto,post);
        post.setId(commentDtoIU.getPostId());
        comment.setPost(post);
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        user.setId(commentDtoIU.getUserId());
        comment.setUser(user);
        commentRepository.save(comment);
        return commentDtoIU;

    }

    @Override
    public CommentDtoIU updateComment(Long id, CommentDtoIU commentDtoIU) {
        Optional<Comment> optional = commentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString()));
        }
        Comment comment = optional.get();
        BeanUtils.copyProperties(commentDtoIU,comment);

        commentRepository.save(comment);
        return commentDtoIU;

    }

    @Override
    public void deleteComment(Long id) {
        Optional<Comment> optional = commentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString()));
        }
        Comment comment = optional.get();
        commentRepository.delete(comment);
    }
}
