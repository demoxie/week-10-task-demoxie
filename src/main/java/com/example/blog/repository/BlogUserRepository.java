package com.example.blog.repository;

import com.example.blog.model.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogUserRepository extends JpaRepository<BlogUser,Long> {
    BlogUser findUserByUsernameAndPassword(String username, String password);
    BlogUser findBlogUserByUserID(Long blogUserID);
    BlogUser findBlogUserByUsernameAndPassword(String username, String password);
    //BlogUser merge(BlogUser blogUser);
    //BlogUser update(BlogUser blogUser);
}
