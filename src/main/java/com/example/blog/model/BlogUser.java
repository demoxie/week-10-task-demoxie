package com.example.blog.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BlogUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;
    private String firstName;
    private String lastName;
    private String gender;
    private String status;
    private String username;
    private String password;
    private String profilePicsUrl;

    @OneToMany
    private List<Comments> comments = new LinkedList<>();

    @OneToMany
    private List<Posts> listOfPostsOwned = new ArrayList<>();
    @ElementCollection
    private Map<Long,String> favouritePosts = new LinkedHashMap<>();
    @ElementCollection
    private Map<Long,String> friendList = new LinkedHashMap<>();
;//


}
