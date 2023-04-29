package com.album2me.repost.global.util;

public class RegexUtils {
    public static final String AUTHID_REGEX = "^[가-힣0-9a-zA-Z]{4,10}$";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$";
    public static final String NICKNAME_REGEX = "^[가-힣0-9a-zA-Z]{2,10}$";

}
