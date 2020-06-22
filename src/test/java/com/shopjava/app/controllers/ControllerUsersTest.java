
package com.shopjava.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopjava.app.models.User;
import com.shopjava.app.models.rest.ApiResponse;
import com.shopjava.app.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class ControllerUsersTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getAllUsers() throws Exception {
        var newUser = new User(
            "Alex", "Tester",
            "alex.tester@gmail.com",
            "9bba5c53"
        );
        var list = List.of(newUser, newUser);

        var response = new ApiResponse<>(true, null, list);
        String responseJson = mapper.writeValueAsString(response);

        given(service.getAllUsers()).willReturn(list);

        mvc.perform(get("/users/"))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }

    @Test
    public void getUserById() throws Exception {
        var newUser = new User(
            "Alex", "Tester",
            "alex.tester@gmail.com",
            "9bba5c53"
        );
        newUser.setId(UUID.randomUUID());

        var response = new ApiResponse<>(true, null, newUser);
        String responseJson = mapper.writeValueAsString(response);

        given(service.getUserById(newUser.getId())).willReturn(Optional.of(newUser));

        mvc.perform(get("/users/" + newUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }

    @Test
    public void createUser() throws Exception {
        var newUser = new User(
            "Alex", "Tester",
            "alex.tester@gmail.com",
            "9bba5c53"
        );
        var response = new ApiResponse<>(true, null, true);

        String content = mapper.writeValueAsString(newUser);
        String responseJson = mapper.writeValueAsString(response);

        mvc.perform(
            post("/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }

    @Test
    public void updateUser() throws Exception {
        var userId = UUID.randomUUID();
        var newUser = new User(
            "Alex", "Tester",
            "alex.tester@gmail.com",
            "9bba5c53"
        );
        var response = new ApiResponse<>(true, null, true);

        String newUserJson = mapper.writeValueAsString(newUser);
        String responseJson = mapper.writeValueAsString(response);

        given(service.updateUser(userId, newUser)).willReturn(true);

        mvc.perform(
            post("/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }

    @Test
    public void deleteUser() throws Exception {
        var userId = UUID.randomUUID();

        var response = new ApiResponse<>(true, null, true);
        String responseJson = mapper.writeValueAsString(response);

        given(service.deleteUser(userId)).willReturn(true);

        mvc.perform(delete("/users/" + userId))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }
}
