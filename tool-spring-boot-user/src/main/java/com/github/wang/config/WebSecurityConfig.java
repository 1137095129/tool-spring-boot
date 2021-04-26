package com.github.wang.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@Component
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 安全元数据源
     */
    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    /**
     * 访问决策管理器
     */
    private AccessDecisionManager accessDecisionManager;

    /**
     * 用户信息加载器
     */
    private UserDetailsService userDetailsService;

    /**
     * 登录成功处理器
     */
    private LogoutSuccessHandler logoutSuccessHandler;

    /**
     * 权限被拒绝时
     */
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * 匿名用户访问无权限资源时
     */
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .disable()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .oauth2Login()
                .disable();
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        //安全数据源
                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
                        //访问决策管理器
                        o.setAccessDecisionManager(accessDecisionManager);
                        o.afterPropertiesSet();
                        return o;
                    }
                })
                .and()
                .userDetailsService(userDetailsService)
                .logout()
                .permitAll()
                .logoutUrl("")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);
    }
}
