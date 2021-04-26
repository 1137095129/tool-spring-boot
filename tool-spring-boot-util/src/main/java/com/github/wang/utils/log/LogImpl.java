package com.github.wang.utils.log;

import com.github.wang.core.log.ILogger;

import java.io.Serializable;
import java.util.Date;

public class LogImpl implements ILogger, Serializable {
    private static final long serialVersionUID = -2841127171885181236L;

    private final Integer executeUser;
    private final Date executeDate;
    private final String executeContext;

    public LogImpl(Integer executeUser, Date executeDate, String executeContext) {
        this.executeUser = executeUser;
        this.executeDate = executeDate;
        this.executeContext = executeContext;
    }

    @Override
    public Integer executeUser() {
        return this.executeUser;
    }

    @Override
    public Date executeDate() {
        return this.executeDate;
    }

    @Override
    public String executeContext() {
        return this.executeContext;
    }

    public static ILogger newLog(Integer executeUser, String executeContext) {
        return new LogImpl(executeUser, new Date(), executeContext);
    }

    public static ILogger newLog(Integer executeUser, Date executeDate, String executeContext) {
        return new LogImpl(executeUser, executeDate, executeContext);
    }
}
