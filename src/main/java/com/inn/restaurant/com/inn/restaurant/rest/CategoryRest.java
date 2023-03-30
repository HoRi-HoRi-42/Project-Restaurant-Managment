package com.inn.restaurant.com.inn.restaurant.rest;


import com.inn.restaurant.com.inn.restaurant.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/category") //in o clasa putem avea mai multe API-uri si sa stim la care vrem sa mergem
public interface CategoryRest {

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewCategory(@RequestBody(required = true) Map<String,String> requestMap);

    @GetMapping(path = "/get")
    ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false) String filterValue); //de ce asteptam o valoare 1) Admin ii logat si adauga un produs atunci trebuie sa fie vizibile toate categoriile astfel ii dam true la filter pentru a nu verifica produse sau nu in categoria aia
                                                                                                    // si astfel ii aratam categoriile care au cel putin un produs
    @PostMapping(path = "/update")
    ResponseEntity<String> updateCategory(@RequestBody(required = true) Map<String,String> requestMap);


}
