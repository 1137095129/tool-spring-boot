package com.github.wang.core;

/**
 * 用户token生成器
 */
public interface ITokenGenerator {
    String generatorLoginToken(DefaultUser user);
}
