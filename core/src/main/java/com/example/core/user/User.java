package com.example.core.user;

import androidx.annotation.NonNull;

public class User {
    String fullname;
    String email;
    public User(){}

    public User(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
    }

    public String getFullname() {
        return this.fullname;
    }

    public String getEmail() {
        return this.email;
    }

    @NonNull
    @Override
    public String toString() {
        return "User {" +
                "fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
