package com.example.blog.services.Impl;

import com.example.blog.model.BlogUser;
import com.example.blog.repository.BlogUserRepository;
import com.example.blog.services.BlogUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.mockito.Mockito.when;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class BlogUserServiceImplTest {
    private final MockHttpSession httpSession = new MockHttpSession();
    BlogUser blogUser;
    BlogUser user;
    BlogUser user1;
    BlogUser user2;
    @Mock
    private BlogUserService blogUserService;
    @MockBean
    private BlogUserRepository blogUserRepository;
 /*   @Autowired
    private MockMvc mockMvc;*/
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        blogUser = new BlogUser();
        blogUser.setUsername("jaga");
        blogUser.setPassword("12345");
        blogUser.setStatus("Active");
        blogUser.setFirstName("Elijah");
        blogUser.setLastName("Adamu");
        ////////
        user1 = new BlogUser();
        user2 = new BlogUser();
        user1.setFirstName("Shadrach");user2.setFirstName("Adamu");
        user1.setLastName("Hannah");user2.setLastName("Adamu");
        user1.setStatus("Active");user2.setStatus("Active");
        user1.setGender("Male");user2.setGender("Male");
        user1.setUsername("hann");user2.setUsername("moxie");
        user1.setPassword("12345");user2.setPassword("12345");

        //System.out.println(user.getUsername());
    }

   /* @Test
    void login() {
        Mockito.when(blogUserRepository.findBlogUserByUsernameAndPassword("jaga","12345")).thenReturn(blogUser);
        assertEquals(blogUser,blogUserService.login(blogUser,httpSession));
    }*/

  /*  @Test
    void register() {
        BlogUser user = blogUserService.register(blogUser);
        //System.err.println(user);
        Mockito.when(blogUserRepository.save(blogUser)).thenReturn(blogUser);
        assertEquals(blogUser,user);
    }*/

    @Test
    void logout() {
    }

    @Test
    void deactivateAccount() {

    }

 /*   @Test
    void cancelDeactivation() {
        Mockito.when(blogUserRepository.findBlogUserByUserID(1L)).thenReturn(user1);
        assertEquals(blogUserRepository.findBlogUserByUserID(1L),user1);
    }*/

    @Test
    void cancel() {
    }

    @Test
    void getBlogUser() {
    }

/*    @Test
    void getAllBlogUsers() throws Exception {
        List<BlogUser> records = new ArrayList<>(Arrays.asList(user1,user2));

        Mockito.when(blogUserRepository.findAll()).thenReturn(records);

        *//*mockMvc.perform(MockMvcRequestBuilders
                        .get("/viewAllBlogUsers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name", is("Shadrach Adamu")));*//*
        assertEquals(blogUserRepository.findAll().size(),2);
    }*/

    @Test
    void updateBlogUser() {
    }

    @Test
    void deleteBlogUser() {
    }

    @Test
    void deactivate() {
    }

    @Test
    void addUserToFriendList() {
    }
}