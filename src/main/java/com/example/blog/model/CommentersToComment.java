package com.example.blog.model;

import javax.persistence.ElementCollection;
import java.util.List;

public class CommentersToComment {
    private Comments comments;
    @ElementCollection
    private List<BlogUser> blogUser;
}
