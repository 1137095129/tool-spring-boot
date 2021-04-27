package com.github.wang.utils.regex;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static boolean checkContain(String targetStr, String regexStr) {
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(targetStr);
        return matcher.find();
    }

    public static String replaceAll(String targetStr, String regexStr, String replaceStr) {
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(targetStr);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, replaceStr);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    public static String replaceAll(String targetStr, String regexStr, Map<String, Object> replaceStrMap) {
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(targetStr);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group();
            Object replace = null;
            if ((replace = replaceStrMap.get(group)) != null) {
                matcher.appendReplacement(buffer, replace.toString());
            } else {
                matcher.appendReplacement(buffer, "");
            }
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
