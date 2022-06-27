package com.example.demoyqb.config;

public class OpenapiAESDemo {
    public static void main(String[] args) throws Exception {
        String testData="122345565656";

        /** 商户秘钥**/
        String merchantKey="853ec0e3d76547a4b97b5dd776366f8f";

        /** 获取AES秘钥**/
        String aesKey=OpenApiAESKeyCreator.toAesKey128(merchantKey);

        /** AES加密**/
        String bizData=OpenApiAESUtils.encrypt(testData, aesKey);
        System.out.println("AES加密结果："+bizData);

        /** AES解密**/
        String decryptRs=OpenApiAESUtils.decrypt(bizData, aesKey);
        System.out.println("AES解密结果："+decryptRs);
    }

}
