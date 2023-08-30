package com.blogapplication.services;

import com.blogapplication.entity.Comments;
import com.blogapplication.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CommentService {
    List<Comments> findAll();
    Comments findById(int theId);
    void save(Comments comments);
    void deleteById(int theId);
    public void updateComment(Comments comment);
    Comments findById(Long commentId);

    void deleteById(Long commentId);
}