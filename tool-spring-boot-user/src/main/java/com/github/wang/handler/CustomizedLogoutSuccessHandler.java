package com.github.wang.handler;

import com.github.wang.core.IExecuteSuccessHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomizedLogoutSuccessHandler implements LogoutSuccessHandler, IExecuteSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

    }

    @Override
    public void executeSuccessHandle() {

    }
}
