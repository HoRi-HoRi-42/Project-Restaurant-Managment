package com.inn.restaurant.com.inn.restaurant.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


public interface DashboardService {
    ResponseEntity<Map<String, Object>> getCount();
}
