package com.inn.restaurant.com.inn.restaurant.serviceImpl;

import com.inn.restaurant.com.inn.restaurant.JWT.CustomerUserDetailsService;
import com.inn.restaurant.com.inn.restaurant.JWT.JwtUtil;
import com.inn.restaurant.com.inn.restaurant.constens.RestaurantConstants;
import com.inn.restaurant.com.inn.restaurant.dao.UserDao;
import com.inn.restaurant.com.inn.restaurant.model.User;
import com.inn.restaurant.com.inn.restaurant.service.UserService;
import com.inn.restaurant.com.inn.restaurant.utils.RestaurantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j //pentru logare
//bisness logic
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside SignUp{}", requestMap);
        try {
            if (validateSignUpMapO(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    //nu putem requestul map direct ci ne treber un obiect de tip user din care extragem valoare din acel map
                    userDao.save(getUserFromMap(requestMap));
                    return RestaurantUtils.getResponseEntity("Succes", HttpStatus.OK);
                } else {
                    return RestaurantUtils.getResponseEntity("Email existent.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return RestaurantUtils.getResponseEntity(RestaurantConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.SOMENTHING_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateSignUpMapO(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") && requestMap.containsKey("password"))
            return true;
        else return false;
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
        if (auth.isAuthenticated()){
            if(customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                //user approved de catre admin
                return new ResponseEntity<String>("{\"token\":\"" + jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
                        customerUserDetailsService.getUserDetail().getRole()) + "\"}",HttpStatus.OK);
            }
            else {
                return new ResponseEntity<String>("{\"message\":\" "+ "Wait for admin approval."+"\"", HttpStatus.BAD_REQUEST );
            }
        }

        } catch (Exception exception) {
            log.error("{}", exception);
        }
        return new ResponseEntity<String>("{\"message\":\" "+ "Bad crededentials."+"\"", HttpStatus.BAD_REQUEST );
    }
}
