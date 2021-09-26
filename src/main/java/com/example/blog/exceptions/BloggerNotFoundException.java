package com.example.blog.exceptions;

public class BloggerNotFoundException extends RuntimeException {
    public BloggerNotFoundException(String message) {
        super(message);
    }
}
