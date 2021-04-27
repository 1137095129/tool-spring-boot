package com.github.wang.config;

import com.github.wang.core.IObtainUserHandler;
import com.github.wang.core.IObtainUsernameAndPasswordHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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

    /**
     * 用户登录配置
     */
    private CustomizedUserProperties customizedUserProperties;

    /**
     * 重写spring security的登录时获取用户名和密码的策略
     */
    private IObtainUsernameAndPasswordHandler obtainUsernameAndPassword;

    /**
     * 用户登录失败的处理器
     */
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 用户登录成功的处理器
     */
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * 从web request中解析用户信息
     */
    private IObtainUserHandler iObtainUserHandler;

    private CustomizedUsernamePasswordAuthenticationFilter customizedUsernamePasswordAuthenticationFilter() throws Exception {
        CustomizedUsernamePasswordAuthenticationFilter filter = new CustomizedUsernamePasswordAuthenticationFilter();
        //自定义处理获取用户名密码的处理器
        filter.setiObtainUsernameAndPasswordHandler(obtainUsernameAndPassword);
        //身份认证处理器
        filter.setAuthenticationManager(authenticationManager());
        //登陆失败
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        //登陆成功
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        //设置登陆路径
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(customizedUserProperties.getSignIn().getLoginUrl(), HttpMethod.POST.name()));
        //禁用session
        filter.setAllowSessionCreation(false);
        filter.afterPropertiesSet();
        return filter;
    }

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
                .logoutUrl(customizedUserProperties.getSignIn().getLogoutUrl())
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);

        //替换原本的用户登录逻辑
        http.addFilterAt(customizedUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        TokenFilter filter = new TokenFilter();
        filter.setiObtainUserHandler(iObtainUserHandler);
        /**
         * 放在默认的加载用户过滤器后
         */
        http.addFilterAfter(filter, AnonymousAuthenticationFilter.class);
    }

    @Autowired
    public void setFilterInvocationSecurityMetadataSource(FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
        this.filterInvocationSecurityMetadataSource = filterInvocationSecurityMetadataSource;
    }

    @Autowired
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        this.accessDecisionManager = accessDecisionManager;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setLogoutSuccessHandler(LogoutSuccessHandler logoutSuccessHandler) {
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Autowired
    public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Autowired
    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Autowired
    public void setLoginProperties(CustomizedUserProperties customizedUserProperties) {
        this.customizedUserProperties = customizedUserProperties;
    }

    @Autowired
    public void setObtainUsernameAndPassword(IObtainUsernameAndPasswordHandler obtainUsernameAndPassword) {
        this.obtainUsernameAndPassword = obtainUsernameAndPassword;
    }

    @Autowired
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Autowired
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Autowired
    public void setiObtainUserHandler(IObtainUserHandler iObtainUserHandler) {
        this.iObtainUserHandler = iObtainUserHandler;
    }
}
