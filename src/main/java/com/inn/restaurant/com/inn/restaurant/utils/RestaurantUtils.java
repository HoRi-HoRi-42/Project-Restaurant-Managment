package com.inn.restaurant.com.inn.restaurant.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//generic metode folosite pentru in fiecare clasa --metode de retur ID uri unice--
public class RestaurantUtils {
    private RestaurantUtils() {

    }

    public static ResponseEntity<String> getResponseEntity(String respMesg, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"mesage\":\"" + respMesg + "\"}", httpStatus);
    }
}
