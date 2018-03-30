package com.example.rajat.projectm;

/**
 * Created by Nishant on 05-11-2016.
 */

public class User {
    public String name;
    public String email;
    public String type;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String email, String type) {
        this.name = name;
        this.email = email;
        this.type = type;
    }
}
