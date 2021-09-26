package com.example.blog.exceptions;

public class CommentNotFound extends RuntimeException{
    public CommentNotFound(String message) {
        super(message);
    }
}
