package com.inn.restaurant.com.inn.restaurant.dao;

import com.inn.restaurant.com.inn.restaurant.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category,Integer> {
    //ca sa luam data si sa luam toat
    List<Category> getAllCategory();
}
