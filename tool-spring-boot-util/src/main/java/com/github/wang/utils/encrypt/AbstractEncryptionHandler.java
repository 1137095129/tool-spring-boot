package com.github.wang.utils.encrypt;

import com.github.wang.core.encrypt.IEncryption;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public abstract class AbstractEncryptionHandler implements IEncryption {

    private static final int MAX_ENCRYPT_LEN = 117;
    private static final int MAX_DECRYPT_LEN = 128;

    /**
     * 根据已Base64编码的私钥字符串获取私钥对象
     *
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    protected abstract PrivateKey getPrivateKeyByBaseEncodeKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException;

    /**
     * 根据已Base64编码的公钥字符串获取公钥对象
     *
     * @param publicKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    protected abstract PublicKey getPublicKeyByBaseEncodeKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException;

    public abstract KeyPair createKeyPair(int keySize) throws NoSuchAlgorithmException;

    @Override
    public KeyPair createKeyPair(String algorithm, int keySize) throws NoSuchAlgorithmException {
        //构建一个安全的随机数源
        SecureRandom random = new SecureRandom();
        //根据传入的算法名，开始构建KeyPairGenerator对象
        KeyPairGenerator instance = KeyPairGenerator.getInstance(algorithm);
        instance.initialize(keySize, random);
        return instance.generateKeyPair();
    }

    @Override
    public byte[] encrypt(String targetValue, String key, int keySize, boolean isPrivateKey) throws Exception {
        if (isPrivateKey) {
            return encrypt(targetValue, getPrivateKeyByBaseEncodeKey(key), keySize, true);
        }
        return encrypt(targetValue, getPublicKeyByBaseEncodeKey(key), keySize, true);
    }

    /**
     * 加密字符串
     *
     * @param targetValue 加密的目标字符串
     * @param key         加密的密钥字符串（经过Base64编码）
     * @param keySize
     * @param toBcd       是否转成bcd码
     * @return
     * @throws Exception
     */
    protected byte[] encrypt(String targetValue, Key key, int keySize, boolean toBcd) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        int MaxBlockSize = keySize / 8;
        String[] datas = splitString(targetValue, MaxBlockSize - 11);
        StringBuilder mi = new StringBuilder();
        for (String s : datas) {
            if (toBcd) {
                mi.append(BCD_To_STR(cipher.doFinal(s.getBytes())));
            } else {
                mi.append(new String(cipher.doFinal(s.getBytes()), StandardCharsets.UTF_8));
            }
        }
        return mi.toString().getBytes();
    }

    @Override
    public byte[] decrypt(String targetValue, String key, int keySize, boolean isPrivateKey) throws Exception {
        targetValue = BaseEncryptionUtil.base64DecodeToString(targetValue);
        if (isPrivateKey) {
            return decrypt(targetValue, getPrivateKeyByBaseEncodeKey(key), keySize, true);
        }
        return decrypt(targetValue, getPublicKeyByBaseEncodeKey(key), keySize, true);
    }

    /**
     * 解密算法
     * @param targetValue
     * @param key
     * @param keySize
     * @param fromBcd
     * @return
     * @throws Exception
     */
    protected byte[] decrypt(String targetValue, Key key, int keySize, boolean fromBcd) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        int key_len = keySize / 8;
        byte[] bytes = targetValue.getBytes();
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        StringBuilder ming = new StringBuilder();
        byte[][] arrays = fromBcd ? splitArray(bcd, key_len) : splitArray(bytes, key_len);
        for (byte[] arr : arrays) {
            ming.append(new String(cipher.doFinal(arr)));
        }
        return ming.toString().getBytes();
    }

    @Override
    public String sign(String targetValue, String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        return null;
    }

    @Override
    public boolean verify(String targetValue, String publicKey, String sign) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        return false;
    }

    /**
     * 切割字符串
     *
     * @param string
     * @param len
     * @return
     */
    private String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    private byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    /**
     * BCD码转字符串
     *
     * @param bytes
     * @return
     */
    private String BCD_To_STR(byte[] bytes) {
        char[] temp = new char[bytes.length * 2];
        char val;
        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * ASCII转BCD
     *
     * @param ascii
     * @param asc_len
     * @return
     */
    private byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = ASC_To_BCD(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : ASC_To_BCD(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    /**
     * ASCII字符转BCD字符
     *
     * @param asc
     * @return
     */
    private byte ASC_To_BCD(byte asc) {
        byte bcd;
        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }

}
