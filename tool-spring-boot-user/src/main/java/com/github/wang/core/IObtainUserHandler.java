package com.github.wang.core;

import javax.servlet.http.HttpServletRequest;

/**
 * 通过request获取用户信息的处理器
 */
public interface IObtainUserHandler {
    DefaultUser obtainUser(HttpServletRequest request);
}
