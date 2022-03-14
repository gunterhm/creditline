package com.tribal.creditline.util;

import org.apache.tomcat.util.codec.binary.Base64;

public class AuthUtil {
    public static String getUserName(String authHeader) {
        String userName = null;
        if (null != authHeader) {
            String pair = new String(Base64.decodeBase64(authHeader.substring(6)));
            if (pair.contains(":")) {
                userName = pair.split(":")[0];
            }
        }
        return userName;
    }
}
