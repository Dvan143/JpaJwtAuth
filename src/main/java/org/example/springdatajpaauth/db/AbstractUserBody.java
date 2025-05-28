package org.example.springdatajpaauth.db;

public class AbstractUserBody {
    String username;
    String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AbstractUserBody() {
    }
    public AbstractUserBody(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
