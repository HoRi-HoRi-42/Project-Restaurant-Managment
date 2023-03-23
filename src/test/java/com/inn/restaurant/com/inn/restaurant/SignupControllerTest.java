package com.inn.restaurant.com.inn.restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inn.restaurant.com.inn.restaurant.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertThat;


import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(classes = RestaurantManagmentApplication.class)

public class SignupControllerTest {

    private final String SIGNUP_ENDPOINT = "/user/signup";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;



    @BeforeEach
    void setup() {
        user = new User();
        user.setName("testuser");
        user.setContactNumber("0770439653");
        user.setEmail("ter@example.com");
        user.setPassword("testpassword");
        initMocks(this);
    }

    @Test
    public void testSignupEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(SIGNUP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        User createdUser = objectMapper.readValue(responseBody, User.class);

        //assertEquals(createdUser.getName(), user.getName());
        assertEquals(createdUser.getEmail(), user.getEmail());
    }
}
