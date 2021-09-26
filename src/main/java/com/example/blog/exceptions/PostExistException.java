package com.example.blog.exceptions;

public class PostExistException extends RuntimeException{
    public PostExistException(String message) {
        super(message);
    }
}
