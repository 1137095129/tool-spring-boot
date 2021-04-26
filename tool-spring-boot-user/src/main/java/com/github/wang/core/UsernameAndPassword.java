package com.github.wang.core;

import java.io.Serializable;

public class UsernameAndPassword implements Serializable {
    private static final long serialVersionUID = 7875911984117292150L;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
