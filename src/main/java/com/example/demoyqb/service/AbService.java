package com.example.demoyqb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demoyqb.bean.request.BaseYqbRequest;
import com.example.demoyqb.bean.request.MerchantBalanceInquiryRequest;
import com.example.demoyqb.bean.result.BaseYqbResult;
import com.example.demoyqb.config.OpenApiAESKeyCreator;
import com.example.demoyqb.config.OpenApiAESUtils;
import com.example.demoyqb.config.SignTest;
import com.example.demoyqb.config.WxMappingJackson2HttpMessageConverter;
import com.example.demoyqb.enums.YqbInterfaceEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

public class AbService {

    private static String merchantKey = "dfce12f3a3bd429fb1cf925bd355b9ec";

    private static String merchantPriKey =
            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALLf7RzCWP9KkTzEy5kn1Pu8g7IUv7At5TyaB43D1P8QCDUs4/W/V85p88kl/ouSv+D7gKXhDC2DC4qxi0QdsR+36CrpiZtAwp7U1iADDdK3NTKjFdsuFUmHhquOwh4AMEfMUXthIj4ROOLwgY1Z5hppkvC90xy//AmmTOCMZV57AgMBAAECgYBTpwdjsLwgQAYmLj6tFIS1adcJHEz9HqmbKsmnkdL1qdC81Y6SafatcL1y75LYQTv9AoGKkfG5AxUNurRPmbcwPsc8QeelZMOKnKlFF6owCvQBQoZCH9cr+344qKp2ILAzTSkOJAEDtbuCjxAptI3szmM4USkXadz2cOMRQxo3uQJBAOHUu/R+iog7OOzXe5dx6SGQ88BkDs3EStOKxFm/Ae/49gi2pt3+RaqlaC2ZPFPwDCP3MawXvw08PFt+c1JLxz8CQQDKxVG66wOFLWuZ3Xe3swBnG8/XVKLN6KwF6c9gwQAOQkLtQaWAzOvoQd8wTec5CELw53DAcu98TbgT6Jgf6zXFAkAUXXDNrZOkkWKiyRPWJmmVo1K36M4E9EyjIwJt7XGpSFQ3mPEXV9TEfMIWSplMIHuXyrTqBgIumV4ACjkwFLFVAkEAozIQR4oieStHML0IP3b32gSOUNYzednLqa62Uz7CVreJuf5dv74uF+38PZpVgJdfmRgMrTdT/A1pG8zVssrRqQJAfCtryNueAjq3qIgbe90HlPev/9bbAiBCzJSm2YYGfPzJi/EpnzlhH+BjWCNPjjnUt6DYGKPVU24145YHvjD3TQ==";

    private static String merchantPublicKey =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCy3+0cwlj/SpE8xMuZJ9T7vIOyFL+wLeU8mgeNw9T/EAg1LOP1v1fOafPJJf6Lkr/g" +
                    "+4Cl4QwtgwuKsYtEHbEft+gq6YmbQMKe1NYgAw3StzUyoxXbLhVJh4arjsIeADBHzFF7YSI+ETji8IGNWeYaaZLwvdMcv" +
                    "/wJpkzgjGVeewIDAQAB";

    private static String publicKey =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsLu7NfbqfGiUIvCNf7wk5RbP" +
                    "+hdauEUBp0Khp1iILFs7O437LyWxYjTtkNWLlcNdZh3BiwTXcNO6c2mf98hiJW8v9hpy" +
                    "+2qoNU40t9RUdJPLQuvvX3lJrPJKjtHrUhOICM06q0Z/FWyvQQO6wswrQTcE" +
                    "/9cBPu7CLvtUSFWq5DmHE8ZtSVah4m6F87GJgKx+SK8PByruWIpQHzeoE5Wv/OB3ZCn8mwDH9rNxS8B" +
                    "+bfFyuDkho4br4QTICC5EirXze4bjJil9g6l35" +
                    "/nUsFmU7OCzgUXfcdwghnQsnzr8LkIgiA7hFaj45av5C2asCpmfTqOY6B0dAPOSkTo5GN7mTQIDAQAB";

    private static String signMerchantNo = "900000121951";

    private static String mercOrderNo = "seq20220629005003401";

    private static String requestNo = "SEQ1656435016786";

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
        String bizData = JSON.toJSONString(request);
        baseYqbRequest.setBizData(bizData);
        baseYqbRequest.setServiceCode(YqbInterfaceEnums.A5030.getServiceCode());
        baseYqbRequest.setRequestNo(requestNo);
        baseYqbRequest.setSignMerchantNo(signMerchantNo);
        baseYqbRequest.setCharset("UTF-8");
        baseYqbRequest.setSignType("SHA256withRSA");
        baseYqbRequest.setEncryptType("AES");

        baseYqbRequest.setRequestTime("1656436451540");

        System.out.println("JSON.toJSONString(baseYqbRequest) = " + JSON.toJSONString(baseYqbRequest));
        String s = JSON.toJSONString(baseYqbRequest,
                SerializerFeature.MapSortField);
        System.out.println("JSON.toJSONString(baseYqbRequest) = " + s);

        String sign = SignTest.sign(s, merchantPriKey);

        //  n84BgytQ3lLge2K592+7YQRm6xkphi/dKbM7PjPlhd5nV3OoqJTj2LOR4tXbE8/0CyFDr6
        //  +v51FTTBZmVt0YKgoBeNgiLNIhbj8XuR62N3V4e7BcU4jY/qaLoL/mU0d7nrF/T7U+84GY04U82ltmr5NHWkjIY1++6wnJCW6nGi4=

        System.out.println("sign = " + sign);

        boolean verify = SignTest.verify(s, sign, merchantPublicKey);
        System.out.println("verify = " + verify);
        if (verify) {
            baseYqbRequest.setSign(sign);
            String encrypt = OpenApiAESUtils.encrypt(bizData, OpenApiAESKeyCreator.toAesKey128(merchantKey));
            System.out.println("encrypt = " + encrypt);
            baseYqbRequest.setBizData(encrypt);

            System.out.println("baseYqbRequest = " + JSON.toJSONString(baseYqbRequest));

            //调接口

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());

            String s1 = restTemplate.postForObject(url, baseYqbRequest, String.class);

            String s2 = JSON.toJSONString(s1);

            System.out.println("s2 = " + s2);
        }
    }

    private static final String url = "https://test-mapi.stg.1qianbao.com/openapi";
}
