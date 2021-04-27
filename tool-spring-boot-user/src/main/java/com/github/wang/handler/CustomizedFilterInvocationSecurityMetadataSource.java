package com.github.wang.handler;

import com.github.wang.config.CustomizedUserProperties;
import com.github.wang.utils.regex.RegexUtil;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;
import java.util.List;

public class CustomizedFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final String err_path = ".*/error";
    private static final String webjar_path = ".*/webjar/.*";

    private CustomizedUserProperties customizedUserProperties;

    public void setCustomizedUserProperties(CustomizedUserProperties customizedUserProperties) {
        this.customizedUserProperties = customizedUserProperties;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation invocation = (FilterInvocation) o;
        String requestUrl = invocation.getRequestUrl();
        if (RegexUtil.checkContain(requestUrl, err_path) || RegexUtil.checkContain(requestUrl, webjar_path)) {
            return null;
        }
        List<String> withOutAuthentication = customizedUserProperties.getWithOutAuthentication();
        if (withOutAuthentication != null && withOutAuthentication.size() > 0) {
            for (String s : withOutAuthentication) {
                if (RegexUtil.checkContain(requestUrl, s)) {
                    return null;
                }
            }
        }
        return SecurityConfig.createList(requestUrl);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
