package com.example.demoyqb.bean.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * http://test-open.stg.yqb.com/moap/www/doc/detail_new?product=P2101&no=1894948470167440392
 */

@NoArgsConstructor
@Data
public class MerchantBalanceInquiryResult{

    @JSONField(name = "cashAccount")
    private String cashAccount;
    @JSONField(name = "respMsg")
    private String respMsg;
    @JSONField(name = "mercOrderNo")
    private String mercOrderNo;
    @JSONField(name = "paymentAccount")
    private String paymentAccount;
    @JSONField(name = "settlementAccount")
    private String settlementAccount;
    @JSONField(name = "respCode")
    private String respCode;
}
