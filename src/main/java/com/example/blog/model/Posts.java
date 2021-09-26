package com.example.blog.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;
    private String content;
    private Long noOfLikes = 0L;

    private LocalDateTime datePosted = LocalDateTime.now(ZoneId.of("Africa/Lagos"));

    @OneToMany
    private List<Comments> comments = new LinkedList<>();

    @ManyToOne
    private BlogUser poster;


    @OneToMany
    private List<BlogUser> postLikers = new ArrayList<>();
    

}
