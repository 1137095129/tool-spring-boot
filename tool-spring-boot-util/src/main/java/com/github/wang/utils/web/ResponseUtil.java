package com.github.wang.utils.web;

import cn.hutool.json.JSONUtil;

import javax.servlet.ServletResponse;
import java.io.IOException;

public class ResponseUtil {

    public static void write(byte[] bytes, ServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(bytes);
    }

    public static void write(String message, ServletResponse response) throws IOException {
        write(message.getBytes(), response);
    }

    public static <T> void write(T obj, ServletResponse response) throws IOException {
        response.setContentType("application/json");
        write(JSONUtil.toJsonStr(obj), response);
    }

}
