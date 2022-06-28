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
                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALLf7RzCWP9KkTzEy5kn1Pu8g7IUv7At5TyaB43D1P8QCDUs4/W/V85p88kl/ouSv+D7gKXhDC2DC4qxi0QdsR+36CrpiZtAwp7U1iADDdK3NTKjFdsuFUmHhquOwh4AMEfMUXthIj4ROOLwgY1Z5hppkvC90xy//AmmTOCMZV57AgMBAAECgYBTpwdjsLwgQAYmLj6tFIS1adcJHEz9HqmbKsmnkdL1qdC81Y6SafatcL1y75LYQTv9AoGKkfG5AxUNurRPmbcwPsc8QeelZMOKnKlFF6owCvQBQoZCH9cr+344qKp2ILAzTSkOJAEDtbuCjxAptI3szmM4USkXadz2cOMRQxo3uQJBAOHUu/R+iog7OOzXe5dx6SGQ88BkDs3EStOKxFm/Ae/49gi2pt3+RaqlaC2ZPFPwDCP3MawXvw08PFt+c1JLxz8CQQDKxVG66wOFLWuZ3Xe3swBnG8/XVKLN6KwF6c9gwQAOQkLtQaWAzOvoQd8wTec5CELw53DAcu98TbgT6Jgf6zXFAkAUXXDNrZOkkWKiyRPWJmmVo1K36M4E9EyjIwJt7XGpSFQ3mPEXV9TEfMIWSplMIHuXyrTqBgIumV4ACjkwFLFVAkEAozIQR4oieStHML0IP3b32gSOUNYzednLqa62Uz7CVreJuf5dv74uF+38PZpVgJdfmRgMrTdT/A1pG8zVssrRqQJAfCtryNueAjq3qIgbe90HlPev/9bbAiBCzJSm2YYGfPzJi/EpnzlhH+BjWCNPjjnUt6DYGKPVU24145YHvjD3TQ==";
        /** 测试公钥**/
        String publicKey =
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCy3+0cwlj/SpE8xMuZJ9T7vIOyFL+wLeU8mgeNw9T/EAg1LOP1v1fOafPJJf6Lkr/g+4Cl4QwtgwuKsYtEHbEft+gq6YmbQMKe1NYgAw3StzUyoxXbLhVJh4arjsIeADBHzFF7YSI+ETji8IGNWeYaaZLwvdMcv/wJpkzgjGVeewIDAQAB";

        /** 计算签名 **/
        String sign = sign(data, privateKey);
        System.out.println("签名结果："+sign);

        /** 验证签名 **/
        boolean result=verify(data, sign, publicKey);
        System.out.println("验签结果："+result);

    }


}
