package com.github.wang.core.user;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface WebUserHandler {
    void loginSuccess(IUserInfo userInfo, ServletRequest request, ServletResponse response);

    void loginFail(IUserInfo userInfo, ServletRequest request, ServletResponse response);

    void logoutSuccess(IUserInfo userInfo, ServletRequest request, ServletResponse response);

    void logoutFail(IUserInfo userInfo, ServletRequest request, ServletResponse response);
}
