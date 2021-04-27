package com.github.wang.config;

import com.github.wang.core.IObtainUserHandler;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.servlet.*;
import java.io.IOException;

public class TokenFilter implements Filter {

    private IObtainUserHandler iObtainUserHandler;

    public void setiObtainUserHandler(IObtainUserHandler iObtainUserHandler) {
        this.iObtainUserHandler = iObtainUserHandler;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            User user = iObtainUserHandler.obtainUser(servletRequest);
            if (user != null) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, user, user.getAuthorities());
                SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
                SecurityContextHolder.getContext().setAuthentication(token);
            } else {
                SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        }finally {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
