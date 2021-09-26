package com.example.blog.services.Impl;

import com.example.blog.model.BlogUser;
import com.example.blog.model.Comments;
import com.example.blog.model.Posts;
import com.example.blog.repository.BlogUserRepository;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.services.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentServices {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final BlogUserRepository blogUserRepository;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,BlogUserRepository blogUserRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.blogUserRepository = blogUserRepository;
    }

    @Override
    public Comments doComment(Comments comments, HttpSession httpSession) {
        BlogUser user = (BlogUser) httpSession.getAttribute(blogUserRepository.
                findBlogUserByUserID(comments.getBloguser().getUserID()).getUsername());
        if(user == null){
            System.out.println("Login to comment");
            return null;
        }
        System.out.println(comments.getPosts().getPostID());
        Optional<Posts> postsOptional = postRepository.findById(comments.getPosts().getPostID());
        Posts posts = new Posts();
        if(postsOptional.isPresent()) {
            posts = postsOptional.get();
            System.out.println(posts);
        }
        System.out.println(posts);
        comments.setPosts(posts);
        comments.setBloguser(user);
        return commentRepository.saveAndFlush(comments);
    }

    @Override
    public void deleteComment(Long postId,Long commentId,HttpSession httpSession) {
        Comments comments = commentRepository.findCommentsByCommentID(commentId);
        BlogUser user = (BlogUser) httpSession.getAttribute(comments.getBloguser().getUsername());

        if(user == null){
            System.out.println("you can delete this comment");
        }
        Posts post = new Posts();
        Optional<Posts> posts = postRepository.findById(postId);
        if(posts.isPresent()){
            post = posts.get();
        }
        commentRepository.delete(commentRepository.findCommentsByBloguserAndPostsAndCommentID(user,post,commentId));

    }

    @Override
    public Comments editComment(Long postId, Long commentId,Comments comment, HttpSession httpSession) {
        BlogUser loggedInUser = (BlogUser) httpSession.getAttribute(comment.getBloguser().getUsername());

        if(loggedInUser == null){
            System.out.println("its null");
        }
        Posts post = new Posts();
        Optional<Posts> posts = postRepository.findById(postId);
        if(posts.isPresent()){
            post = posts.get();
        }
        Comments comments = commentRepository.findCommentsByBloguserAndPostsAndCommentID(loggedInUser,post,commentId);
        comments.setComment(comment.getComment());
        return commentRepository.save(comments);
    }
    @Override
    public Comments likeComment(Long userId,Long postId,Long commentId, HttpSession httpSession) {
        Posts post = postRepository.findPostsByPostID(postId);
        BlogUser loggedInUser = (BlogUser) httpSession.getAttribute(blogUserRepository.findBlogUserByUserID(userId).getUsername());
        if(loggedInUser == null){
            System.out.println("login to like this post");
            return null;
        }
        Comments comment = commentRepository.findCommentsByBloguserAndPostsAndCommentID(loggedInUser,post,commentId);
        Set<BlogUser> commentLikers = comment.getCommentLikers();
        Optional<BlogUser> likeUser = commentLikers.stream().filter(userUser -> Objects.equals(userUser.getUserID(), loggedInUser.getUserID())).findAny();
        if(likeUser.isEmpty()){
            comment.setNoOfLikes(comment.getNoOfLikes()+1);
            comment.getCommentLikers().add(loggedInUser);
            return commentRepository.save(comment);
        }else{
            if(comment.getNoOfLikes()>0){
                System.out.println("entereturn postRepository.save(post);");
                commentLikers.remove(likeUser.get());
                comment.setCommentLikers(commentLikers);
                comment.setNoOfLikes(comment.getNoOfLikes() - 1);
                return commentRepository.save(comment);
            }
            System.out.println("ok not now");
            return null;
        }
    }
}
