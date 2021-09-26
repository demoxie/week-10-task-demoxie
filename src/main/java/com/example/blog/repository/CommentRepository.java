package com.example.blog.repository;

import com.example.blog.model.BlogUser;
import com.example.blog.model.Comments;
import com.example.blog.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments,Long> {
    Comments findCommentsByBloguserAndPostsAndCommentID(BlogUser user,Posts post, Long commentId);
    Comments deleteCommentsByPostsAndCommentIDAndBloguser(Posts post, Long commentId, BlogUser user);
    Comments findCommentsByCommentID(Long commentId);
}
