package com.example.blog.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentID;
    private String comment;
    private Long noOfLikes = 0L;

    private LocalDateTime dateCommented = LocalDateTime.now(ZoneId.of("Africa/Lagos"));

    @ManyToOne
    private BlogUser bloguser;

    @ManyToOne
    private Posts posts;

    @OneToMany
    private Set<BlogUser> commentLikers = new HashSet<>();


}
