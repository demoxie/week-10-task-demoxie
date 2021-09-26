package com.example.blog.config;

import com.example.blog.model.BlogUser;
import com.example.blog.repository.BlogUserRepository;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

public class Task implements Callable<String>
{
    private final String name;
    private final BlogUserRepository blogUserRepository;
    private final BlogUser loginBlogUser;

    public Task(String name, BlogUserRepository blogUserRepository, BlogUser blogUser) {
        this.name = name;
        this.blogUserRepository = blogUserRepository;
        this.loginBlogUser = blogUser;
    }

    @Override
    public String call() throws Exception {
        assert loginBlogUser != null;
        loginBlogUser.setStatus("Inactive");
        blogUserRepository.save(loginBlogUser);
        System.out.println("Task [" + name + "] executed on : " + LocalDateTime.now().toString().replace("T00:"," "));
        return "Task [" + name + "] is SUCCESS !!";
    }

}
