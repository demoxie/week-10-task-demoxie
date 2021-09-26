package com.example.blog.exceptions;

public class BloggerExistException extends RuntimeException{
    public BloggerExistException(String message) {
        super(message);
    }
}
