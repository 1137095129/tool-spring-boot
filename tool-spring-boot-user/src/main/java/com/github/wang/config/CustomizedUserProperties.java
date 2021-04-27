package com.github.wang.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "log-config")
public class CustomizedUserProperties implements Serializable {
    private static final long serialVersionUID = 9153393047189907082L;
    /**
     * 登录、登出
     */
    private SignInProperties signIn;

    /**
     * 无身份认证的接口
     */
    private List<String> withOutAuthentication;

    public CustomizedUserProperties() {
        this.signIn = new SignInProperties();
        this.withOutAuthentication = new ArrayList<>();
    }

    public SignInProperties getSignIn() {
        return signIn;
    }

    public void setSignIn(SignInProperties signIn) {
        this.signIn = signIn;
    }

    public List<String> getWithOutAuthentication() {
        return withOutAuthentication;
    }

    public void setWithOutAuthentication(List<String> withOutAuthentication) {
        this.withOutAuthentication = withOutAuthentication;
    }

    public static class SignInProperties implements Serializable {
        private static final long serialVersionUID = -74552248376022893L;
        /**
         * 登陆接口请求路径
         */
        private String loginUrl;

        /**
         * 登出请求接口
         */
        private String logoutUrl;

        public SignInProperties() {
            this.loginUrl = "/login";
            this.logoutUrl = "/logout";
        }

        public String getLoginUrl() {
            return loginUrl;
        }

        public void setLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
        }

        public String getLogoutUrl() {
            return logoutUrl;
        }

        public void setLogoutUrl(String logoutUrl) {
            this.logoutUrl = logoutUrl;
        }
    }
}
