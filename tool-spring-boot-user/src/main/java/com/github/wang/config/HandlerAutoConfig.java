package com.github.wang.config;

import com.github.wang.config.CustomizedUserProperties;
import com.github.wang.handler.CustomizedAccessDecisionManager;
import com.github.wang.handler.CustomizedFilterInvocationSecurityMetadataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

@Configuration
public class HandlerAutoConfig {

    @ConditionalOnMissingBean(FilterInvocationSecurityMetadataSource.class)
    @Bean
    public FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource(CustomizedUserProperties properties) {
        CustomizedFilterInvocationSecurityMetadataSource metadataSource = new CustomizedFilterInvocationSecurityMetadataSource();
        metadataSource.setCustomizedUserProperties(properties);
        return metadataSource;
    }

    @ConditionalOnMissingBean(AccessDecisionManager.class)
    @Bean
    public AccessDecisionManager customizedAccessDecisionManager(){
        return new CustomizedAccessDecisionManager();
    }

}
