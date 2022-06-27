package com.example.demoyqb.config;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


//todo hutools
public class SignTest {
    //public static void main(String[] args) {
    //    String bizData=null;

        //bizData字段设置为未加密原始json字符串；
        //去掉sign参数字段；
        //将报文json字符串 按照key字典序升序排序（bizData中json字符串内容无需排序）
        //将第3步生成的字符串+商户秘钥后进行SHA-256,生成签名。
        //签名数据Base64编码；

        //验签规则：同签名逻辑，验签使用平安付提供的RSA公钥验证。

        //请求报文签名是使用商户侧私钥加签；接口响应验签使用平安付侧的RSA公钥；
        //商户侧RSA公钥通过平安付B门户上传至平台，平安付侧RAS公钥由平安付提供；
        //RSA位数2048。

    //对bizData业务参数原json字符串进行AES128加密（加密数据Base64编码）
    //模式：  AES/CBC/PKCS5Padding , 秘钥由平安付提供。


    //}



    /**
     * 加签算法
     */
    public static final String SIGN_ALGORITHM = "SHA256WithRSA";

    /**
     * 秘钥算法
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 秘钥长度
     */
    public static final int KEY_SIZE = 2048;
    /**
     * 生成签名
     *
     * @param orgData 待加签数据
     * @param privateKey 秘钥(base64编码) RSA私钥
     * @return
     * @throws Exception
     */

    public static String sign(String orgData, String privateKey) throws Exception {
        return sign(orgData, privateKey, OpenApiCommonUtils.CHARSET_DEFAULT);
    }

    /**
     * 生成签名
     *
     * @param orgData 待加签数据
     * @param privateKey 秘钥(base64编码)
     * @param charset 编码
     * @return
     * @throws Exception
     */

    public static String sign(String orgData, String privateKey, String charset) throws Exception {
        String charsetName = OpenApiCommonUtils.isBlank(charset) ? OpenApiCommonUtils.CHARSET_DEFAULT : charset;
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(OpenApiCommonUtils.base64Decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGN_ALGORITHM);
            signature.initSign(priKey);
            signature.update(orgData.getBytes(charsetName));
            byte[] signed = signature.sign();
            return OpenApiCommonUtils.base64Encode(signed);
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 验签
     *
     * @param orgData 待签名数据
     * @param sign 签名串
     * @param publicKey 公钥(base64编码)
     * @return 布尔值
     */
    public static boolean verify(String orgData, String sign, String publicKey) throws Exception {
        return verify(orgData, sign, publicKey, OpenApiCommonUtils.CHARSET_DEFAULT);
    }

    /**
     * 验签
     *
     * @param orgData 待签名数据
     * @param sign 签名串
     * @param publicKey 公钥(base64编码)
     * @param charset 编码格式
     * @return 布尔值
     */
    public static boolean verify(String orgData, String sign, String publicKey, String charset) throws Exception {
        String charsetName = OpenApiCommonUtils.isBlank(charset) ? OpenApiCommonUtils.CHARSET_DEFAULT : charset;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            byte[] encodedKey = OpenApiCommonUtils.base64Decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            Signature signature = Signature.getInstance(SIGN_ALGORITHM);
            signature.initVerify(pubKey);
            signature.update(orgData.getBytes(charsetName));

            return signature.verify(OpenApiCommonUtils.base64Decode(sign));
        } catch (Exception e) {
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {

        /** 测试数据**/
        String data ="testabc1233";
        /** 测试私钥**/
        String privateKey =
                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANHKChQJK8NfTBEKPyBmakGkfb1foTYhFccp9tpiDTyDbKbAqsC+Bv8ckCv0CbOpV8bkhxi+7KcFGJmdyqibardNKNrCh/Vq1PUaCyShNiVyNp46qb68n/ia+ggwVjhMDkpMkmgbOk2t2EiLppnFVdH79BTtbfecipReGuv97JB5AgMBAAECgYEArM6gPqDPS7/UCLVICohSPoAgkz2SrePS7JvEm17pSE950GIkRWBCoyIEIDCFTVjQ1SSpo9ihV3L7MI6pwPlEjPs7kmrmengSxNyP1PTpOLvdnyoWHMfmuErUISoWPRy8GQHHK85elcqIdcQ8Jh2egneL0R6lkuR+qQ+eO8A7hgECQQDtVaKUfmVSJLeSmw6njQiiD/YE8ZlRmp0L6wZZJdY6aQcNYxJFAN3mwW+6p38MW4JdzcYzFW2gk1SzogLsX6mtAkEA4knSej+2MTV9fonMz1TTzEBTf15HRHXNcFizPkRhOF+on//s4FedaSpE8/cqFCgjgY2bdo7vAEiFjeix/bZzfQJAB7pgHuG2vuP/LkDqSz5mZ5CJfEO5sFSOhJLUlBaNUT2WQzNUpvaOQzNNRhCqLGbWaeOHbqZl+XKSsX63BadfyQJASv0XyPdQEyKcwdA8lQCRZ4BxqyW9DEzVlcNvMtVKOnpI0SzofLfMLEubqhVgVmmuyoH44OMhBMEDsTFJusA5qQJAUgNtckGbIO6/mQlN2p66qEAgqdox1BRIR8JW+C2R5nkfeEae6IPJm59b9D5ME68pnEL0Hz/l6BxOim4U+ukV4A==";
        /** 测试公钥**/
        String publicKey =
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDRygoUCSvDX0wRCj8gZmpBpH29X6E2IRXHKfbaYg08g2ymwKrAvgb/HJAr9AmzqVfG5IcYvuynBRiZncqom2q3TSjawof1atT1GgskoTYlcjaeOqm+vJ/4mvoIMFY4TA5KTJJoGzpNrdhIi6aZxVXR+/QU7W33nIqUXhrr/eyQeQIDAQAB";

        /** 计算签名 **/
        String sign = sign(data, privateKey);
        System.out.println("签名结果："+sign);

        /** 验证签名 **/
        boolean result=verify(data, sign, publicKey);
        System.out.println("验签结果："+result);

    }


}
