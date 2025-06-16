package com.example.social_media.Controllers.Impl;

import com.example.social_media.Controllers.ICommentController;
import com.example.social_media.Dto.CommentDto;
import com.example.social_media.Dto.CommentDtoIU;
import com.example.social_media.Entities.Comment;
import com.example.social_media.Services.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/comments")
public class CommentController implements ICommentController {

    @Autowired
    ICommentService commentService;

    @GetMapping(path = "")
    @Override
    public List<CommentDtoIU> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
        return commentService.getAllComments(userId,postId);
    }

    @GetMapping(path = "/{id}")
    @Override
    public CommentDto findCommentById(@PathVariable(name = "id") Long id) {
        return commentService.findCommentById(id);
    }

    @PostMapping(path = "/add")
    @Override
    public CommentDtoIU addComment(@RequestBody CommentDtoIU commentDtoIU) {
        return commentService.addComment(commentDtoIU);
    }

    @PutMapping(path = "/update/{id}")
    @Override
    public CommentDtoIU updateComment(@PathVariable(name = "id") Long id, @RequestBody CommentDtoIU commentDtoIU) {
        return commentService.updateComment(id,commentDtoIU);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deleteComment(@PathVariable(name = "id") Long id) {

    }
}
