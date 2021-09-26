package com.example.blog.services;

import com.example.blog.model.BlogUser;
//import com.example.blog.model.PostLikers;
import com.example.blog.model.Favourites;
import com.example.blog.model.Posts;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

public interface PostService {
    Posts createPost(Posts posts, HttpSession httpSession);
    String deletePost(Long postId,HttpSession httpSession);
    Posts editPost(Long postId,Posts posts, HttpSession httpSession);
    Posts likePost(Long userId,Long postId,HttpSession httpSession);
    Map<Long, String> addPostToFavourites(Long userId, Long postId, HttpSession httpSession);


}
