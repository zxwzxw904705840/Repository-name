package com.example.demo.utils;

import org.springframework.util.DigestUtils;

public class MD5 {
    private static final String slat = "&%5123***&&%%$$#@";public static String getMD5(String str) {
        String base = str +"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
