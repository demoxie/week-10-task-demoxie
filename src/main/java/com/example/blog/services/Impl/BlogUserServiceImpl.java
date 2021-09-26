package com.example.blog.services.Impl;

import com.example.blog.config.Task;
import com.example.blog.model.BlogUser;
import com.example.blog.repository.BlogUserRepository;
import com.example.blog.services.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

@Service
@Component
public class BlogUserServiceImpl implements BlogUserService {
    private final BlogUserRepository blogUserRepository;
    private Thread t;
    private ScheduledExecutorService executor;
    private ScheduledFuture<String> deactivatingSchedule;

    @Autowired
    public BlogUserServiceImpl(BlogUserRepository blogUserRepository) {
        this.blogUserRepository = blogUserRepository;
    }

    @Override
    public BlogUser login(BlogUser blogUser, HttpSession httpSession) {
        BlogUser loginBlogUser = (BlogUser) httpSession.getAttribute(blogUser.getUsername());
        if (loginBlogUser != null) {
            if (loginBlogUser.getStatus().equalsIgnoreCase("Inactive")) {
                System.out.println("====>" + "Your account is deactivated");
                httpSession.removeAttribute(loginBlogUser.getUsername());
                return null;
            }
            return loginBlogUser;
        }

        BlogUser currentUser = blogUserRepository.findBlogUserByUsernameAndPassword(blogUser.getUsername(), blogUser.getPassword());
        if (currentUser.getStatus().equalsIgnoreCase("Inactive")) {
            System.out.println("Your account is deactivated");
            return null;
        }
        System.out.println(currentUser.getListOfPostsOwned());
        httpSession.setAttribute(currentUser.getUsername(), currentUser);
        return currentUser;
    }

    @Override
    public BlogUser register(BlogUser blogUser) {
        BlogUser newBlogUser = new BlogUser();
        newBlogUser.setFirstName(blogUser.getFirstName());
        newBlogUser.setLastName(blogUser.getLastName());
        newBlogUser.setGender(blogUser.getGender());
        newBlogUser.setStatus(blogUser.getStatus());
        newBlogUser.setUsername(blogUser.getUsername());
        newBlogUser.setPassword(blogUser.getPassword());
        System.out.println(newBlogUser);
        return blogUserRepository.saveAndFlush(newBlogUser);
    }

    @Override
    public String logout(HttpSession httpSession) {
        BlogUser loginBlogUser = (BlogUser) httpSession.getAttribute("BlogUser");
        if (loginBlogUser == null) {
            System.out.println("You're not logged in");
        }

        httpSession.removeAttribute("BlogUser");
        httpSession.invalidate();
        return "Logged out successfully";
    }

    @Override
    @Async
    public void deactivateAccount(Long userId, HttpSession httpSession) {
        BlogUser loginBlogUser = (BlogUser) httpSession.getAttribute(blogUserRepository.findBlogUserByUserID(userId).getUsername());
        if (loginBlogUser == null) {
            System.out.println("You're not logged in");
        }
        executor = Executors.newScheduledThreadPool(1);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime afterOneMinute = now.plusMinutes(1);

        Duration duration = Duration.between(now, afterOneMinute);
        long delay = Math.abs(duration.toMillis());

        System.out.println("Deactivation Task scheduled at : "+ LocalDateTime.now().toString().replace("T00:"," "));

        assert loginBlogUser != null;
        deactivatingSchedule = executor.schedule(new Task(loginBlogUser.getUsername(),blogUserRepository,loginBlogUser), delay, TimeUnit.MILLISECONDS);

        //future = executor.submit(new LongRunningTask(blogUserRepository,loginBlogUser));


    }

    @Override
    public String cancelDeactivation(Long userId) {
        BlogUser user = blogUserRepository.findBlogUserByUserID(userId);
        if(user != null){
            if(user.getStatus().equalsIgnoreCase("Active")){
                if(!deactivatingSchedule.isDone())
                {
                    System.out.println("====Cancelling the Deactivation====");

                    deactivatingSchedule.cancel(false);
                }

                System.out.println("Task is cancelled : " + deactivatingSchedule.isCancelled());
                executor.shutdown();
                return "Deactivation cancelled";
            }
            return "You were deactivated already";
        }
        return "Invalid user";

    }

    @Override
    public void cancel() {

    }


    @Override
    public ResponseEntity<BlogUser> getBlogUser(Long blogUserID) {
        Optional<BlogUser> blogUser = Optional.ofNullable(blogUserRepository.findBlogUserByUserID(blogUserID));
        return blogUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public List<BlogUser> getAllBlogUsers() {
        return blogUserRepository.findAll();
    }

    @Override
    public BlogUser updateBlogUser(BlogUser blogUser) {
        BlogUser currentBlogUser = blogUserRepository.findBlogUserByUserID(blogUser.getUserID());
        currentBlogUser.setFirstName(blogUser.getFirstName());
        return blogUserRepository.saveAndFlush(currentBlogUser);
    }

    @Override
    public void deleteBlogUser(Long blogUserID) {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public BlogUser addUserToFriendList(Long userId, Long friendId, HttpSession httpSession) {
        BlogUser blogUser = (BlogUser) httpSession.getAttribute(blogUserRepository.findBlogUserByUserID(userId).getUsername());
        if (blogUser == null) {
            System.out.println("Not loged in");
            return null;
        }
        BlogUser blogUser1 = blogUserRepository.findBlogUserByUserID(friendId);
        if (blogUser1.getUsername().equals(blogUser.getUsername())) {
            System.out.println("You can't add yourself as friend");
            return null;
        }
        Map<Long, String> list = blogUser.getFriendList();
        list.put(blogUser1.getUserID(), blogUser1.getFirstName() + " " + blogUser1.getLastName());
        blogUser.setFriendList(list);
        return blogUserRepository.save(blogUser);
    }
}
