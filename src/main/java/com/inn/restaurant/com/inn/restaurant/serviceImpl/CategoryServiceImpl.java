package com.inn.restaurant.com.inn.restaurant.serviceImpl;

import com.google.common.base.Strings;
import com.inn.restaurant.com.inn.restaurant.JWT.JwtFilter;
import com.inn.restaurant.com.inn.restaurant.constens.RestaurantConstants;
import com.inn.restaurant.com.inn.restaurant.dao.CategoryDao;
import com.inn.restaurant.com.inn.restaurant.model.Category;
import com.inn.restaurant.com.inn.restaurant.service.CategoryService;
import com.inn.restaurant.com.inn.restaurant.utils.RestaurantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j//pentru loguri
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryMap(requestMap, false)) {
                    categoryDao.save(getCategoryFromMao(requestMap, false));
                    return RestaurantUtils.getResponseEntity("Category addesd succ", HttpStatus.OK);
                }
            } else {
                return RestaurantUtils.getResponseEntity(RestaurantConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.SOMENTHING_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        //o sa folosim mai sus si in uppdate ca acolo avem nevoie si de id si de nume si de ce trimitem o booleana, sa stim ce vrem sa validam
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMao(Map<String, String> requestMap, Boolean isAdd) {
        Category category = new Category();
        if (isAdd) // vrem sa setem idu
        {
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {//avem nevoie doar categoriile care au cel putin un produs in ele
                log.info("Inside witf filterValue");
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryMap(requestMap, true)) {//trebuie validat si id-ul am implementat mai sus deoarece noui nu vrem sa avem un 1d 5000 care sa nu exista
                    Optional optional = categoryDao.findById(Integer.parseInt(requestMap.get("id"))); // o sa returneze un obiect de tip optional
                    if (!optional.isEmpty()) {
                        categoryDao.save(getCategoryFromMao(requestMap, true));//daca ii true o sa extragem si id-ul
                        return RestaurantUtils.getResponseEntity("Category updated succ", HttpStatus.OK);
                    } else {
                        return RestaurantUtils.getResponseEntity("Category id does not exist", HttpStatus.OK);
                    }
                }
            }
            return RestaurantUtils.getResponseEntity(RestaurantConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestaurantUtils.getResponseEntity(RestaurantConstants.SOMENTHING_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
