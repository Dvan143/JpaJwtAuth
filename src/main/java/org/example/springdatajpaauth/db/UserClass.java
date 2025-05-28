package org.example.springdatajpaauth.db;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String username;
    @Column
    String password;
    @Column
    String role;

    public UserClass(){

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public UserClass(String username, String password){
        this.username = username;
        this.password = password;
        this.role = "user";
    }
    public UserClass(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
