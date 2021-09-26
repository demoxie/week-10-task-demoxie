package com.example.blog.controller;

import com.example.blog.model.Comments;
import com.example.blog.services.CommentServices;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentServices commentServices;

    public CommentController(CommentServices commentServices) {
        this.commentServices = commentServices;
    }
    @PostMapping(value="/comment")
    private ResponseEntity<Comments> comment(@RequestBody Comments comments, HttpSession httpSession){
        Comments comments1 = commentServices.doComment(comments,httpSession);
        //System.out.println(comments.getPosts().getPostID());

        var headers = new HttpHeaders();
        headers.add("Responded", "MyController");

        return ResponseEntity.accepted().headers(headers).body(comments1);
    }
    @PutMapping(value="/edit-comment/{postId}/{commentId}")
    private ResponseEntity<Comments> editComment(@PathVariable Long postId,@PathVariable Long commentId,@RequestBody Comments comments, HttpSession httpSession){
        Comments comments1 = commentServices.editComment(postId,commentId,comments,httpSession);

        var headers = new HttpHeaders();
        headers.add("Responded", "MyController");
        return ResponseEntity.accepted().headers(headers).body(comments1);
    }
    @GetMapping(value = "/delete-comment/{postId}/{commentId}")
    private void delete_comment(@PathVariable Long postId,@PathVariable Long commentId,HttpSession httpSession){
        commentServices.deleteComment(postId,commentId,httpSession);
    }
    @GetMapping(value = "/like-comment/{userId}/{postId}/{commentId}")
    private Comments likeAComment(@PathVariable Long userId,@PathVariable Long postId,@PathVariable Long commentId,HttpSession httpSession){
        return commentServices.likeComment(userId,postId,commentId,httpSession);
    }
}
