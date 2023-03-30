package com.inn.restaurant.com.inn.restaurant.restImpl;

import com.inn.restaurant.com.inn.restaurant.constens.RestaurantConstants;
import com.inn.restaurant.com.inn.restaurant.rest.UserRest;
import com.inn.restaurant.com.inn.restaurant.service.UserService;
import com.inn.restaurant.com.inn.restaurant.utils.RestaurantUtils;
import com.inn.restaurant.com.inn.restaurant.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
//controloer and control body, cand porneste spring vede toate clasele de tip controler si creaza o boaba pentru acele clase
//apri returneaza mai multe, dar acum returneaza valorile non-HTML
public class UserRestImpl implements UserRest {
    //implement the abstraction

    @Autowired // face boaba acestuia, nu putem face bean la interfata
    UserService userService;

    @Override
    public ResponseEntity<String> singUp(Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //cu respont entity trimitem si ce http eroare avemsi nu doar string
        return RestaurantUtils.getResponseEntity(RestaurantConstants.SOMENTHING_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);//mesaj generic folosit in multe parti
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.SOMENTHING_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);//mesaj generic folosit in multe parti
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            return userService.getAllUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            return userService.update(requestMap);
        } catch (Exception ex) {
            return RestaurantUtils.getResponseEntity(RestaurantConstants.SOMENTHING_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try {
            return userService.checkToken();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.SOMENTHING_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            return userService.changePassword(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.SOMENTHING_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            return userService.forgotPassword(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.SOMENTHING_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);    }

}
