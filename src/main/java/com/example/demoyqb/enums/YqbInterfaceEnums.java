package com.example.demoyqb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YqbInterfaceEnums {
    A5031 ("实时付款-代付到卡","C0001.A5031"),
    A5030 ("商户余额查询","C0001.A5030"),
    ;


    private String describe;
    private String serviceCode;
}
