package com.github.wang.core;

import com.github.wang.utils.encrypt.EncryptKeyObj;

/**
 * 密钥生成器
 */
public interface IEncryptGenerator {
    EncryptKeyObj generatorEncrypt();
}
