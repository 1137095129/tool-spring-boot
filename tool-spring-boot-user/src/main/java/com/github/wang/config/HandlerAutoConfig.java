package com.github.wang.config;

import com.github.wang.handler.CustomizedAccessDecisionManager;
import com.github.wang.handler.CustomizedFilterInvocationSecurityMetadataSource;
import com.github.wang.handler.UserTokenWithRedisHandlerAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultScriptExecutor;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
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
    public AccessDecisionManager customizedAccessDecisionManager() {
        return new CustomizedAccessDecisionManager();
    }

    @ConditionalOnClass({RedisTemplate.class, RedisConnectionFactory.class})
    @ConditionalOnBean(RedisConnectionFactory.class)
    @ConditionalOnMissingBean({RedisTemplate.class})
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redisTemplate.setScriptExecutor(new DefaultScriptExecutor<>(redisTemplate));
        return redisTemplate;
    }

    @ConditionalOnClass({RedisTemplate.class, RedisConnectionFactory.class})
    @ConditionalOnBean({RedisConnectionFactory.class, RedisTemplate.class})
    @Bean
    public UserTokenWithRedisHandlerAdapter userTokenWithRedisHandlerAdapter(
            RedisTemplate<String, Object> redisTemplate,
            UserLoginConfigProperties userLoginConfigProperties
    ) {
        UserTokenWithRedisHandlerAdapter handler = new UserTokenWithRedisHandlerAdapter();
        handler.setRedisTemplate(redisTemplate);
        handler.setUserLoginConfigProperties(userLoginConfigProperties);
        return handler;
    }

}
