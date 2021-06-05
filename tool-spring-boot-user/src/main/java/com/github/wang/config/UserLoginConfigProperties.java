package com.github.wang.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties(prefix = "user-login-config")
public class UserLoginConfigProperties implements Serializable {
    private static final long serialVersionUID = -5659554291322176367L;
    private boolean signal = false;
    private boolean withEncrypt = false;
    private boolean encryptWithBcd = false;
    private boolean tokenPrefix;

    public boolean isSignal() {
        return signal;
    }

    public void setSignal(boolean signal) {
        this.signal = signal;
    }

    public boolean isWithEncrypt() {
        return withEncrypt;
    }

    public void setWithEncrypt(boolean withEncrypt) {
        this.withEncrypt = withEncrypt;
    }

    public boolean isEncryptWithBcd() {
        return encryptWithBcd;
    }

    public void setEncryptWithBcd(boolean encryptWithBcd) {
        this.encryptWithBcd = encryptWithBcd;
    }

    public boolean isTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(boolean tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }
}
