package com.employeeservice.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Account {

    private String password;
    @Indexed(name = "email", unique = true)
    private String email;


    public Account(String password, String email) {
        this.password = password;
        this.email = email;
    }


    public Account() {
    }


    public String getPassword() {
        return password;
    }

    public Account setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Account setEmail(String email) {
        this.email = email;
        return this;
    }
}
