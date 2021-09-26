package com.example.blog.services.Impl;

import com.example.blog.model.BlogUser;
//import com.example.blog.model.PostLikers;
//import com.example.blog.model.PostLiker;
import com.example.blog.model.Favourites;
import com.example.blog.model.Posts;
//import com.example.blog.repository.PostLikersRepository;
//import com.example.blog.repository.PostLikerRepository;
import com.example.blog.repository.BlogUserRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.services.PostService;
import com.sun.istack.NotNull;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    //private final PostLikerRepository postLikerRepository;
    private final BlogUserRepository blogUserRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,BlogUserRepository blogUserRepository) {

        this.postRepository = postRepository;
        this.blogUserRepository = blogUserRepository;
        //this.postLikerRepository = postLikerRepository;

    }

    @Override
    public Posts createPost(Posts posts, HttpSession httpSession) {
        BlogUser loginUser = (BlogUser) httpSession.getAttribute(posts.getPoster().getUsername());

        if(loginUser == null ){
            return null;
        }

        posts.setPoster(loginUser);
        loginUser.getListOfPostsOwned().add(posts);
        return postRepository.save(posts);
    }

    @Override
    public String deletePost(Long postId, HttpSession httpSession) {
        BlogUser loginUser = (BlogUser) httpSession.getAttribute("BlogUser");
        String response = "";
        if(loginUser != null ){
            Long id = loginUser.getUserID();
            Optional<Posts> posts = Optional.ofNullable(postRepository.findPostsByPosterAndPostID(loginUser, postId));
            if(posts.isPresent()){
                Posts savedPost = posts.get();
                System.out.println(savedPost.getContent());
                postRepository.delete(savedPost);
                response = "deleted successfully";
                return response;
            }
        }
        return null;

    }

    @Override
    public Posts editPost(Long postId, Posts posts, HttpSession httpSession) {
        BlogUser loginUser = (BlogUser) httpSession.getAttribute("BlogUser");
        if(loginUser == null ){
            return null;
        }else
        return postRepository.findById(postId)
                .map(post -> {
                    post.setContent(posts.getContent());
                    return postRepository.save(post);
                }).orElseThrow(() -> new ResourceNotFoundException("Post not found " + postId));
    }

    @Override
    public Posts likePost(Long userId,Long postId, HttpSession httpSession) {
        Posts post = postRepository.findPostsByPostID(postId);
        BlogUser loggedInUser = (BlogUser) httpSession.getAttribute(blogUserRepository.findBlogUserByUserID(userId).getUsername());
        if(loggedInUser == null){
            System.out.println("login to like this post");
            return null;
        }
        List<BlogUser> postLikers = post.getPostLikers();
        System.out.println("<--------->"+postLikers.toString());
        Optional<BlogUser> likeUser = postLikers.stream().filter(userUser -> Objects.equals(userUser.getUserID(), loggedInUser.getUserID())).findAny();
        if(likeUser.isEmpty()){
            post.setNoOfLikes(post.getNoOfLikes()+1);
            post.getPostLikers().add(loggedInUser);
            return postRepository.save(post);
        }else{
            if(post.getNoOfLikes()>0){
                System.out.println("entereturn postRepository.save(post);");
                postLikers.remove(likeUser.get());
                post.setPostLikers(postLikers);
                post.setNoOfLikes(post.getNoOfLikes() - 1);
                return postRepository.saveAndFlush(post);
            }
            System.out.println("ok not now");
            return null;
        }
    }

    @Override
    public Map<Long,String> addPostToFavourites(Long userId, Long postId, HttpSession httpSession) {
        BlogUser blogUser = (BlogUser) httpSession.getAttribute(blogUserRepository.findBlogUserByUserID(userId).getUsername());
        if(blogUser == null){
            System.out.println("Not loged in");
            return null;
        }
        Posts post = postRepository.findPostsByPostID(postId);
        blogUser.getFavouritePosts().put(post.getPostID(),post.getContent());
        blogUserRepository.save(blogUser);
        return blogUser.getFavouritePosts();
    }

}
