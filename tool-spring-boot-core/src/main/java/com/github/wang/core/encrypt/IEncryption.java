package com.github.wang.core.encrypt;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

public interface IEncryption {

    KeyPair createKeyPair(String algorithm, int keySize) throws NoSuchAlgorithmException;

    /**
     * 加密
     *
     * @param targetValue 要加密的目标字符串
     * @param key         加密密钥
     * @param keySize
     * @return
     */
    byte[] encrypt(String targetValue, String key, int keySize, boolean isPrivateKey) throws Exception;

    byte[] encrypt(String targetValue, String key, int keySize, boolean isPrivateKey, boolean toBcd) throws Exception;

    /**
     * 解密
     *
     * @param targetValue 要解密的目标字符串
     * @param key         解密密钥
     * @param keySize
     * @return
     */
    byte[] decrypt(String targetValue, String key, int keySize, boolean isPrivateKey) throws Exception;

    byte[] decrypt(String targetValue, String key, int keySize, boolean isPrivateKey, boolean fromBcd) throws Exception;

    /**
     * 根据私钥对目标字符串生成签名
     *
     * @param targetValue 要签名的目标字符串
     * @param privateKey  签名使用的私钥字符串
     * @return
     */
    String sign(String targetValue, String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException;

    /**
     * 验证签名是否正确
     *
     * @param targetValue 要签证的目标字符串
     * @param publicKey   验证使用的公钥字符串
     * @param sign        签名字符串
     * @return
     */
    boolean verify(String targetValue, String publicKey, String sign) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException;
}
