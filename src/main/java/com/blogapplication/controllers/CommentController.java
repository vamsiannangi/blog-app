package com.blogapplication.controllers;


import com.blogapplication.entity.Comments;
import com.blogapplication.entity.Post;
import com.blogapplication.services.CommentService;
import com.blogapplication.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;


@Controller
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;
    private PostService postService;
    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }
    @PostMapping("/save")
    public String saveComment(@RequestParam("postId") Long postId,
                              @RequestParam("name") String name,
                              @RequestParam("email") String email,
                              @RequestParam("comments") String comments) {
        Post post = postService.findById(postId);
        Comments comment = new Comments();
        comment.setName(name);
        comment.setEmail(email);
        comment.setComments(comments);
        comment.setPost(post);
        commentService.save(comment);
        return "redirect:/showPost?postId=" + postId;
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("commentId") Long commentId, Model model) {
        Comments comment = commentService.findById(commentId);
        model.addAttribute("comment", comment);
        return "comment-updateForm";
    }

    @PostMapping("/update")
    public String updateComment(@RequestParam("commentId") Long commentId,
                                @RequestParam("updatedText") String updatedText) {
        Comments comment = commentService.findById(commentId);
        comment.setComments(updatedText);
        commentService.updateComment(comment);
        return "redirect:/showPost?postId=" + comment.getPost().getId();
    }

    @PostMapping("/delete")
    public String deleteComment(@RequestParam("commentId") Long commentId) {
        Comments comment = commentService.findById(commentId);
        Long postId = comment.getPost().getId();
        commentService.deleteById(commentId);
        return "redirect:/posts/showPost?postId=" + postId;
    }
}