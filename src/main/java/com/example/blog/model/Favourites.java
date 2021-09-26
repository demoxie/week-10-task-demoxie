package com.example.blog.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Favourites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favouriteID;
    private String favouriteName;
    private LocalDateTime dateCreated;

    @OneToMany
    private Set<Posts> posts = new HashSet<>();
}
