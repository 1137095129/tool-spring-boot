package com.github.wang.core;

import java.io.Serializable;

public class ResponseMap {

    private static final int REQUEST_SUCCESS = 2000;
    private static final int RESOURCE_NOT_FOUND = 4040;
    private static final int REQUEST_PARAM_ERROR = 4000;
    private static final int ACCESS_DENIED_ERROR = 4030;
    private static final int AUTHENTICATION_ERROR = 4031;
    private static final int SERVER_ERROR = 5000;

    private static <T> ResponseData<T> ok() {
        return ok(null);
    }

    private static <T> ResponseData<T> ok(T data) {
        return ok("request success!", data);
    }

    private static <T> ResponseData<T> ok(String msg, T data) {
        return ok(msg, REQUEST_SUCCESS, data);
    }

    private static <T> ResponseData<T> ok(String msg, int code, T data) {
        return build(msg, code, ResponseEnum.OK, data);
    }

    private static <T> ResponseData<T> fail(String msg) {
        return fail(msg, null);
    }

    private static <T> ResponseData<T> fail(String msg, T data) {
        return fail(msg, SERVER_ERROR, data);
    }

    private static <T> ResponseData<T> fail(String msg, int code, T data) {
        return build(msg, code, ResponseEnum.FAIL, data);
    }

    private static <T> ResponseData<T> build(String msg, int code, ResponseEnum responseEnum, T data) {
        return new ResponseData<>(msg, code, responseEnum, data);
    }

    public final static class ResponseData<T> implements Serializable {
        private static final long serialVersionUID = -7124121233142788908L;
        private String msg;
        private int code;
        private ResponseEnum sym;
        private T data;

        ResponseData(String msg, int code, ResponseEnum sym, T data) {
            this.msg = msg;
            this.code = code;
            this.sym = sym;
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public ResponseEnum getSym() {
            return sym;
        }

        public void setSym(ResponseEnum sym) {
            this.sym = sym;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    public enum ResponseEnum {
        OK,
        FAIL,
        AUTHENTICATION_FAIL,
        ACCESS_DENIED,
        ERROR
    }

}
