package com.employeeservice.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class Account {

    private String name;
    private String password;
    @Indexed(name="email",unique = true)
    private String email;


    public Account(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }


    public Account() {
    }


    public String getName() {
        return name;
    }

    public Account setName(String name) {
        this.name = name;
        return this;
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
