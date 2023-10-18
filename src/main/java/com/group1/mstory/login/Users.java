package com.group1.mstory.login;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Users {
    
    @Id
    private Integer id;
    private String email;
    private String pass;
    private String role;
}
