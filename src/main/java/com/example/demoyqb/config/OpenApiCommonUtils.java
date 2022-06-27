package com.example.demoyqb.config;

import java.io.IOException;

public class OpenApiCommonUtils {
    public static final String CHARSET_DEFAULT = "UTF-8";

    /**
     * 进行 BASE64 解码
     * @param base64String
     * @return
     */
    public static byte[] base64Decode(String base64String) throws IOException {
        if (base64String == null) {
            return null;
        }
        try {
            return (new sun.misc.BASE64Decoder()).decodeBuffer(base64String);
        } catch (IOException e) {
            throw e;
        }
    }
    /**
     * 进行 BASE64 编码
     * @param src
     * @return
     */
    public static String base64Encode(byte[] src) {
        if (src == null) {
            return null;
        }
        return (new sun.misc.BASE64Encoder()).encode(src);
    }
    public static boolean isBlank(String str) {
        return null == str || str.trim().length() < 1;
    }

}
