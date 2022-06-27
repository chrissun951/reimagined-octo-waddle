package com.example.demoyqb.config;

import java.util.Arrays;

/**
 *
 * 收单秘钥转为AES Key
 */
public class OpenApiAESKeyCreator {


    public static void main(String[] args) {

        String merchantKey = "853ec0e3d76547a4b97b5dd776366f8f";

        System.out.println("==AES128秘钥========================");
        System.out.println(toAesKey128(merchantKey));

    }
    /**
     * 将收单秘钥转为AES 128秘钥， 秘钥base64编码
     *
     * @param merchantKey
     * @return
     */
    public static String toAesKey128(String merchantKey) {
        return toAesKey(merchantKey, 128);
    }

    /**
     *
     *
     * @param merchantKey 收单商户秘钥
     * @param aesKeyBit AES秘钥位数 128、192、256
     * @return
     */
    private static String toAesKey(String merchantKey, int aesKeyBit) {

        int aesKeySize = aesKeyBit / 8;
        // 16位编码转为字节数组
        byte[] bs = hexStr2Bytes(merchantKey);

        byte[] aesKeyBytes = Arrays.copyOf(bs, aesKeySize);

        int merchantKeySize = bs.length;

        if (aesKeySize > merchantKeySize) {
            // 填充
            for (int index = merchantKeySize; index < aesKeySize; index++) {
                aesKeyBytes[index] = (byte) index;
            }
        }

        return base64encode(aesKeyBytes);
    }


    // 将 s 进行 BASE64 编码
    private static String base64encode(byte[] src) {
        if (src == null) {
            return null;
        }

        return (new sun.misc.BASE64Encoder()).encode(src);
    }
    private static byte[] hexStr2Bytes(String src) {
        /* 对输入值进行规范化整理 */
        src = src.trim().replace(" ", "").toUpperCase();
        // 处理值初始化
        int m = 0, n = 0;
        int iLen = src.length() / 2; // 计算长度
        byte[] ret = new byte[iLen]; // 分配存储空间

        for (int i = 0; i < iLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = (byte) (Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)).intValue() & 0xFF);
        }
        return ret;
    }
}

