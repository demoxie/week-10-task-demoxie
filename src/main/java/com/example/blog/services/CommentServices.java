package com.example.blog.services;

import com.example.blog.model.BlogUser;
import com.example.blog.model.Comments;
import com.example.blog.model.Posts;

import javax.servlet.http.HttpSession;

public interface CommentServices {
    Comments doComment(Comments comments, HttpSession httpSession);
    void deleteComment(Long postId,Long commentId,HttpSession httpSession);
    Comments editComment(Long postId,Long commentId,Comments comment,HttpSession httpSession);
    Comments likeComment(Long userId,Long postId,Long commentId, HttpSession httpSession);
}
