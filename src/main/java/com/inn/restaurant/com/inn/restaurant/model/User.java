package com.inn.restaurant.com.inn.restaurant.model;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")//de fiecare data cand ii dam un user ID acel id al emailullk o sa fie verificat in baza de date
                                                                                            //returneaza obiectul User
@NamedQuery(name = "User.getAllUser", query = "select new com.inn.restaurant.com.inn.restaurant.wrapper.UserWrapper(u.id,u.name,u.email,u.contactNumber,u.status) from User u where u.role='user'") //creeem objectul de clasa

@NamedQuery(name = "User.getAllAdmin", query = "select u.email from User u where u.role='admin'") //creeem objectul de clasa

@NamedQuery(name = "User.updateStatus", query = "update User u set u.status=:status where u.id=:id")

@Data
//constructor
@Entity//creaza tabelu
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
