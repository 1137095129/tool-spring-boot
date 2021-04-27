package com.github.wang.core;

public interface IExecuteSuccessHandler extends Handler {
    @Override
    default void handle() {
        executeSuccessHandle();
    }

    void executeSuccessHandle();
}
