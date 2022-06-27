package com.example.demoyqb.config;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * iaps通用网关加密工具 AES128 AES/CBC/PKCS5Padding
 */
public class OpenApiAESUtils {

    private static final String KEY_ALGORITHM = "AES";

    private static final String CBC_ALGORITHM = "AES/CBC/PKCS5Padding";

    private static final byte[] IV_VALUE = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6};


    /**
     * 加密数据 AES/CBC/PKCS5Padding
     *
     * @param data 待加密数据
     * @param key 密钥 128位
     * @return 加密后的数据 CBC模式
     */
    public static String encrypt(String data, String key) throws Exception {
        return encrypt(data, key, OpenApiCommonUtils.CHARSET_DEFAULT);
    }

    /**
     * 加密数据 AES/CBC/PKCS5Padding
     *
     * @param data 待加密数据
     * @param key 密钥 128位
     * @param charset 编码
     * @return 加密后的数据 CBC模式
     */
    public static String encrypt(String data, String key, String charset) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(CBC_ALGORITHM);
            Key desKey = base64DecodeSecretKey(key);
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强庄1�7
            IvParameterSpec iv = new IvParameterSpec(IV_VALUE);
            cipher.init(Cipher.ENCRYPT_MODE, desKey, iv);
            // 加密
            byte[] result = cipher.doFinal(data.getBytes(OpenApiCommonUtils.isBlank(charset) ? OpenApiCommonUtils.CHARSET_DEFAULT : charset));
            // 通过Base64转码返回
            return OpenApiCommonUtils.base64Encode(result);
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key 密钥
     * @param charset 编码
     * @return 解密后的数据 CBC解密
     */
    public static String decrypt(String data, String key) throws Exception {
        return decrypt(data, key, OpenApiCommonUtils.CHARSET_DEFAULT);
    }



    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key 密钥
     * @param charset 编码
     * @return 解密后的数据 CBC解密
     */
    public static String decrypt(String data, String key, String charset) throws Exception {
        try {
            Key desKey = base64DecodeSecretKey(key);
            Cipher cipher = Cipher.getInstance(CBC_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(IV_VALUE);
            cipher.init(Cipher.DECRYPT_MODE, desKey, iv);
            return new String(cipher.doFinal(OpenApiCommonUtils.base64Decode(data)), OpenApiCommonUtils.isBlank(charset) ? OpenApiCommonUtils.CHARSET_DEFAULT : charset);
        } catch (Exception e) {
            throw e;
        }
    }



    /**
     * 解析秘钥
     *
     * @param keyStr 秘钥(base64编码)
     * @return
     * @throws Exception
     */
    private static Key base64DecodeSecretKey(String keyStr) throws Exception {
        return new SecretKeySpec(OpenApiCommonUtils.base64Decode(keyStr), KEY_ALGORITHM);
    }



    public static void main(String[] args) throws Exception {

        String data ="testabc123";

        String key = "wm5H8sqSSzS/qr9VXZy4oA==";

        String encodeData = encrypt(data, key);

        System.out.println(encodeData);

        System.out.println(data.equals(decrypt(encodeData, key)));

    }

}