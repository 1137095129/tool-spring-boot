package com.github.wang.config;

import com.github.wang.handler.UserTokenWithRedisHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TokenFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    private UserTokenWithRedisHandlerAdapter handlerAdapter;

    public void setHandlerAdapter(UserTokenWithRedisHandlerAdapter handlerAdapter) {
        this.handlerAdapter = handlerAdapter;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            if (handlerAdapter.handleRequest((HttpServletRequest) servletRequest)) {
                logger.info("authentication success");
            } else {
                logger.info("authentication fail");
            }
        } finally {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
