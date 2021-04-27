package com.github.wang.handler;

import com.github.wang.core.DefaultUser;
import com.github.wang.core.user.IUserInfo;
import com.github.wang.core.user.WebUserHandler;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomizedWebUserHandler implements WebUserHandler {



    @Override
    public void loginSuccess(IUserInfo userInfo, ServletRequest request, ServletResponse response) {
        DefaultUser user = (DefaultUser) userInfo;

    }

    @Override
    public void loginFail(IUserInfo userInfo, ServletRequest request, ServletResponse response) {

    }

    @Override
    public void logoutSuccess(IUserInfo userInfo, ServletRequest request, ServletResponse response) {

    }

    @Override
    public void logoutFail(IUserInfo userInfo, ServletRequest request, ServletResponse response) {

    }
}
