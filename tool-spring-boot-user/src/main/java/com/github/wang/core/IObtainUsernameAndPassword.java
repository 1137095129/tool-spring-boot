package com.github.wang.core;

import javax.servlet.http.HttpServletRequest;

public interface IObtainUsernameAndPassword {
    UsernameAndPassword obtainUserByRequest(HttpServletRequest request);
}
