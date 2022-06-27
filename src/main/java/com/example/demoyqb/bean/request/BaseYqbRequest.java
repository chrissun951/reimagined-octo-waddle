package com.example.demoyqb.bean.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public  class BaseYqbRequest {

    @JSONField(name = "requestTime")
    private String requestTime;
    @JSONField(name = "charset")
    private String charset;
    @JSONField(name = "signMerchantNo")
    private String signMerchantNo;
    @JSONField(name = "serviceCode")
    private String serviceCode;
    @JSONField(name = "sign")
    private String sign;
    @JSONField(name = "signType")
    private String signType;
    @JSONField(name = "bizData")
    private String bizData;
    @JSONField(name = "requestNo")
    private String requestNo;
    @JSONField(name = "encryptType")
    private String encryptType;
}
