package com.example.demoyqb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demoyqb.bean.request.BaseYqbRequest;
import com.example.demoyqb.bean.request.MerchantBalanceInquiryRequest;
import com.example.demoyqb.bean.result.BaseYqbResult;
import com.example.demoyqb.config.SignTest;
import com.example.demoyqb.enums.YqbInterfaceEnums;

import java.time.Instant;

public class AbService {
    private static String merchantKey = "2dd81587a77649709500196117a192ce";
    //private static String merchantPriKey =
    //        "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALLf7RzCWP9KkTzEy5kn1Pu8g7IUv7At5TyaB43D1P8QCDUs4/W" +
    //                "/V85p88kl/ouSv+D7gKXhDC2DC4qxi0QdsR" +
    //                "+36CrpiZtAwp7U1iADDdK3NTKjFdsuFUmHhquOwh4AMEfMUXthIj4ROOLwgY1Z5hppkvC90xy" +
    //                "//AmmTOCMZV57AgMBAAECgYBTpwdjsLwgQAYmLj6tFIS1adcJHEz9HqmbKsmnkdL1qdC81Y6SafatcL1y75LYQTv9AoGKkfG5AxUNurRPmbcwPsc8QeelZMOKnKlFF6owCvQBQoZCH9cr+344qKp2ILAzTSkOJAEDtbuCjxAptI3szmM4USkXadz2cOMRQxo3uQJBAOHUu/R+iog7OOzXe5dx6SGQ88BkDs3EStOKxFm/Ae/49gi2pt3+RaqlaC2ZPFPwDCP3MawXvw08PFt+c1JLxz8CQQDKxVG66wOFLWuZ3Xe3swBnG8/XVKLN6KwF6c9gwQAOQkLtQaWAzOvoQd8wTec5CELw53DAcu98TbgT6Jgf6zXFAkAUXXDNrZOkkWKiyRPWJmmVo1K36M4E9EyjIwJt7XGpSFQ3mPEXV9TEfMIWSplMIHuXyrTqBgIumV4ACjkwFLFVAkEAozIQR4oieStHML0IP3b32gSOUNYzednLqa62Uz7CVreJuf5dv74uF+38PZpVgJdfmRgMrTdT/A1pG8zVssrRqQJAfCtryNueAjq3qIgbe90HlPev/9bbAiBCzJSm2YYGfPzJi/EpnzlhH+BjWCNPjjnUt6DYGKPVU24145YHvjD3TQ==";
    private static String merchantPriKey =
            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANHKChQJK8NfTBEKPyBmakGkfb1foTYhFccp9tpiDTyDbKbAqsC+Bv8ckCv0CbOpV8bkhxi+7KcFGJmdyqibardNKNrCh/Vq1PUaCyShNiVyNp46qb68n/ia+ggwVjhMDkpMkmgbOk2t2EiLppnFVdH79BTtbfecipReGuv97JB5AgMBAAECgYEArM6gPqDPS7/UCLVICohSPoAgkz2SrePS7JvEm17pSE950GIkRWBCoyIEIDCFTVjQ1SSpo9ihV3L7MI6pwPlEjPs7kmrmengSxNyP1PTpOLvdnyoWHMfmuErUISoWPRy8GQHHK85elcqIdcQ8Jh2egneL0R6lkuR+qQ+eO8A7hgECQQDtVaKUfmVSJLeSmw6njQiiD/YE8ZlRmp0L6wZZJdY6aQcNYxJFAN3mwW+6p38MW4JdzcYzFW2gk1SzogLsX6mtAkEA4knSej+2MTV9fonMz1TTzEBTf15HRHXNcFizPkRhOF+on//s4FedaSpE8/cqFCgjgY2bdo7vAEiFjeix/bZzfQJAB7pgHuG2vuP/LkDqSz5mZ5CJfEO5sFSOhJLUlBaNUT2WQzNUpvaOQzNNRhCqLGbWaeOHbqZl+XKSsX63BadfyQJASv0XyPdQEyKcwdA8lQCRZ4BxqyW9DEzVlcNvMtVKOnpI0SzofLfMLEubqhVgVmmuyoH44OMhBMEDsTFJusA5qQJAUgNtckGbIO6/mQlN2p66qEAgqdox1BRIR8JW+C2R5nkfeEae6IPJm59b9D5ME68pnEL0Hz/l6BxOim4U+ukV4A==";


private static         String publicKey =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDRygoUCSvDX0wRCj8gZmpBpH29X6E2IRXHKfbaYg08g2ymwKrAvgb/HJAr9AmzqVfG5IcYvuynBRiZncqom2q3TSjawof1atT1GgskoTYlcjaeOqm+vJ/4mvoIMFY4TA5KTJJoGzpNrdhIi6aZxVXR+/QU7W33nIqUXhrr/eyQeQIDAQAB";



    private static String mercOrderNo="seq20220628000059560";

    private static String signMerchantNo="900000121547";
    private static String requestNo="SEQ1656345737601";

    public static void main(String[] args) throws Exception {


        MerchantBalanceInquiryRequest request = new MerchantBalanceInquiryRequest();
        BaseYqbRequest baseYqbRequest = new BaseYqbRequest();
        //bizData字段设置为未加密原始json字符串；
        //去掉sign参数字段；
        //将报文json字符串 按照key字典序升序排序（bizData中json字符串内容无需排序）
        //将第3步生成的字符串+商户秘钥后进行SHA-256,生成签名。
        //签名数据Base64编码；

        //验签规则：同签名逻辑，验签使用平安付提供的RSA公钥验证。

        //请求报文签名是使用商户侧私钥加签；接口响应验签使用平安付侧的RSA公钥；
        //商户侧RSA公钥通过平安付B门户上传至平台，平安付侧RAS公钥由平安付提供；
        //RSA位数2048。
        request.setMercOrderNo(mercOrderNo);
        request.setAcctType("05");
        baseYqbRequest.setBizData(JSON.toJSONString(request));
        baseYqbRequest.setServiceCode(YqbInterfaceEnums.A5030.getServiceCode());
        baseYqbRequest.setRequestNo(requestNo);
        baseYqbRequest.setSignMerchantNo(signMerchantNo);
baseYqbRequest.setCharset("UTF-8");
baseYqbRequest.setSignType("SHA256withRSA");
baseYqbRequest.setEncryptType("AES");

        baseYqbRequest.setRequestTime("1656347396791");

        System.out.println("JSON.toJSONString(baseYqbRequest) = " + JSON.toJSONString(baseYqbRequest));
        String s = JSON.toJSONString(baseYqbRequest,
                SerializerFeature.MapSortField);
        System.out.println("JSON.toJSONString(baseYqbRequest) = " + s);

        String sign = SignTest.sign(s, merchantPriKey);

      //  n84BgytQ3lLge2K592+7YQRm6xkphi/dKbM7PjPlhd5nV3OoqJTj2LOR4tXbE8/0CyFDr6
        //  +v51FTTBZmVt0YKgoBeNgiLNIhbj8XuR62N3V4e7BcU4jY/qaLoL/mU0d7nrF/T7U+84GY04U82ltmr5NHWkjIY1++6wnJCW6nGi4=

        System.out.println("sign = " + sign);

        boolean verify = SignTest.verify(s, sign, publicKey);
        System.out.println("verify = " + verify);
    }
}
