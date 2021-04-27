package com.github.wang.core;

import javax.servlet.http.HttpServletRequest;

public interface IObtainUsernameAndPasswordHandler {
    UsernameAndPassword obtainUserByRequest(HttpServletRequest request);
}
