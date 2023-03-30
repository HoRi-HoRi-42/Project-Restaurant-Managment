package com.inn.restaurant.com.inn.restaurant.serviceImpl;

import com.inn.restaurant.com.inn.restaurant.JWT.JwtFilter;
import com.inn.restaurant.com.inn.restaurant.dao.ProductDao;
import com.inn.restaurant.com.inn.restaurant.dao.UserDao;
import com.inn.restaurant.com.inn.restaurant.model.Product;
import com.inn.restaurant.com.inn.restaurant.model.User;
import com.inn.restaurant.com.inn.restaurant.restImpl.UserRestImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    JwtFilter jwtFilter;

    @Mock
    ProductDao productDao;

    @InjectMocks
    ProductServiceImpl productService;

//    @Test
//    @DisplayName(
//            "should return a bad request response when the user is an admin and the request map is invalid")
//    void addNewProductWhenUserIsAdminAndRequestMapIsInvalid() {
//        Map<String, String> requestMap = new HashMap<>();
//        requestMap.put("name", "test");
//        requestMap.put("categoryId", "1");
//        requestMap.put("description", "test");
//        requestMap.put("price", "1");
//        when(jwtFilter.isAdmin()).thenReturn(false);
//        ResponseEntity<String> responseEntity = productService.addNewProduct(requestMap);
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        assertEquals("{\"mesage\":\"Invalid Data\"}", responseEntity.getBody());
//    }

    @Test
    @DisplayName("should return an unauthorized response when the user is not an admin")
    void addNewProductWhenUserIsNotAdmin() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("name", "test");
        requestMap.put("description", "test");
        requestMap.put("price", "10");
        requestMap.put("categoryId", "1");
        when(jwtFilter.isAdmin()).thenReturn(false);
        ResponseEntity<String> responseEntity = productService.addNewProduct(requestMap);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("{\"mesage\":\"Unauthorized access.\"}", responseEntity.getBody());
    }

    @Test
    @DisplayName("should add a new product when the user is an admin and the request map is valid")
    void addNewProductWhenUserIsAdminAndRequestMapIsValid() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", "1");
        requestMap.put("name", "test");
        requestMap.put("description", "test");
        requestMap.put("price", "1");
        requestMap.put("categoryId", "1");
        when(jwtFilter.isAdmin()).thenReturn(true);
        ResponseEntity<String> responseEntity = productService.addNewProduct(requestMap);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("{\"mesage\":\"Product added Succ\"}", responseEntity.getBody());
    }


  //  @Test
   // @DisplayName("should return an internal server error response when an exception occurs")
//    void addNewProductWhenExceptionOccurs() {
//        Map<String, String> requestMap = new HashMap<>();
//        requestMap.put("name", "test");
//        requestMap.put("description", "test");
//        requestMap.put("price", "10");
//        requestMap.put("categoryId", "1");
//        when(jwtFilter.isAdmin()).thenReturn(true);
//        when(productService.validateProductMap(requestMap, false)).thenReturn(true);
//        when(productService.getProductFromMap(requestMap, false)).thenReturn(new Product());
//        when(productDao.save(any())).thenThrow(new RuntimeException());
//        ResponseEntity<String> responseEntity = productService.addNewProduct(requestMap);
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
//    }
}