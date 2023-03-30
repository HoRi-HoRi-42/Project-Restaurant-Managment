package com.inn.restaurant.com.inn.restaurant.serviceImpl;

import com.inn.restaurant.com.inn.restaurant.JWT.JwtFilter;
import com.inn.restaurant.com.inn.restaurant.dao.CategoryDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    CategoryDao categoryDao;

    @Mock
    JwtFilter jwtFilter;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    @DisplayName("should not add a new category when the request map is invalid")
    void addNewCategoryWhenRequestMapIsInvalid() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("name", "test");
        ResponseEntity<String> responseEntity = categoryService.addNewCategory(requestMap);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("{\"mesage\":\"Something not gud\"}", responseEntity.getBody());
    }

    @Test
    @DisplayName("should add a new category when the user is an admin and the request map is valid")
    void addNewCategoryWhenUserIsAdminAndRequestMapIsValid() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("name", "test");
        when(jwtFilter.isAdmin()).thenReturn(true);
        ResponseEntity<String> responseEntity = categoryService.addNewCategory(requestMap);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{\"mesage\":\"Category addesd succ\"}", responseEntity.getBody());
    }

    @Test
    @DisplayName("should return internal server error when an exception occurs")
    void addNewCategoryWhenExceptionOccursThenReturnInternalServerError() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("name", "test");
        when(jwtFilter.isAdmin()).thenReturn(true);
        when(categoryDao.save(any())).thenThrow(new RuntimeException());
        ResponseEntity<String> responseEntity = categoryService.addNewCategory(requestMap);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("should return unauthorized access when the user is not an admin")
    void addNewCategoryWhenUserIsNotAdminThenReturnUnauthorizedAccess() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("name", "test");
        when(jwtFilter.isAdmin()).thenReturn(false);
        ResponseEntity<String> responseEntity = categoryService.addNewCategory(requestMap);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("{\"mesage\":\"Unauthorized access.\"}", responseEntity.getBody());
    }
}