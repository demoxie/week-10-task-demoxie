package com.example.blog.repository;

import com.example.blog.model.BlogUser;
import com.example.blog.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Posts,Long> {
    Posts findPostsByPosterAndPostID(BlogUser blogUser, Long postId);
    Posts findPostsByPostID(Long postId);
}
