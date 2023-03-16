package com.inn.restaurant.com.inn.restaurant.dao;

import com.inn.restaurant.com.inn.restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

//dao pentru operatii pe baza de date, din Jpa find all find by id etc.
public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmailId(@Param("email") String email);

}
