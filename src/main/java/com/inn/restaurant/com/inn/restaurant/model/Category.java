package com.inn.restaurant.com.inn.restaurant.model;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Category.getAllCategory", query = "select c from Category c where c.id in (select p.category from Product p where p.status='tru' )") //luam doar p[rodsele active
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;//pentru deserializare

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//autogenerat pentru fiecare noua valoare
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

}
