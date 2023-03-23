package com.inn.restaurant.com.inn.restaurant.serviceImpl;

import com.inn.restaurant.com.inn.restaurant.dao.UserDao;
import com.inn.restaurant.com.inn.restaurant.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    UserServiceImpl userService;
    @Mock
    UserDao userDao;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
        userService.userDao = userDao;
    }

    @Test
    void signUp_should_return_ok_when_input_is_valid() {
        // arrange
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("name", "John");
        requestMap.put("contactNumber", "1234567890");
        requestMap.put("email", "john@example.com");
        requestMap.put("password", "password");

        when(userDao.findByEmailId(requestMap.get("email"))).thenReturn(null);

        // act
        ResponseEntity<String> response = userService.signUp(requestMap);

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"mesage\":\"Succes\"}", response.getBody());
    }

    @Test
    void signUp_should_return_bad_request_when_email_already_exists() {
        // arrange
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("name", "John");
        requestMap.put("contactNumber", "1234567890");
        requestMap.put("email", "john@example.com");
        requestMap.put("password", "password");

        when(userDao.findByEmailId(requestMap.get("email"))).thenReturn(new User());

        // act
        ResponseEntity<String> response = userService.signUp(requestMap);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"mesage\":\"Email existent.\"}", response.getBody());
    }

    @Test
    void signUp_should_return_bad_request_when_input_is_invalid() {
        // arrange
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("name", "John");
        requestMap.put("contactNumber", "1234567890");
        requestMap.put("email", "john@example.com");

        // act
        ResponseEntity<String> response = userService.signUp(requestMap);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"mesage\":\"Invalid Data\"}", response.getBody());
    }
}
