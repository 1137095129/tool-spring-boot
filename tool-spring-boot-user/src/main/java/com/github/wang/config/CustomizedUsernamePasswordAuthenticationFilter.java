package com.github.wang.config;

import com.github.wang.core.IObtainUsernameAndPasswordHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;

public class CustomizedUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private IObtainUsernameAndPasswordHandler iObtainUsernameAndPasswordHandler;

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return iObtainUsernameAndPasswordHandler.obtainUserByRequest(request).getUsername();
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return iObtainUsernameAndPasswordHandler.obtainUserByRequest(request).getPassword();
    }

    public void setiObtainUsernameAndPasswordHandler(IObtainUsernameAndPasswordHandler iObtainUsernameAndPasswordHandler) {
        this.iObtainUsernameAndPasswordHandler = iObtainUsernameAndPasswordHandler;
    }
}
