package com.inn.restaurant.com.inn.restaurant.restImpl;

import com.inn.restaurant.com.inn.restaurant.constens.RestaurantConstants;
import com.inn.restaurant.com.inn.restaurant.rest.UserRest;
import com.inn.restaurant.com.inn.restaurant.service.UserService;
import com.inn.restaurant.com.inn.restaurant.utils.RestaurantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController //controloer and control body, cand porneste spring vede toate clasele de tip controler si creaza o boaba pentru acele clase
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
        return RestaurantUtils.getResponseEntity(RestaurantConstants.SOMENTHING_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);//mesaj generic folosit in multe parti
    }
}
