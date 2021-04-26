package com.github.wang.utils.encrypt;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;

public class BaseEncryptionUtil {
    public static byte[] base64Encode(byte[] bytes) {
        return (Base64Encoder.encode(bytes)).getBytes();
    }

    public static byte[] base64Encode(String s) {
        return base64Encode(s.getBytes());
    }

    public static String base64EncodeToString(byte[] bytes) {
        return Base64Encoder.encode(bytes);
    }

    public static String base64EncodeToString(String s) {
        return base64EncodeToString(s.getBytes());
    }

    public static byte[] base64Decode(byte[] bytes) {
        return Base64Decoder.decode(bytes);
    }

    public static byte[] base64Decode(String s) {
        return base64Decode(s.getBytes());
    }

    public static String base64DecodeToString(byte[] bytes) {
        return new String(base64Decode(bytes));
    }

    public static String base64DecodeToString(String source) {
        return new String(base64Decode(source));
    }

}
