package com.inn.restaurant.com.inn.restaurant.restImpl;

import com.inn.restaurant.com.inn.restaurant.rest.DashboardRest;
import com.inn.restaurant.com.inn.restaurant.service.DashboardService;
import com.inn.restaurant.com.inn.restaurant.serviceImpl.DashboardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardRestImpl implements DashboardRest {

    @Autowired
    DashboardService dashboardService;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        return dashboardService.getCount();
    }
}
