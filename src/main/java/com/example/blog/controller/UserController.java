package com.example.blog.controller;

import com.example.blog.model.BlogUser;
import com.example.blog.services.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final BlogUserService blogUserService;
    @Autowired
    public UserController(BlogUserService blogUserService) {
        this.blogUserService = blogUserService;
    }

    @PostMapping(value="/register")
    private ResponseEntity<BlogUser> register(@RequestBody BlogUser blogUser){
        try {
            return new ResponseEntity<>(blogUserService.register(blogUser), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="/login")
    private ResponseEntity<BlogUser> login(@RequestBody BlogUser blogUser, HttpSession httpSession){
        try {
            return new ResponseEntity<>(blogUserService.login(blogUser,httpSession), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/viewBlogUser/{id}")
    private ResponseEntity<BlogUser> getViewBlogUser(@PathVariable Long id){
        return blogUserService.getBlogUser(id);
    }
    @GetMapping(value="/logout")
    private String getViewBlogUser(HttpSession httpSession){
        return blogUserService.logout(httpSession);
    }

    @GetMapping(value="/viewAllBlogUsers")
    private List<BlogUser> getViewAllBlogUsers(){
        return blogUserService.getAllBlogUsers();

    }
    @PutMapping(value="/updateBlogUser/{id}")
    private ResponseEntity<BlogUser> updateBlogUser(@PathVariable Long id){
        return blogUserService.getBlogUser(id);
    }


    @GetMapping(value="/deactivate-account/{userId}")
    private void deleteAccount(@PathVariable Long userId, HttpSession httpSession){
        //String result = blogUserService.deactivateAccount(userId,httpSession).toString();
        blogUserService.deactivateAccount(userId,httpSession);
    }
    @GetMapping(value="/cancel-deactivation/{userId}")
    private String cancelDeactivation(@PathVariable Long userId, HttpSession httpSession){
        return blogUserService.cancelDeactivation(userId);

    }
    @GetMapping(value = "/add-friend/{userId}/{friendId}")
    private ResponseEntity<BlogUser> connectToUser(@PathVariable Long userId,@PathVariable Long friendId, HttpSession httpSession){
        BlogUser blogUser1 = blogUserService.addUserToFriendList(userId,friendId, httpSession);

        var headers = new HttpHeaders();
        headers.add("Responded", "add friend");

        return ResponseEntity.accepted().headers(headers).body(blogUser1);
    }
}
