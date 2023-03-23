package com.inn.restaurant.com.inn.restaurant.dao;

import com.inn.restaurant.com.inn.restaurant.model.User;
import com.inn.restaurant.com.inn.restaurant.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import javax.transaction.Transactional;
import java.util.List;

//dao pentru operatii pe baza de date, din Jpa find all find by id etc.
public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUser();

    List<String> getAllAdmin();
    @Transactional
    @Modifying
//ca sa mearga update si arunca exceptie
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

}
