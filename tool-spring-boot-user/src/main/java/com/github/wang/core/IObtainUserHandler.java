package com.github.wang.core;

import org.springframework.security.core.userdetails.User;

import javax.servlet.ServletRequest;

public interface IObtainUserHandler {
    User obtainUser(ServletRequest request);
}
