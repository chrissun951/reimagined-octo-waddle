package com.example.demoyqb.bean.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public  class BaseYqbResult {

    @JSONField(name = "msg")
    private String msg;
    @JSONField(name = "code")
    private String code;
    @JSONField(name = "sign")
    private String sign;
    @JSONField(name = "signType")
    private String signType;
    @JSONField(name = "bizData")
    private String bizData;
    @JSONField(name = "encryptType")
    private String encryptType;
}
