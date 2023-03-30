package com.inn.restaurant.com.inn.restaurant.serviceImpl;

import com.inn.restaurant.com.inn.restaurant.JWT.CustomerUserDetailsService;
import com.inn.restaurant.com.inn.restaurant.dao.UserDao;
import com.inn.restaurant.com.inn.restaurant.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserDao userDao;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    CustomerUserDetailsService customerUserDetailsService;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName(
            "should return a 'Bad credentials' message when the email is incorrect but the password is correct")
    void loginWhenEmailIsIncorrectAndPasswordIsCorrect() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("email", "test@test.com");
        requestMap.put("password", "test");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        ResponseEntity<String> response = userService.login(requestMap);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"message\":\" " + "Bad crededentials." + "\"", response.getBody());
    }

    @Test
    @DisplayName(
            "should return a 'Bad credentials' message when the email is correct but the password is incorrect")
    void loginWhenEmailIsCorrectAndPasswordIsIncorrect() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("email", "test@test.com");
        requestMap.put("password", "test");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        ResponseEntity<String> response = userService.login(requestMap);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"message\":\" " + "Bad crededentials." + "\"", response.getBody());
    }

    @Test
    @DisplayName(
            "should return a 'Bad credentials' message when the email and password are incorrect")
    void loginWhenEmailAndPasswordAreIncorrect() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("email", "test@test.com");
        requestMap.put("password", "test");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        ResponseEntity<String> response = userService.login(requestMap);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"message\":\" " + "Bad crededentials." + "\"", response.getBody());
    }

    @Test
    @DisplayName(
            "should return a JWT token when the email and password are correct and the user is approved")
    void loginWhenEmailAndPasswordAreCorrectAndUserIsApproved() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("email", "test@test.com");
        requestMap.put("password", "test");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("test@test.com", "test"));
        when(customerUserDetailsService.getUserDetail()).thenReturn(new User());
        ResponseEntity<String> response = userService.login(requestMap);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("token"));
    }

    @Test
    @DisplayName(
            "should return a 'Wait for admin approval' message when the email and password are correct but the user is not approved")
    void loginWhenEmailAndPasswordAreCorrectAndUserIsNotApproved() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("email", "john.doe@example.com");
        requestMap.put("password", "123");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("john.doe@example.com", "123"));
        when(customerUserDetailsService.getUserDetail()).thenReturn(new User());
        ResponseEntity<String> response = userService.login(requestMap);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"message\":\" " + "Wait for admin approval." + "\"", response.getBody());
    }
}