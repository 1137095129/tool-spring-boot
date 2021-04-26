package com.github.wang.config;

import com.github.wang.core.IObtainUsernameAndPassword;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;

public class CustomizedUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private IObtainUsernameAndPassword iObtainUsernameAndPassword;

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return iObtainUsernameAndPassword.obtainUserByRequest(request).getUsername();
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return iObtainUsernameAndPassword.obtainUserByRequest(request).getPassword();
    }

    public void setiObtainUsernameAndPassword(IObtainUsernameAndPassword iObtainUsernameAndPassword) {
        this.iObtainUsernameAndPassword = iObtainUsernameAndPassword;
    }
}
