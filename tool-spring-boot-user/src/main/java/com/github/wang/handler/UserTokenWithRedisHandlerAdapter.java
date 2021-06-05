package com.github.wang.handler;

import com.github.wang.config.UserLoginConfigProperties;
import com.github.wang.core.DefaultUser;
import com.github.wang.core.IEncryptGenerator;
import com.github.wang.core.IObtainUserHandler;
import com.github.wang.core.ITokenGenerator;
import com.github.wang.utils.encrypt.EncryptKeyObj;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

public class UserTokenWithRedisHandlerAdapter implements IEncryptGenerator, ITokenGenerator, IObtainUserHandler {

    public static final String authorizationRequestHeader = "Authorization";

    private static final String defaultUserTokenPrefix = "tool_spring_boot_user_token_";

    private static final String defaultEncryptTokenPrefix = "tool_spring_boot_encrypt_token_";

    protected RedisTemplate<String, Object> redisTemplate;

    protected UserLoginConfigProperties userLoginConfigProperties;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setUserLoginConfigProperties(UserLoginConfigProperties userLoginConfigProperties) {
        this.userLoginConfigProperties = userLoginConfigProperties;
    }

    /**
     * 生成token处理
     *
     * @param user
     * @return 生成的公钥字符串（经过base64编码）
     */
    public final String handleUser(DefaultUser user) {
        String loginToken = generatorLoginToken(user);
        if (userLoginConfigProperties.isWithEncrypt()) {
            EncryptKeyObj encryptKeyObj = generatorEncrypt();
            user.setPrivateKeyStr(encryptKeyObj.getPrivateKeyStr());
            user.setPublicKeyStr(encryptKeyObj.getPublicKeyStr());
        }

        return "";
    }

    /**
     * 身份认证处理
     *
     * @param request
     * @return
     */
    public final boolean handleRequest(HttpServletRequest request) {
        DefaultUser user = obtainUser(request);
        if (user != null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, user, user.getAuthorities());
            SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
            SecurityContextHolder.getContext().setAuthentication(token);
            return true;
        } else {
            SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
            SecurityContextHolder.getContext().setAuthentication(null);
            return false;
        }
    }

    @Override
    public EncryptKeyObj generatorEncrypt() {
        return null;
    }

    @Override
    public DefaultUser obtainUser(HttpServletRequest request) {
        return null;
    }

    @Override
    public String generatorLoginToken(DefaultUser user) {
        return null;
    }
}
