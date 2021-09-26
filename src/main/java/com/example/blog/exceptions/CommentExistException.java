package com.example.blog.exceptions;

public class CommentExistException extends RuntimeException{
    public CommentExistException(String message) {
        super(message);
    }
}
