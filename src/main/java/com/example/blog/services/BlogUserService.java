package com.example.blog.services;

import com.example.blog.model.BlogUser;
import com.example.blog.model.Posts;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface BlogUserService {
    BlogUser login(BlogUser blogUser, HttpSession httpSession);
    BlogUser register(BlogUser blogUser);
    BlogUser addUserToFriendList(Long userId,Long friendId, HttpSession httpSession);
    String logout(HttpSession httpSession);
    void deactivateAccount(Long userId, HttpSession httpSession);
    String cancelDeactivation(Long userId);
    void cancel();
    ResponseEntity<BlogUser> getBlogUser(Long blogUserID);
    List<BlogUser> getAllBlogUsers();
    BlogUser updateBlogUser(BlogUser blogUser);
    void deleteBlogUser(Long blogUserID);
    void deactivate();
}
