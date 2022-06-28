package com.example.demoyqb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demoyqb.bean.request.BaseYqbRequest;
import com.example.demoyqb.bean.request.MerchantBalanceInquiryRequest;
import com.example.demoyqb.config.OpenApiAESKeyCreator;
import com.example.demoyqb.config.OpenApiAESUtils;
import com.example.demoyqb.config.SignTest;
import com.example.demoyqb.config.WxMappingJackson2HttpMessageConverter;
import com.example.demoyqb.enums.YqbInterfaceEnums;
import org.springframework.web.client.RestTemplate;

public class AService {

    private static String merchantKey = "2dd81587a77649709500196117a192ce";

    private static String merchantPriKey =
            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALLf7RzCWP9KkTzEy5kn1Pu8g7IUv7At5TyaB43D1P8QCDUs4/W/V85p88kl/ouSv+D7gKXhDC2DC4qxi0QdsR+36CrpiZtAwp7U1iADDdK3NTKjFdsuFUmHhquOwh4AMEfMUXthIj4ROOLwgY1Z5hppkvC90xy//AmmTOCMZV57AgMBAAECgYBTpwdjsLwgQAYmLj6tFIS1adcJHEz9HqmbKsmnkdL1qdC81Y6SafatcL1y75LYQTv9AoGKkfG5AxUNurRPmbcwPsc8QeelZMOKnKlFF6owCvQBQoZCH9cr+344qKp2ILAzTSkOJAEDtbuCjxAptI3szmM4USkXadz2cOMRQxo3uQJBAOHUu/R+iog7OOzXe5dx6SGQ88BkDs3EStOKxFm/Ae/49gi2pt3+RaqlaC2ZPFPwDCP3MawXvw08PFt+c1JLxz8CQQDKxVG66wOFLWuZ3Xe3swBnG8/XVKLN6KwF6c9gwQAOQkLtQaWAzOvoQd8wTec5CELw53DAcu98TbgT6Jgf6zXFAkAUXXDNrZOkkWKiyRPWJmmVo1K36M4E9EyjIwJt7XGpSFQ3mPEXV9TEfMIWSplMIHuXyrTqBgIumV4ACjkwFLFVAkEAozIQR4oieStHML0IP3b32gSOUNYzednLqa62Uz7CVreJuf5dv74uF+38PZpVgJdfmRgMrTdT/A1pG8zVssrRqQJAfCtryNueAjq3qIgbe90HlPev/9bbAiBCzJSm2YYGfPzJi/EpnzlhH+BjWCNPjjnUt6DYGKPVU24145YHvjD3TQ==";


    private static String signMerchantNo = "900000121547";
    private static String mercOrderNo = "seq20220629005003401";
    private static String requestNo = "SEQ1656435357723";

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

        baseYqbRequest.setRequestTime("1656435358447");

        System.out.println("JSON.toJSONString(baseYqbRequest) = " + JSON.toJSONString(baseYqbRequest));
        String s = JSON.toJSONString(baseYqbRequest,
                SerializerFeature.MapSortField);
        System.out.println("JSON.toJSONString(baseYqbRequest) = " + s);

        String sign = SignTest.sign(s, merchantPriKey);


        System.out.println("sign = " + sign);
        //"MXpBoN5zd3pMC9ZQb+nm26TMhYvIVWvJEQDEkP/YY1q6Wlyt2kH8JoBLQmVJXkVqGWJ01iedDtAW
        //4mcIwZaeCRfcHFludC/wlMYb0H5oZMrTakaGtxHVxU34igF2EzDYKHfcP0hNwESKmhH7C7svJGkT
        //m2esBabKTKfnzVTKzuM="

        //ItzNLkPecZUdpUdAPr68bwKlCzVmUteN5ZKlPLgGorox6oybOzsKku1HST+D4vD2BhN0EC7x0vwt
        //QXFzfiyABnMAOaaRKxZj4F/OvXf/muE+Xn0/bKI8h2hmlo2zbFQX9wDsYFLWNzMRXoAUYs4fFFwN
        //GoWgtMg6CAS0ZRiqxtc=

        String encrypt = OpenApiAESUtils.encrypt(bizData, OpenApiAESKeyCreator.toAesKey128(merchantKey));
        System.out.println("encrypt = " + encrypt);
        //hiewVEcwsk5R/JIg0Di/aHlBcXnf1JF7lKHgO6rsVi4iboLFx0o31BhMM0t2J0UMjACdoE2VhIea
        //9AapQNpdTQ==

        //boolean verify = SignTest.verify(s, sign, merchantPublicKey);
        //System.out.println("verify = " + verify);
        //if (verify) {
        //    baseYqbRequest.setSign(sign);
        //
        //    System.out.println("baseYqbRequest = " + JSON.toJSONString(baseYqbRequest));
        //
        //    //调接口
        //
        //    RestTemplate restTemplate = new RestTemplate();
        //    restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        //
        //    String s1 = restTemplate.postForObject(url, baseYqbRequest, String.class);
        //
        //    String s2 = JSON.toJSONString(s1);
        //
        //    System.out.println("s2 = " + s2);
        //}
    }

    private static final String url = "https://test-mapi.stg.1qianbao.com/openapi";
}
