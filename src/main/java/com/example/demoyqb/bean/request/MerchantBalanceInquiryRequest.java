package com.example.demoyqb.bean.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.demoyqb.bean.result.BaseYqbResult;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * http://test-open.stg.yqb.com/moap/www/doc/detail_new?product=P2101&no=1894948470167440392
 */
@NoArgsConstructor
@Data
public class MerchantBalanceInquiryRequest   {

    /**
     * 商户侧生成，保持每笔请求单号唯一
     * 商户订单号
     * 必填
     * 32
     */
    @JSONField(name = "mercOrderNo")
    private String mercOrderNo;
    /**
     * 账户类型
     *
     * 商户账户类型；
     * 枚举值：
     * 05—商户现金户余额；
     * 07—商户付款户余额；
     * 03—商户结算户余额；
     * 不传默认查出所有账户余额
     */
    @JSONField(name = "acctType")
    private String acctType;
}
