package com.inn.restaurant.com.inn.restaurant.model;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")//de fiecare data cand ii dam un user ID acel id al emailullk o sa fie verificat in baza de date
                                                                                            //returneaza obiectul User

@Data
//constructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
//cum se va numi in baza de date
public class User implements Serializable {//obiectele sunt convertite in streamuri care sunt trimise prin retea

    private static final long serialVersionUID = 1L;//pentru deserializare

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//autogenerat pentru fiecare noua valoare
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;


}
