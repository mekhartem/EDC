package com.ua.eds.utils;

import lombok.experimental.UtilityClass;

import java.util.Base64;

@UtilityClass
public class Base64Utils {

    public byte[] decodeKey(String keyString) {
        return Base64.getDecoder().decode(keyString);
    }

    public String encodeKey(byte[] keyBytes) {
        return Base64.getEncoder().encodeToString(keyBytes);
    }

}
